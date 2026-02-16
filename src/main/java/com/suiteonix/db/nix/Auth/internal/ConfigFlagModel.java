package com.suiteonix.db.nix.Auth.internal;

import lombok.Data;

@Data
public class ConfigFlagModel {
    String name;
    boolean enabled;
    ManagementType selfManaged;
}
