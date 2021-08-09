package se.lexicon.g36webshop.service.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.g36webshop.TestObjectSeeder;
import se.lexicon.g36webshop.model.dto.app_user.AppUserDTO;
import se.lexicon.g36webshop.model.entity.AppUser;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext
class AppUserServiceImplTest {

    @Autowired
    private AppUserServiceImpl testObject;
    @Autowired
    private TestEntityManager em;

    private String testUserId;
    private String testUsername;
    private String testPassword;

    @BeforeEach
    void setUp() {
        List<AppUser> persistedAppUsers = new TestObjectSeeder().appUsers().stream()
                .map(em::persist)
                .collect(Collectors.toList());

        testUserId = persistedAppUsers.get(0).getUserId();
        testUsername = persistedAppUsers.get(0).getUsername();
        testPassword = persistedAppUsers.get(0).getPassword();
    }

    @Test
    void create() {
        AppUserDTO appUserDTO = new AppUserDTO();
        appUserDTO.setUsername("nisse");
        appUserDTO.setPassword("nisse123");

        AppUser result = testObject.create(appUserDTO);
        assertNotNull(result);
        assertNotNull(result.getUserId());
        assertEquals(appUserDTO.getUsername(), result.getUsername());
        assertEquals(appUserDTO.getPassword(), result.getPassword());
    }

    @Test
    void findByUsername() {
        AppUser result = testObject.findByUsername(testUsername);
        assertNotNull(result);
        assertEquals(testUsername, result.getUsername());
    }

    @Test
    void findById() {
        AppUser result = testObject.findById(testUserId);
        assertNotNull(result);
        assertEquals(testUserId, result.getUserId());
    }

    @Test
    void findAll() {
        int expectedSize = 5;
        assertEquals(expectedSize, testObject.findAll().size());
    }

    @Test
    void update() {
        AppUserDTO appUserDTO = new AppUserDTO();
        appUserDTO.setUserId(testUserId);
        appUserDTO.setUsername("vader");
        appUserDTO.setPassword(null);

        AppUser result = testObject.update(testUserId, appUserDTO);
        assertEquals(testUserId, result.getUserId());
        assertEquals(appUserDTO.getUsername(), result.getUsername());
        assertEquals(testPassword, result.getPassword());
    }

    @Test
    void delete() {
        testObject.delete(testUserId);
        assertNull(em.find(AppUser.class, testUserId));
    }
}