package com.suiteonix.db.nix.shared.ValueObjects;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PhoneTest {

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
    @DisplayName("Phone value object should be created with valid data")
    void phone_ShouldBeCreated_WithValidData() {
        Phone phone = Phone.NEW("+1234567890");

        assertNotNull(phone);
        assertThat(phone.value()).isEqualTo("+1234567890");
    }

    @Test
    @DisplayName("Phone value object should validate correctly")
    void phone_ShouldValidateCorrectly() {
        Phone validPhone = Phone.NEW("+9876543210");

        assertNotNull(validPhone);
        assertTrue(validPhone.isValid());
    }

}