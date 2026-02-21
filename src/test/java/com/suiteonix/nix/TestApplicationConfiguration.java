package com.suiteonix.nix;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@EnableAutoConfiguration(exclude = {
        org.jobrunr.spring.autoconfigure.JobRunrAutoConfiguration.class,
        org.jobrunr.spring.autoconfigure.storage.JobRunrSqlStorageAutoConfiguration.class
})
@ComponentScan(basePackages = "com.suiteonix.nix"
//        excludeFilters = @ComponentScan.Filter(
//                type = FilterType.ASSIGNABLE_TYPE,
//                classes = JobrunrConfig.class
//        )
)
public class TestApplicationConfiguration {
}
