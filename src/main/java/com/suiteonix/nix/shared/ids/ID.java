package com.suiteonix.nix.shared.ids;

import jakarta.persistence.Transient;

import java.util.function.Consumer;
import java.util.function.Function;

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

    String toString();

    default <N extends ID<N, T>> N convert(Function<T, N> factory) {
        return factory.apply(get());
    }

}
