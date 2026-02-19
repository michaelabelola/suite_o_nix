package com.suiteonix.nix.kernel.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@RequiredArgsConstructor
public class EventPublicationColumnPatch {
    private final JdbcTemplate jdbcTemplate;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        patchEventPublicationColumns();
    }

    private void patchEventPublicationColumns() {
        try {
            jdbcTemplate.execute("ALTER TABLE event_publication ALTER COLUMN serialized_event TYPE TEXT;");
            System.out.println("[PATCH] event_publication columns patched to TEXT");
        } catch (Exception e) {
            System.out.println("[PATCH] Could not patch columns: " + e.getMessage());
        }
    }
}
