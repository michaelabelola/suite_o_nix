package com.suiteonix.nix.shared.audit;

import com.suiteonix.nix.shared.ids.NixID;
import com.suiteonix.nix.shared.principal.Principal;
import jakarta.annotation.Nullable;
import jakarta.persistence.PrePersist;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;

@Slf4j
@RequiredArgsConstructor
public class OwnerEntityListener {

    @PrePersist
    public void touchForCreate(@Nullable Object target) {
        if (target == null) return;
        if (target instanceof Ownable ownable) {
            if (ownable.getOwnerId().isEmpty()) return;
            setOwnerId(ownable, Principal.ID());
        }
    }

    private void setOwnerId(Ownable target, NixID id) {
        if (id.isEmpty()) return;
        try {
            var setOwnerMethod = target.getClass().getDeclaredMethod("setOwnerId", NixID.class);
            setOwnerMethod.setAccessible(true);
            setOwnerMethod.invoke(target, id);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
