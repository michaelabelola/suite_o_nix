package com.suiteonix.nix.shared.ids;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Transient;

import java.util.Objects;
import java.util.function.Supplier;

public interface GenericStringID extends Supplier<String> {

    String id();

    @JsonIgnore
    @Transient
    @Override
    default String get() {
        return id();
    }

    default boolean matches(GenericStringID obj) {
        return Objects.equals(id(), obj.id());
    }

    default boolean matches(String obj) {
        return Objects.equals(id(), obj);
    }

    @Transient
    @JsonIgnore
    default boolean isEmpty() {
        return StringUtils.isBlank(id());
    }

}
