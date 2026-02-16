package com.suiteonix.db.nix.shared.ids;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

@Embeddable
@Access(AccessType.PROPERTY)
@Schema(name = "snowflake")
public record Snowflake(
        Long id
) implements GenericID {
    private static final Snowflake SYSTEM_ID = Snowflake.of(0L);

    Snowflake() {
        this(null);
    }

    public static @NonNull Snowflake of(Long id) {
        return new Snowflake(id);
    }

    public static @NonNull Snowflake nextId() {
        return SnowflakeIdGenerator.INSTANCE.nextSnowflake();
    }

    @Transient
    @JsonIgnore
    public boolean isEmpty() {
        return id() == null;
    }

    public static @NonNull Snowflake ofEmpty() {
        return new Snowflake(null);
    }

    @Transient
    @JsonIgnore
    @NotNull
    @Override
    public String toString() {
        return String.valueOf(id());
    }
}
