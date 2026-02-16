package com.suiteonix.db.nix.shared.ValueObjects;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordTest {

    @Test
    void newEncodedPassword() {
    }

    @Test
    void isValid() {
    }

    @Test
    void checkValidity() {
    }

    @Test
    void isEmpty() {
    }

    @Test
    void value() {
    }
    @Test
    @DisplayName("Password validation should check complexity requirements")
    void password_ShouldCheckComplexityRequirements() {
        assertFalse(Password.checkValidity("weak"));
        assertFalse(Password.checkValidity("NoNumbers!"));
        assertFalse(Password.checkValidity("nouppercase1!"));
        assertFalse(Password.checkValidity("NOLOWERCASE1!"));
        assertFalse(Password.checkValidity("NoSpecial1"));
        assertTrue(Password.checkValidity("ValidPass123!"));
    }

}