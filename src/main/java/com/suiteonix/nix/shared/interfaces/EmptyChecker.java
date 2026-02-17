package com.suiteonix.nix.shared.interfaces;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface EmptyChecker {

    @JsonIgnore
    boolean isEmpty();
}
