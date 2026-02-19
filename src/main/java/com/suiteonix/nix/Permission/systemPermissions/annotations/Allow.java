package com.suiteonix.nix.Permission.systemPermissions.annotations;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Allow {
    @AliasFor("permissionDefinitionId")
    String value() default "";

    @AliasFor("value")
    String permissionDefinitionId() default "";
}
