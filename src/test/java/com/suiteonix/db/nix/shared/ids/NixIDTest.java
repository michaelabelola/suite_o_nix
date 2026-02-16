package com.suiteonix.db.nix.shared.ids;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class NixIDTest {

    @Test
    void NEW() {
    }

    @Test
    void get() {
    }

    @Test
    void of() {
    }

    @Test
    void EMPTY() {
    }

    @Test
    void id() {
    }

    @Test
    @DisplayName("NixID should support EMPTY")
    void nixId_ShouldSupportEmpty() {
        NixID emptyId = NixID.EMPTY();

        assertNotNull(emptyId);
        assertNull(emptyId.id());
    }

    @Test
    @DisplayName("NixID should support SYSTEM constant")
    void nixId_ShouldSupportSystemConstant() {
        NixID systemId = NixID.SYSTEM;

        assertNotNull(systemId);
        assertThat(systemId.id()).isEqualTo("SYSTEM");
    }
}