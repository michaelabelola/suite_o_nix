package com.suiteonix.nix.shared.audit;

import com.suiteonix.nix.shared.ids.NixID;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.AbstractAggregateRoot;


@Getter
@Setter
@ToString
@MappedSuperclass
@RequiredArgsConstructor
@EntityListeners({NixEntityAuditListener.class, OwnerEntityListener.class})
public abstract class IAuditableOwnableEntity<T extends AbstractAggregateRoot<T>,R> extends AbstractAggregateRoot<T> implements IAuditable {

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "owner_id", updatable = false))
    @Setter(AccessLevel.PROTECTED)
    NixID ownerId;

    @Embedded
    @Setter(AccessLevel.PUBLIC)
    JpaAuditSection audit;
}
