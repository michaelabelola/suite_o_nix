package com.suiteonix.nix.Permission.systemPermissions.aop;

import com.suiteonix.nix.Permission.systemPermissions.annotations.Allow;
import com.suiteonix.nix.Permission.systemPermissions.domain.PermissionContext;
import com.suiteonix.nix.Permission.systemPermissions.domain.PermissionDefinitionRecord;
import com.suiteonix.nix.Permission.systemPermissions.service.PermissionDefinitionService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Optional;

@Aspect
@Component
@RequiredArgsConstructor
class AllowAspect {

    private final PermissionDefinitionService permissionDefinitionService;

    @Pointcut("@annotation(com.suiteonix.nix.Permission.systemPermissions.annotations.Allow) " +
              "|| @within(com.suiteonix.nix.Permission.systemPermissions.annotations.Allow)")
    public void allowPointcut() {}

    @Around("allowPointcut()")
    public Object resolvePermission(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            resolveAndSetContext(joinPoint);
            return joinPoint.proceed();
        } finally {
            PermissionContext.clear();
        }
    }

    private void resolveAndSetContext(ProceedingJoinPoint joinPoint) {
        String permissionId = resolvePermissionId(joinPoint);
        if (permissionId == null || permissionId.isBlank()) return;

        Optional<PermissionDefinitionRecord> definition =
                permissionDefinitionService.getPermissionDefinitionById(permissionId);
        definition.ifPresent(PermissionContext::set);
    }

    private String resolvePermissionId(ProceedingJoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();

        // Method-level @Allow takes precedence over class-level
        Allow methodAllow = AnnotationUtils.getAnnotation(method, Allow.class);
        if (methodAllow != null) {
            return (String) AnnotationUtils.getValue(methodAllow, "value");
        }

        Allow classAllow = AnnotationUtils.getAnnotation(joinPoint.getTarget().getClass(), Allow.class);
        if (classAllow != null) {
            return (String) AnnotationUtils.getValue(classAllow, "value");
        }

        return null;
    }
}
