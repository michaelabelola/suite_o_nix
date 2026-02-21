package com.suiteonix.nix.Organization.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.suiteonix.nix.shared.exceptions.EX;
import com.suiteonix.nix.shared.ids.ID;
import com.suiteonix.nix.shared.ids.NixID;
import com.suiteonix.nix.shared.ids.NixRole;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import org.jspecify.annotations.NonNull;

@Embeddable
public record OrgID(
        @Column(name = "id")
        Long value
) implements ID<OrgID, Long> {

    public static OrgID NEW() {
        return from(NixID.NEW(NixRole.ORGANIZATION));
    }

    public static OrgID from(NixID nixID) {
        if (nixID == null) return OrgID.EMPTY();
        if (nixID.role() != NixRole.ORGANIZATION)
            throw EX.badRequest("INVALID_ORG_ID", "Provided id is not an Organization ID");
        return new OrgID(nixID.id());
    }

    private static OrgID EMPTY() {
        return new OrgID(NixRole.ORGANIZATION.generateID().get());
    }


    @Transient
    @Override
    public Long get() {
        return value;
    }

    @Transient
    @JsonIgnore
    public String filePath() {
        return "organization/%s/".formatted(value);
    }

    @Override
    public @NonNull String toString() {
        return String.valueOf(get());
    }
}