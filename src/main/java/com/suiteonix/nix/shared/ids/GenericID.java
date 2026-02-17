package com.suiteonix.db.nix.shared.ids;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;

import java.util.Objects;
import java.util.function.Supplier;

@Embeddable
public interface GenericID extends Supplier<Long> {
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonDeserialize(using = FromStringDeserializer.class)
    Long id();

    @JsonIgnore
    @Transient
    @Override
    default Long get() {
        return id();
    }

    @JsonIgnore
    @Transient
    default String asString() {
        return String.valueOf(id());
    }

    default boolean matches(GenericID obj) {
        return Objects.equals(id(), obj.id());
    }

    default boolean matches(Long obj) {
        return Objects.equals(id(), obj);
    }

    @Transient
    @JsonIgnore
    default boolean isEmpty() {
        return id() == null;
    }

}
