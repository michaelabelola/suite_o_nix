package com.suiteonix.nix.shared.ValueObjects;

import com.suiteonix.nix.shared.interfaces.EmptyChecker;
import com.suiteonix.nix.shared.ddd.ValueObject;
import com.suiteonix.nix.shared.exceptions.EX;
import com.suiteonix.nix.shared.interfaces.ValidityChecker;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.jspecify.annotations.Nullable;

import java.util.function.Supplier;


@Embeddable
@ValueObject
public record Email(
        @Column(name = "email")
        String value
) implements Supplier<String>, EmptyChecker, ValidityChecker {

    public Email {
        value = value.toLowerCase();
    }

    public static Email NEW(String email) {
        if (email == null) return new Email(email);
        email = email.toLowerCase();

        if (!checkValidity(email))
            throw EX.badRequest("INVALID_EMAIL", "The provided email is invalid");

        return new Email(email);
    }

    @Override
    public boolean isValid() {
        return checkValidity(value());
    }

    public static boolean checkValidity(String mail) {
        return mail != null && mail.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    @Override
    public @Nullable String get() {
        return value();
    }

    @Override
    public boolean isEmpty() {
        return value == null || value.isEmpty();
    }

//    public String country() {
//        if (value().startsWith("+"))
//    }
}
