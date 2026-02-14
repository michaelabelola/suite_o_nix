package com.suiteonix.nix.shared.ValueObjects;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test
    void NEW() {
    }

    @Test
    void isValid() {
    }

    @Test
    void checkValidity() {
    }

    @Test
    void get() {
    }

    @Test
    void isEmpty() {
    }

    @Test
    void value() {
    }

    @Test
    @DisplayName("Email validation should check format")
    void email_ShouldCheckFormat() {
        assertTrue(Email.checkValidity("valid@example.com"));
        assertTrue(Email.checkValidity("user.name@example.co.uk"));
        assertFalse(Email.checkValidity("invalid"));
        assertFalse(Email.checkValidity("@example.com"));
        assertFalse(Email.checkValidity("user@"));
        assertFalse(Email.checkValidity(null));
    }

    @Test
    @DisplayName("Email value object should normalize to lowercase")
    void email_ShouldNormalize_ToLowercase() {
        Email email = Email.NEW("Test.User@EXAMPLE.COM");

        assertThat(email.value()).isEqualTo("test.user@example.com");
    }

    @Test
    @DisplayName("Email value object should validate correctly")
    void email_ShouldValidateCorrectly() {
        Email validEmail = Email.NEW("test@example.com");

        assertNotNull(validEmail);
        assertTrue(validEmail.isValid());
        assertThat(validEmail.value()).isEqualTo("test@example.com");
    }

    @Test
    @DisplayName("Email value object should throw exception for invalid email")
    void email_ShouldThrowException_ForInvalidEmail() {
        assertThrows(Exception.class, () ->
                Email.NEW("invalid-email"));
    }
}