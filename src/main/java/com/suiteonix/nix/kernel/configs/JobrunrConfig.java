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
        return new PostgresStorageProvider(dataSource, StorageProviderUtils.DatabaseOptions.CREATE);
    }

//    @Bean
//    public StorageProvider storageProvider(DataSource dataSource, JobMapper jobMapper) {
//        StorageProvider sqlStorageProviderFactory = SqlStorageProviderFactory.using(dataSource);
//        sqlStorageProviderFactory.setJobMapper(jobMapper);
//        sqlStorageProviderFactory.setUpStorageProvider(StorageProviderUtils.DatabaseOptions.CREATE);
//        return sqlStorageProviderFactory;
//    }
}
