package com.suiteonix.nix.shared.audit;

import com.suiteonix.nix.shared.ids.NixID;
import com.suiteonix.nix.shared.principal.Principal;
import jakarta.annotation.Nullable;
import jakarta.persistence.PrePersist;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
@RequiredArgsConstructor
public class OwnerEntityListener {

    @PrePersist
    public void touchForCreate(@Nullable Object target) {
        var dd = Principal.CURRENT();
        if (target == null) return;
        if (target instanceof Ownable ownable) {
            var pri = Principal.CURRENT();
            if (!ownable.getOwnerId().isEmpty()) return;
            Principal.ID().ifPresent(nixID -> setOwnerId(ownable, nixID));
        }
    }

    private void setOwnerId(Ownable target, NixID id) {
        try {
            Method setOwnerMethod = findMethodInHierarchy(target.getClass(), "setOwnerId", NixID.class);
            setOwnerMethod.setAccessible(true);
            setOwnerMethod.invoke(target, id);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private Method findMethodInHierarchy(Class<?> clazz, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
        Class<?> currentClass = clazz;
        while (currentClass != null) {
            try {
                return currentClass.getDeclaredMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException e) {
                currentClass = currentClass.getSuperclass();
            }
        }
        throw new NoSuchMethodException("Method '" + methodName + "' not found in class hierarchy of " + clazz.getName());
    }
}
