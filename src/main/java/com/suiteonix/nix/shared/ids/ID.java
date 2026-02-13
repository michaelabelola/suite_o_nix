package com.suiteonix.nix.shared.ids;

import java.util.function.Supplier;

/**
 * Represents a generic identifier wrapper.
 *
 * @param <T> the type of the underlying identifier value
 */
public interface ID<T> extends Supplier<T> {

    /**
     * @return true if the identifier value is null
     */
    default boolean isEmpty() {
        return get() == null;
    }
}
