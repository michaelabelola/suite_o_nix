package com.suiteonix.nix.User.service;

import com.suiteonix.nix.Organization.services.OrgID;
import com.suiteonix.nix.shared.ids.NixID;

public record UserEvents() {

    public record Created(
            NixID id
    ) {
        public static Created of(NixID id) {
            return new Created(id);
        }
    }

    public record OrgUserCreated(
            NixID id,
            OrgID orgId
    ) {
        public static OrgUserCreated of(NixID id, OrgID orgId) {
            return new OrgUserCreated(id, orgId);
        }
    }
}
