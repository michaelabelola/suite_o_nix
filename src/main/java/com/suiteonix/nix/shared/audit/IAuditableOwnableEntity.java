package com.suiteonix.nix.shared.audit;

import com.suiteonix.nix.shared.ids.NixID;
import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.AbstractAggregateRoot;


@Getter
@Setter
@ToString
@MappedSuperclass
@RequiredArgsConstructor
@EntityListeners({NixEntityAuditListener.class, OwnerEntityListener.class})
public abstract class IAuditableOwnableEntity<T extends AbstractAggregateRoot<T>> extends AbstractAggregateRoot<T> implements IAuditable, Ownable {

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "org_id", updatable = false))
    @Setter(AccessLevel.PACKAGE)
    NixID orgID = NixID.EMPTY();

    @Embedded
    @Setter(AccessLevel.PACKAGE)
    JpaAuditSection audit = new JpaAuditSection();

    public @NonNull NixID getOrgID() {
        if (orgID == null) return NixID.EMPTY();
        return orgID;
    }
}
