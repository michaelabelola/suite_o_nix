package com.suiteonix.nix.Permission.systemPermissions.annotations;

import com.suiteonix.nix.shared.NixModule;
import com.suiteonix.nix.shared.ids.NixRole;

import java.lang.annotation.*;

@Repeatable(PermissionDefinitions.class)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.PACKAGE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionDefinition {
    String id();
    String name();
    NixRole[] roles() default {};
    NixModule module();
    String[] action() default {};
}
