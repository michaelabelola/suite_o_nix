package com.suiteonix.db.nix.shared.ids;

import com.suiteonix.db.nix.shared.interfaces.EmptyChecker;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.function.Supplier;

/**
 * Represents a generic identifier wrapper.
 *
 * @param <T> the type of the underlying identifier value
 */
public interface IID<T> extends Supplier<T>, EmptyChecker {

    /**
     * @return true if the identifier value is null
     */
    @Override
    @Schema(hidden = true)
    default boolean isEmpty() {
        return get() == null;
    }
}
