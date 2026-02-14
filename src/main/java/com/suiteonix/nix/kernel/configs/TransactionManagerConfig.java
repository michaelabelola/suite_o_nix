package com.suiteonix.nix.kernel.configs;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class TransactionManagerConfig {

    /**
     * Make JPA transaction manager the default transaction manager
     */
    @Bean(name = "transactionManager")
    @Primary
    public PlatformTransactionManager jpaTransactionManager(
            EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

}
