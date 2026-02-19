package com.suiteonix.nix.Permission.systemPermissions.domain;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DuplicatePermissionDefinitionException extends RuntimeException {

    public DuplicatePermissionDefinitionException(Map<String, List<String>> duplicates) {
        super(buildMessage(duplicates));
    }

    private static String buildMessage(Map<String, List<String>> duplicates) {
        String details = duplicates.entrySet().stream()
                .map(e -> "  '%s' found at: %s".formatted(e.getKey(), String.join(", ", e.getValue())))
                .collect(Collectors.joining("\n"));
        return "Code contains duplicate PermissionDefinition ids:\n" + details;
    }
}
