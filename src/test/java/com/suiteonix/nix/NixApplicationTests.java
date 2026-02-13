package com.suiteonix.nix;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

@SpringBootTest
class NixApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void createApplicationModuleModel() {
        ApplicationModules modules = ApplicationModules.of(NixApplication.class);
        modules.forEach(System.out::println);
    }

    @Test
    void verifiesModularStructure() {
        ApplicationModules modules = ApplicationModules.of(NixApplication.class);
        modules.verify();
    }
    @Test
    void createDocument() {
        ApplicationModules modules = ApplicationModules.of(NixApplication.class);
        new Documenter(modules).writeDocumentation();
    }
}
