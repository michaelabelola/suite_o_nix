package com.suiteonix.nix.Common.audit;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.suiteonix.nix.shared.ids.NixID;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.Instant;

@Embeddable
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PACKAGE)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JpaAuditSection implements AuditSection, Serializable {
    //    @Pattern(regexp = "^[a-zA-Z0-9_]*$")
    @CreatedBy
    @Embedded
    @JsonUnwrapped(prefix = "createdBy_")
    @AttributeOverride(name = "id", column = @Column(name = "created_by", updatable = false))
    NixID createdBy = NixID.EMPTY();

    @CreatedDate
    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    Instant createdDate;

    @Embedded
    @LastModifiedBy
    @JsonUnwrapped(prefix = "modifiedBy_")
    @AttributeOverride(name = "id", column = @Column(name = "modified_by"))
    NixID modifiedBy = NixID.EMPTY();

    @LastModifiedDate
    @Column(name = "modified_date")
    @UpdateTimestamp
    Instant modifiedDate;

    protected JpaAuditSection() {
    }


    public static AuditSection EMPTY() {
        return new JpaAuditSection();
    }

    public static Object NEW() {
        return new JpaAuditSection();
    }
}