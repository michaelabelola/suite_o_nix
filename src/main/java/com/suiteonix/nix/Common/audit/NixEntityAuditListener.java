package com.suiteonix.nix.Common.audit;

import com.suiteonix.nix.shared.ids.NixID;
import com.suiteonix.nix.shared.exceptions.EX;
import com.suiteonix.nix.shared.principal.Actor;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class NixEntityAuditListener {
    private final Method createdByMethod;
    private final Method updatedByMethod;

    public NixEntityAuditListener() throws NoSuchMethodException {
        createdByMethod = JpaAuditSection.class.getDeclaredMethod("setCreatedBy", NixID.class);
        createdByMethod.setAccessible(true);
        updatedByMethod = JpaAuditSection.class.getDeclaredMethod("setModifiedBy", NixID.class);
        updatedByMethod.setAccessible(true);
    }

    @PrePersist
    public void onSave(Object o) {
        if (o instanceof IAuditable auditable) {
            setAuditSectionIfNonNull(auditable);
            setCreatedBy(auditable);
        }
    }

    private void setCreatedBy(IAuditable auditable) {
        if (auditable.getAudit() != null && auditable.getAudit().getCreatedBy() != null && !auditable.getAudit().getCreatedBy().isEmpty()) return;
        Actor.CURRENT().ifPresent(actor -> {
            try {
                createdByMethod.invoke(auditable.getAudit(), actor.id());
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw EX.internalServerError("AUDIT_ERROR", "Failed to set the createdBy").serverError("message", e.getMessage());
            }
        });
    }

    private static void setAuditSectionIfNonNull(IAuditable o) {
        if (o.getAudit() != null) return;
        try {
            Field field = findFieldInHierarchy(o.getClass());
            field.setAccessible(true);
            field.set(o, JpaAuditSection.NEW());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static Field findFieldInHierarchy(Class<?> clazz) throws NoSuchFieldException {
        Class<?> currentClass = clazz;
        while (currentClass != null) {
            try {
                return currentClass.getDeclaredField("audit");
            } catch (NoSuchFieldException e) {
                currentClass = currentClass.getSuperclass();
            }
        }
        assert clazz != null;
        throw new NoSuchFieldException("Field '" + "audit" + "' not found in class hierarchy of " + clazz.getName());
    }

    @PreUpdate
    public void onUpdate(Object o) {
        if (o instanceof IAuditable auditable) {
            setAuditSectionIfNonNull(auditable);
            Actor.CURRENT().ifPresent(actor -> {
                try {
                    updatedByMethod.invoke(auditable.getAudit(), Actor.ID());
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw EX.internalServerError("AUDIT_ERROR", "Failed to set the modifiedBy").serverError("message", e.getMessage());
                }
            });
        }
    }

//    @PreUpdate
//    public void onUpdate(Object o) {
//        if (o instanceof IAuditable auditable) {
//            JpaAuditSection audit = getJpaAuditSection(auditable);
//            try {
//                var p = Actor.CURRENT();
//                if (p != null && p.id() != null)
//                    updatedByMethod.invoke(audit, p.id());
//                else
//                    updatedByMethod.invoke(audit, Actor.SYSTEM);
//            } catch (InvocationTargetException | IllegalAccessException e) {
//                throw EX.internalServerError("AUDIT_ERROR", "Failed to set the modifiedBy").prop("error", e.getMessage());
//            }
//
//
//            try {
//                Field field = Class.forName(o.getClass().getName()).getDeclaredField("audit");
//                field.setAccessible(true);
//                field.set(o, audit);
//            } catch (NoSuchFieldException | ClassNotFoundException | IllegalAccessException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }

}
