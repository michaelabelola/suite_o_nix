package com.suiteonix.nix.Auth.service;

import com.suiteonix.nix.Organization.services.OrgID;
import com.suiteonix.nix.shared.ids.NixID;
import org.jspecify.annotations.NonNull;

public interface AuthenticationService {
    @NonNull AuthProfile register(AuthProfile.@NonNull Register registerDto);
    AuthProfile registerOrgUserProfile(NixID id, OrgID orgID);
}
