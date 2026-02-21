package com.suiteonix.nix.shared.ValueObjects;

import com.suiteonix.nix.shared.interfaces.EmptyChecker;
import com.suiteonix.nix.Common.ddd.ValueObject;
import com.suiteonix.nix.shared.exceptions.EX;
import com.suiteonix.nix.shared.interfaces.ValidityChecker;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.jspecify.annotations.Nullable;

import java.util.function.Supplier;

@Embeddable
@ValueObject
public record Phone(
        @Column(name = "phone")
        String value
) implements Supplier<String>, EmptyChecker, ValidityChecker {

    public Phone {
        if (value != null && !checkValidity(value))
            throw EX.badRequest("INVALID_PHONE", "The provided value number is invalid");
    }

    public static Phone NEW(String phone) {
        if (phone == null) return new Phone(phone);

        if (!checkValidity(phone))
            throw EX.badRequest("INVALID_PHONE", "The provided value number is invalid");

        return new Phone(phone);
    }

    @Override
    public boolean isValid() {
        return checkValidity(value());
    }

    public static boolean checkValidity(String phone) {
        return phone != null && phone.matches("^\\+?[1-9]\\d{1,14}$");
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
