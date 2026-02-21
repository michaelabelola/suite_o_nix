package com.suiteonix.nix.Organization.services;

import com.suiteonix.nix.shared.ids.NixID;
import org.jspecify.annotations.NonNull;

public record OrgEvents() {

    public record Created(
            OrgID orgId,
            NixID registerer
    ) {

        public static @NonNull Created of(OrgID orgId, NixID registerer) {
            return new Created(orgId, registerer);
        }
    }
}
