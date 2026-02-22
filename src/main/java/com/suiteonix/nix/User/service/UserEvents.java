package com.suiteonix.nix.User.service;

import com.suiteonix.nix.Organization.services.OrgID;

public record UserEvents() {

    public record Created(
            UserID id
    ) {
        public static Created of(UserID id) {
            return new Created(id);
        }
    }

    public record OrgUserCreated(
            UserID id,
            OrgID orgId
    ) {
        public static OrgUserCreated of(UserID id, OrgID orgId) {
            return new OrgUserCreated(id, orgId);
        }
    }
}
