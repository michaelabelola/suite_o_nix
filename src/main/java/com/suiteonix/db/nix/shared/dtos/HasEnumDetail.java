package com.suiteonix.db.nix.shared.dtos;


public interface HasEnumDetail {
    String name();
    String getDescription();
    String getDisplayName();

    default EnumDetail to(){
        return EnumDetail.of(this);
    }
}
