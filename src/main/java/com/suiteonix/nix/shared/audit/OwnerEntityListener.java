package com.suiteonix.nix.shared.audit;

import com.suiteonix.nix.shared.ids.NixID;
import com.suiteonix.nix.shared.principal.Principal;
import jakarta.annotation.Nullable;
import jakarta.persistence.PrePersist;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
@RequiredArgsConstructor
public class OwnerEntityListener {

    @PrePersist
    public void touchForCreate(@Nullable Object target) {
        if (target == null) return;
        if (target instanceof Ownable ownable) {
            NixID currentOwnerId = ownable.getOwnerId();
            if (currentOwnerId != null && !currentOwnerId.isEmpty()) return;

            NixID principalId = Principal.ID();
            if (principalId.isEmpty()) {
                if (ownable instanceof IAuditableOwnableEntity entity) {
                    try {
                        var idField = findFieldInHierarchy(entity.getClass(), "id");
                        idField.setAccessible(true);
                        var entityId = (NixID) idField.get(entity);
                        if (entityId != null && !entityId.isEmpty()) {
                            principalId = entityId;
                        }
                    } catch (Exception e) {
                        log.warn("Could not get entity ID for owner fallback", e);
                    }
                }
            }

            principalId.ifPresent(nixID -> setOwnerId(ownable, nixID));
        }
    }

    private Field findFieldInHierarchy(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        Class<?> currentClass = clazz;
        while (currentClass != null) {
            try {
                return currentClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                currentClass = currentClass.getSuperclass();
            }
        }
        throw new NoSuchFieldException("Field '" + fieldName + "' not found in class hierarchy of " + clazz.getName());
    }

    private void setOwnerId(Ownable target, NixID id) {
        if (id.isEmpty()) return;
        try {
            Method setOwnerMethod = findMethodInHierarchy(target.getClass(), "setOwnerId", NixID.class);
            setOwnerMethod.setAccessible(true);
            setOwnerMethod.invoke(target, id);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private Method findMethodInHierarchy(Class<?> clazz, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
        Class<?> currentClass = clazz;
        while (currentClass != null) {
            try {
                return currentClass.getDeclaredMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException e) {
                currentClass = currentClass.getSuperclass();
            }
        }
        throw new NoSuchMethodException("Method '" + methodName + "' not found in class hierarchy of " + clazz.getName());
    }
}
