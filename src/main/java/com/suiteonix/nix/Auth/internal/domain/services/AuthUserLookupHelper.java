package com.suiteonix.nix.Auth.internal.domain.services;

import com.suiteonix.nix.Auth.internal.domain.AuthProfileModel;
import com.suiteonix.nix.Auth.internal.infrastructure.AuthUserRepository;
import com.suiteonix.nix.shared.ValueObjects.Email;
import com.suiteonix.nix.shared.exceptions.EX;
import com.suiteonix.nix.shared.ids.NixID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class AuthUserLookupHelper {

    private final AuthUserRepository repository;

    /**
     * Finds an AuthProfileModel by email, scoped to orgID when it is a concrete (non-system, non-anonymous) ID.
     * SYSTEM = NixID.SYSTEM ("S-SYSTEM"), ANONYMOUS = NixID.EMPTY() (null id).
     */
    AuthProfileModel findByEmailScoped(String email, Long ownerId) {
        Email emailVO = Email.NEW(email);
        NixID orgID = toNixId(ownerId);
        if (isSystemOrAnonymous(orgID)) {
            return repository.findByEmail(emailVO)
                    .orElseThrow(() -> EX.notFound("AUTH_USER_NOT_FOUND", "No account found for the provided email"));
        }
        return repository.findByEmailAndOrgID(emailVO, orgID)
                .orElseThrow(() -> EX.notFound("AUTH_USER_NOT_FOUND", "No account found for the provided email"));
    }

    static boolean isSystemOrAnonymous(NixID id) {
        if (id == null) return true;
        Long raw = id.get();
        return raw == null || raw == 0 || id.equals(NixID.SYSTEM);
    }

    static NixID toNixId(Long id) {
        if (id == null || id == 0) return NixID.EMPTY();
        return NixID.of(id);
    }
}
