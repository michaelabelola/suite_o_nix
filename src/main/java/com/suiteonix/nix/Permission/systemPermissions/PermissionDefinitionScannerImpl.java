package com.suiteonix.nix.Permission.systemPermissions;

import com.suiteonix.nix.shared.permissions.system.PermissionDefinitionRecord;
import com.suiteonix.nix.shared.permissions.system.annotations.PermissionDefinition;
import com.suiteonix.nix.shared.permissions.system.annotations.PermissionDefinitions;
import com.suiteonix.nix.shared.permissions.system.exceptions.DuplicatePermissionDefinitionException;
import com.suiteonix.nix.shared.permissions.system.scanner.PermissionDefinitionScanner;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AbstractTypeHierarchyTraversingFilter;
import org.springframework.stereotype.Component;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PermissionDefinitionScannerImpl implements PermissionDefinitionScanner {

    @Override
    public void scanAndRegisterPermissionDefinitions(String packageName) {
        List<ScannedDefinition> found = new ArrayList<>();

        scanPackageInfo(packageName, found);
        scanClassesAndMethods(packageName, found);

        validateNoDuplicatesWithinBatch(found);
        validateNoConflictsWithRegistry(found);

        found.forEach(sd -> Definitions.register(sd.record()));
    }

    // --- Scanning helpers ---

    private void scanPackageInfo(String packageName, List<ScannedDefinition> found) {
        try {
            Class<?> packageInfo = Class.forName(packageName + ".package-info");
            collectFromElement(packageInfo, packageName, found);
        } catch (ClassNotFoundException ignored) {
            // package-info.java is optional
        }
    }

    private void scanClassesAndMethods(String packageName, List<ScannedDefinition> found) {
        ClassPathScanningCandidateComponentProvider scanner = buildAcceptAllScanner();
        for (var beanDef : scanner.findCandidateComponents(packageName)) {
            String className = beanDef.getBeanClassName();
            if (className == null) continue;
            try {
                Class<?> clazz = Class.forName(className);
                collectFromElement(clazz, className, found);
                for (Method method : clazz.getDeclaredMethods()) {
                    collectFromElement(method, className + "#" + method.getName(), found);
                }
            } catch (ClassNotFoundException ignored) {
                // skip unresolvable classes
            }
        }
    }

    private void collectFromElement(AnnotatedElement element, String sourceLocation, List<ScannedDefinition> found) {
        for (PermissionDefinition annotation : resolveAnnotations(element)) {
            found.add(new ScannedDefinition(
                    PermissionDefinitionRecord.from(annotation, sourceLocation),
                    sourceLocation
            ));
        }
    }

    private List<PermissionDefinition> resolveAnnotations(AnnotatedElement element) {
        PermissionDefinitions container = AnnotationUtils.getAnnotation(element, PermissionDefinitions.class);
        if (container != null) {
            return Arrays.asList(container.value());
        }
        PermissionDefinition single = AnnotationUtils.getAnnotation(element, PermissionDefinition.class);
        if (single != null) {
            return List.of(single);
        }
        return Collections.emptyList();
    }

    private ClassPathScanningCandidateComponentProvider buildAcceptAllScanner() {
        ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AbstractTypeHierarchyTraversingFilter(false, false) {
            @Override
            public boolean match(@NonNull MetadataReader metadataReader, @NonNull MetadataReaderFactory metadataReaderFactory) {
                return true;
            }
        });
        return scanner;
    }

    // --- Duplicate validation ---

    private void validateNoDuplicatesWithinBatch(List<ScannedDefinition> found) {
        Map<String, List<String>> duplicates = found.stream()
                .collect(Collectors.groupingBy(
                        sd -> sd.record().id(),
                        Collectors.mapping(ScannedDefinition::sourceLocation, Collectors.toList())
                ))
                .entrySet().stream()
                .filter(e -> e.getValue().size() > 1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        if (!duplicates.isEmpty()) {
            throw new DuplicatePermissionDefinitionException(duplicates);
        }
    }

    private void validateNoConflictsWithRegistry(List<ScannedDefinition> found) {
        Map<String, List<String>> conflicts = new LinkedHashMap<>();
        for (ScannedDefinition sd : found) {
            Definitions.getById(sd.record().id()).ifPresent(
                    existing ->
                            conflicts.put(sd.record().id(), List.of(existing.sourceLocation(), sd.sourceLocation())));
        }

        if (!conflicts.isEmpty()) {
            throw new DuplicatePermissionDefinitionException(conflicts);
        }
    }

}
