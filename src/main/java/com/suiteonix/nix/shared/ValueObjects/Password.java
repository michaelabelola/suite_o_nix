package com.suiteonix.nix.shared.ValueObjects;

import com.suiteonix.nix.shared.interfaces.EmptyChecker;
import com.suiteonix.nix.shared.ddd.ValueObject;
import com.suiteonix.nix.shared.exceptions.EX;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.jspecify.annotations.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;

@Embeddable
@ValueObject
public record Password(
        @Column(name = "password")
        String value
) implements EmptyChecker {

    public static Password NewEncodedPassword(String rawPassword, @NonNull PasswordEncoder encoder) {
        if (rawPassword == null)
            throw EX.badRequest("INVALID_PASSWORD", "The provided password cannot be null");

        if (!checkValidity(rawPassword))
            throw EX.badRequest("INVALID_PASSWORD", "The provided password is invalid");

        return new Password(encoder.encode(rawPassword));
    }

    public boolean isValid() {
        return checkValidity(value());
    }

    public static boolean checkValidity(String password) {
        return password != null && password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    }

    @Override
    public boolean isEmpty() {
        return value == null || value.isEmpty();
    }
}
