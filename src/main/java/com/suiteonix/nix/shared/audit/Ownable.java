package com.suiteonix.db.nix.shared.audit;

import com.suiteonix.db.nix.shared.ids.NixID;
import org.jspecify.annotations.NonNull;

/**
 * Interface for entities that have a defined owner.
 */
public interface Ownable {
    /**
     * Returns the unique identifier of the owner.
     *
     * @return the owner's ID
     * @apiNote must not return null. Null value should be an empty id. {@link NixID#EMPTY()}
     */
    @NonNull NixID getOwnerId();
}
