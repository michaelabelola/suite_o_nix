package com.suiteonix.nix.shared.audit;

import com.suiteonix.nix.shared.ids.ID;
import io.swagger.v3.oas.annotations.StringToClassMapItem;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(
        name = "AuditSection",
        description = "Audit information about entity creation and modification",
        example = """
                {
                  "createdBy_id": "user-123",
                  "createdDate": "2024-01-01T12:00:00Z",
                  "modifiedBy_id": "user-456",
                  "modifiedDate": "2024-06-01T15:30:00Z"
                }
                """, properties = {
        @StringToClassMapItem(key = "createdBy_id", value = String.class),
        @StringToClassMapItem(key = "createdDate", value = Instant.class),
        @StringToClassMapItem(key = "modifiedBy_id", value = String.class),
        @StringToClassMapItem(key = "modifiedDate", value = Instant.class)
})
public interface AuditSection {

    @Schema(hidden = true)
    ID<?, ?> getCreatedBy();

    Instant getCreatedDate();

    @Schema(hidden = true)
    ID<?, ?> getModifiedBy();

    Instant getModifiedDate();

}
