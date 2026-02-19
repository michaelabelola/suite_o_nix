package com.suiteonix.nix.Permission.systemPermissions.annotations;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.PACKAGE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionDefinitions {
    PermissionDefinition[] value();
}
