package com.suiteonix.db.nix.shared.interfaces;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface EmptyChecker {

    @JsonIgnore
    boolean isEmpty();
}
