package com.suiteonix.nix.kernel.configs;

import lombok.RequiredArgsConstructor;
import org.jobrunr.storage.StorageProvider;
import org.jobrunr.storage.StorageProviderUtils;
import org.jobrunr.storage.sql.postgres.PostgresStorageProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(name = "jobrunr.enabled", havingValue = "true", matchIfMissing = true)
public class JobrunrConfig {

    @Bean
    public StorageProvider storageProvider(DataSource dataSource) {
        PostgresStorageProvider provider = new PostgresStorageProvider(dataSource);
        provider.setUpStorageProvider(StorageProviderUtils.DatabaseOptions.CREATE);
        return provider;
    }
}
