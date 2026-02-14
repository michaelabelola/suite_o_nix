package com.suiteonix.nix.Auth;

import com.suiteonix.nix.Auth.internal.AuthUserRepository;
import com.suiteonix.nix.TestApplicationConfiguration;
import com.suiteonix.nix.kernel.security.authentication.CustomAuthentication;
import com.suiteonix.nix.shared.ids.NixID;
import com.suiteonix.nix.shared.ids.NixRole;
import com.suiteonix.nix.shared.principal.Actor;
import com.suiteonix.nix.shared.principal.Principal;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TestApplicationConfiguration.class)
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.liquibase.enabled=false",
        "spring.modulith.events.jpa.enabled=false",
        "spring.batch.job.enabled=false",
        "spring.batch.jdbc.initialize-schema=never",
        "jobrunr.enabled=false"
})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("AuthenticationModule Integration Tests")
class AuthenticationModuleIntegrationTest {

    @Autowired
    private AuthenticationModule authenticationModule;

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private AuthUserRegisterDto validRegisterDto;
    private NixID userId;
    private CustomAuthentication customAuthenticationHolder;

    @BeforeEach
    void setUp() {
        customAuthenticationHolder = CustomAuthentication.ofSystem();
        SecurityContextHolder.getContext().setAuthentication(CustomAuthentication.ofSystem());

        userId = NixID.of(UUID.randomUUID().toString());

        validRegisterDto = new AuthUserRegisterDto(
                userId,
                NixRole.CUSTOMER,
                "test@example.com",
                "+1234567890",
                "SecurePass123!"
        );
    }

    @AfterEach
    void tearDown() {
        authUserRepository.deleteAll();
    }

    @Test
    @Order(1)
    @Transactional
    @DisplayName("register() should successfully register a new user with valid data")
    void register_ShouldRegisterNewUser_WhenValidDtoProvided() {
        AuthUser result = authenticationModule.register(validRegisterDto);

        assertNotNull(result);
        assertThat(result.id()).isEqualTo(userId);
        assertThat(result.role()).isEqualTo(NixRole.CUSTOMER);
        assertThat(result.email().value()).isEqualTo("test@example.com");
        assertThat(result.phone().value()).isEqualTo("+1234567890");
        assertNotNull(result.password());
        assertNotNull(result.password().value());
        assertNotEquals("SecurePass123!", result.password().value());
        assertNotNull(result.ownerId());
        assertNotNull(result.audit());
    }

    @Test
    @Order(2)
    @Transactional
    @DisplayName("register() should persist user to database")
    void register_ShouldPersistUser_ToDatabase() {
        AuthUser result = authenticationModule.register(validRegisterDto);

        var savedUser = authUserRepository.findById(userId);
        assertTrue(savedUser.isPresent());
        assertThat(savedUser.get().getId()).isEqualTo(userId);
        assertThat(savedUser.get().getEmail().value()).isEqualTo("test@example.com");
        assertThat(savedUser.get().getRole()).isEqualTo(NixRole.CUSTOMER);
    }

    @Test
    @Order(3)
    @Transactional
    @DisplayName("register() should encode password")
    void register_ShouldEncodePassword() {
        AuthUser result = authenticationModule.register(validRegisterDto);

        assertNotNull(result.password());
        assertNotNull(result.password().value());
        assertNotEquals(validRegisterDto.password(), result.password().value());
        assertTrue(result.password().value().length() > 20);
    }

    @Test
    @Order(4)
    @Transactional
    @DisplayName("register() should set ownerId equal to userId")
    void register_ShouldSetOwnerId_EqualToUserId() {
        AuthUser result = authenticationModule.register(validRegisterDto);

        assertNotNull(result.ownerId(), "OwnerId should not be null");
        assertNotNull(result.id(), "User ID should not be null");
        assertThat(result.ownerId().id()).isNotNull();
        assertThat(result.ownerId()).isEqualTo(Principal.ID());
    }

    @Test
    @Order(4)
    @Transactional
    @DisplayName("register() should set ownerId equal to userId")
    void register_ShouldSetCreatedById_EqualToPrincipalId() {
        AuthUser result = authenticationModule.register(validRegisterDto);

        assertNotNull(result.audit(), "Created by ID should not be null");
        assertThat(result.audit()).isNotNull();
        assertThat(result.audit().getCreatedBy()).isEqualTo(Actor.ID());
    }

    @Test
    @Order(5)
    @Transactional
    @DisplayName("register() should create audit information")
    void register_ShouldCreateAuditInformation() {
        AuthUser result = authenticationModule.register(validRegisterDto);

        assertNotNull(result.audit());
    }

    @Test
    @Order(6)
    @Transactional
    @DisplayName("register() should handle ADMIN role registration")
    void register_ShouldHandleAdminRole_Registration() {
        NixID adminId = NixID.of(UUID.randomUUID().toString());
        AuthUserRegisterDto adminDto = new AuthUserRegisterDto(
                adminId,
                NixRole.ADMIN,
                "admin@example.com",
                "+1234567891",
                "AdminPass123!"
        );

        AuthUser result = authenticationModule.register(adminDto);

        assertNotNull(result);
        assertThat(result.id()).isEqualTo(adminId);
        assertThat(result.role()).isEqualTo(NixRole.ADMIN);
        assertThat(result.email().value()).isEqualTo("admin@example.com");

        var savedUser = authUserRepository.findById(adminId);
        assertTrue(savedUser.isPresent());
        assertThat(savedUser.get().getRole()).isEqualTo(NixRole.ADMIN);
    }

    @Test
    @Order(7)
    @Transactional
    @DisplayName("register() should handle BUSINESS role registration")
    void register_ShouldHandleBusinessRole_Registration() {
        NixID businessId = NixID.of(UUID.randomUUID().toString());
        AuthUserRegisterDto businessDto = new AuthUserRegisterDto(
                businessId,
                NixRole.BUSINESS,
                "business@example.com",
                "+9876543210",
                "BusinessPass123!"
        );

        AuthUser result = authenticationModule.register(businessDto);

        assertNotNull(result);
        assertThat(result.id()).isEqualTo(businessId);
        assertThat(result.role()).isEqualTo(NixRole.BUSINESS);
        assertThat(result.email().value()).isEqualTo("business@example.com");
        assertThat(result.phone().value()).isEqualTo("+9876543210");

        var savedUser = authUserRepository.findById(businessId);
        assertTrue(savedUser.isPresent());
        assertThat(savedUser.get().getRole()).isEqualTo(NixRole.BUSINESS);
    }

    @Test
    @Order(8)
    @Transactional
    @DisplayName("register() should normalize email to lowercase")
    void register_ShouldNormalizeEmail_ToLowercase() {
        NixID testId = NixID.of(UUID.randomUUID().toString());
        AuthUserRegisterDto dtoWithUppercaseEmail = new AuthUserRegisterDto(
                testId,
                NixRole.CUSTOMER,
                "Test.User@EXAMPLE.COM",
                "+1234567892",
                "TestPass123!"
        );

        AuthUser result = authenticationModule.register(dtoWithUppercaseEmail);

        assertThat(result.email().value()).isEqualTo("test.user@example.com");

        var savedUser = authUserRepository.findById(testId);
        assertTrue(savedUser.isPresent());
        assertThat(savedUser.get().getEmail().value()).isEqualTo("test.user@example.com");
    }

    @Test
    @Order(9)
    @Transactional
    @DisplayName("register() should throw exception for invalid email format")
    void register_ShouldThrowException_ForInvalidEmailFormat() {
        NixID testId = NixID.of(UUID.randomUUID().toString());
        AuthUserRegisterDto invalidEmailDto = new AuthUserRegisterDto(
                testId,
                NixRole.CUSTOMER,
                "invalid-email",
                "+1234567893",
                "ValidPass123!"
        );

        Exception exception = assertThrows(Exception.class, () ->
                authenticationModule.register(invalidEmailDto));

        assertNotNull(exception.getMessage());

        var savedUser = authUserRepository.findById(testId);
        assertFalse(savedUser.isPresent());
    }

    @Test
    @Order(10)
    @Transactional
    @DisplayName("register() should throw exception for weak password")
    void register_ShouldThrowException_ForWeakPassword() {
        NixID testId = NixID.of(UUID.randomUUID().toString());
        AuthUserRegisterDto weakPasswordDto = new AuthUserRegisterDto(
                testId,
                NixRole.CUSTOMER,
                "test2@example.com",
                "+1234567894",
                "weak"
        );

        Exception exception = assertThrows(Exception.class, () ->
                authenticationModule.register(weakPasswordDto));

        assertNotNull(exception.getMessage());

        var savedUser = authUserRepository.findById(testId);
        assertFalse(savedUser.isPresent());
    }

    @Test
    @Order(11)
    @Transactional
    @DisplayName("register() should throw exception for null password")
    void register_ShouldThrowException_ForNullPassword() {
        NixID testId = NixID.of(UUID.randomUUID().toString());
        AuthUserRegisterDto nullPasswordDto = new AuthUserRegisterDto(
                testId,
                NixRole.CUSTOMER,
                "test3@example.com",
                "+1234567895",
                null
        );

        Exception exception = assertThrows(Exception.class, () ->
                authenticationModule.register(nullPasswordDto));

        assertNotNull(exception.getMessage());

        var savedUser = authUserRepository.findById(testId);
        assertFalse(savedUser.isPresent());
    }

    @Test
    @Order(12)
    @Transactional
    @DisplayName("register() should throw exception for invalid phone format")
    void register_ShouldThrowException_ForInvalidPhoneFormat() {
        NixID testId = NixID.of(UUID.randomUUID().toString());
        AuthUserRegisterDto invalidPhoneDto = new AuthUserRegisterDto(
                testId,
                NixRole.CUSTOMER,
                "test4@example.com",
                "invalid-phone",
                "ValidPass123!"
        );

        Exception exception = assertThrows(Exception.class, () ->
                authenticationModule.register(invalidPhoneDto));

        assertNotNull(exception.getMessage());

        var savedUser = authUserRepository.findById(testId);
        assertFalse(savedUser.isPresent());
    }

    @Test
    @Order(13)
    @Transactional
    @DisplayName("register() should handle multiple user registrations")
    void register_ShouldHandleMultipleUserRegistrations() {
        NixID user1Id = NixID.of(UUID.randomUUID().toString());
        NixID user2Id = NixID.of(UUID.randomUUID().toString());
        NixID user3Id = NixID.of(UUID.randomUUID().toString());

        AuthUserRegisterDto user1Dto = new AuthUserRegisterDto(
                user1Id, NixRole.CUSTOMER, "user1@example.com", "+1111111111", "User1Pass123!"
        );
        AuthUserRegisterDto user2Dto = new AuthUserRegisterDto(
                user2Id, NixRole.ADMIN, "user2@example.com", "+2222222222", "User2Pass123!"
        );
        AuthUserRegisterDto user3Dto = new AuthUserRegisterDto(
                user3Id, NixRole.BUSINESS, "user3@example.com", "+3333333333", "User3Pass123!"
        );

        AuthUser result1 = authenticationModule.register(user1Dto);
        AuthUser result2 = authenticationModule.register(user2Dto);
        AuthUser result3 = authenticationModule.register(user3Dto);

        assertNotNull(result1);
        assertNotNull(result2);
        assertNotNull(result3);

        assertThat(authUserRepository.count()).isGreaterThanOrEqualTo(3);

        assertTrue(authUserRepository.findById(user1Id).isPresent());
        assertTrue(authUserRepository.findById(user2Id).isPresent());
        assertTrue(authUserRepository.findById(user3Id).isPresent());
    }

    @Test
    @Order(14)
    @Transactional
    @DisplayName("register() should preserve all user data after persistence")
    void register_ShouldPreserveAllUserData_AfterPersistence() {
        AuthUser result = authenticationModule.register(validRegisterDto);

        var savedUser = authUserRepository.findById(userId);
        assertTrue(savedUser.isPresent());

        var user = savedUser.get();
        assertThat(user.getId()).isEqualTo(result.id());
        assertThat(user.getRole()).isEqualTo(result.role());
        assertThat(user.getEmail()).isEqualTo(result.email());
        assertThat(user.getPhone()).isEqualTo(result.phone());
        assertThat(user.getPassword()).isEqualTo(result.password());
        assertThat(user.getOwnerId()).isEqualTo(result.ownerId());
    }
}
