package com.suiteonix.nix.User.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.suiteonix.nix.shared.exceptions.EX;
import com.suiteonix.nix.shared.ids.NixID;
import com.suiteonix.nix.shared.ids.NixRole;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import org.jspecify.annotations.NonNull;

@Embeddable
public record UserID(
        @Column(name = "id")
        Long value
) implements NixID {

    public static UserID NEW() {
        return FROM(NixID.NEW(NixRole.USER));
    }

    public static UserID FROM(NixID nixID) {
        if (nixID == null) return UserID.EMPTY();
        if (nixID.role() != NixRole.USER)
            throw EX.badRequest("INVALID_USER_ID", "Provided id is not a valid User ID");
        return new UserID(nixID.value());
    }

    private static UserID EMPTY() {
        return new UserID(NixRole.USER.generateID().get());
    }


    @Transient
    @Override
    public Long get() {
        return value;
    }

    @Transient
    @JsonIgnore
    public String filePath() {
        return "user/%s".formatted(value);
    }

    @Override
    public @NonNull String toString() {
        return String.valueOf(get());
    }

}
