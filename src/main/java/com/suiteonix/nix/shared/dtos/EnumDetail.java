package com.suiteonix.nix.shared.dtos;


public record EnumDetail(
        String name,
        String description,
        String displayName
) {
    public static EnumDetail of(HasEnumDetail enumObj) {
        return new EnumDetail(
                enumObj.name(),
                enumObj.getDescription(),
                enumObj.getDisplayName()
        );
    }
}
