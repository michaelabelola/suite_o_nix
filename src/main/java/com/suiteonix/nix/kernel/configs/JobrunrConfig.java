package com.suiteonix.nix.kernel.configs;

import lombok.RequiredArgsConstructor;
import org.jobrunr.storage.StorageProvider;
import org.jobrunr.storage.StorageProviderUtils;
import org.jobrunr.storage.sql.postgres.PostgresStorageProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class JobrunrConfig {

    @Bean
    public StorageProvider storageProvider(DataSource dataSource) {
        PostgresStorageProvider provider = new PostgresStorageProvider(dataSource);
        provider.setUpStorageProvider(StorageProviderUtils.DatabaseOptions.CREATE);
        return provider;
    }
}
