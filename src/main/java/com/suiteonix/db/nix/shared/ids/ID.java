package com.suiteonix.db.nix.shared.ids;

import jakarta.persistence.Transient;

import java.util.function.Consumer;

/**
 * Represents a generic identifier wrapper.
 *
 * @param <T> the type of the underlying identifier value
 */
public interface ID<SELF extends ID<SELF, T>, T> extends IID<T> {

    /**
     * @return true if the identifier value is null
     */
    @Transient
    @Override
    default boolean isEmpty() {
        return get() == null;
    }

    default void ifPresent(Consumer<SELF> id) {
        if (!isEmpty()) id.accept((SELF) this);
    }
}
