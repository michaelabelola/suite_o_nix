package com.suiteonix.db.nix.Auth.service;

import com.suiteonix.db.nix.shared.ids.NixID;

public interface AuthUserEvents {

    record Registered(NixID id) {

    }

    record Updated(NixID id) {

    }

    record Deleted(NixID id) {

    }

    record LoggedIn(NixID id) {

    }

    record LoggedOut(NixID id) {

    }

    record Authenticated(NixID id) {

    }
}
