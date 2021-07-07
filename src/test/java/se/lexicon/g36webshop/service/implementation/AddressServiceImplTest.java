package se.lexicon.g36webshop.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.g36webshop.TestObjectSeeder;
import se.lexicon.g36webshop.model.dto.address.AddressDTO;
import se.lexicon.g36webshop.model.entity.Address;
import se.lexicon.g36webshop.model.entity.AppUser;
import se.lexicon.g36webshop.model.entity.Customer;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext
@Slf4j
class AddressServiceImplTest {

    @Autowired
    private AddressServiceImpl testObject;

    @Autowired
    private TestEntityManager em;

    private List<Address> persistedAddresses;
    private List<Customer> persistedCustomers;

    @BeforeEach
    void setUp() {
        TestObjectSeeder seeder = new TestObjectSeeder();
        List<AppUser> appUsers = seeder.appUsers().stream().map(em::persist).collect(Collectors.toList());
        List<Customer> customers = seeder.customers();
        customers.get(0).setUserCredentials(appUsers.get(0));
        customers.get(1).setUserCredentials(appUsers.get(1));
        customers.get(2).setUserCredentials(appUsers.get(2));
        customers.get(3).setUserCredentials(appUsers.get(3));
        customers.get(4).setUserCredentials(appUsers.get(4));

        persistedCustomers = customers.stream().map(em::persist).collect(Collectors.toList());

        persistedAddresses = seeder.addresses().stream().map(em::persist).collect(Collectors.toList());
        persistedCustomers.get(0).setShippingAddress(persistedAddresses.get(0));
        persistedCustomers.get(1).setShippingAddress(persistedAddresses.get(0));
        persistedCustomers.get(2).setShippingAddress(persistedAddresses.get(1));
        persistedCustomers.get(3).setShippingAddress(persistedAddresses.get(2));
        persistedCustomers.get(4).setShippingAddress(persistedAddresses.get(3));

        em.flush();

    }

    @Test
    @DisplayName("Given AddressDTO create persist new Address and return")
    void create() {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet("Kronobergsgatan 18");
        addressDTO.setZipCode("352 33");
        addressDTO.setCity("Växjö");
        addressDTO.setCountry("Sweden");

        Address result = testObject.create(addressDTO);

        assertNotNull(result);
        assertNotNull(result.getAddressId());
        assertEquals(addressDTO.getStreet(), result.getStreet());
        assertEquals("35233", result.getZipCode());
        assertEquals(addressDTO.getCity(), result.getCity());
        assertEquals(addressDTO.getCountry(), result.getCountry());
    }

    @Test
    @DisplayName("Given AddressDTO containing existing address data find and return Address from database")
    void create_existing_address() {
        Address existingAddress = persistedAddresses.get(0);
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet(existingAddress.getStreet());
        addressDTO.setZipCode(existingAddress.getZipCode());
        addressDTO.setCity(existingAddress.getCity());
        addressDTO.setCountry(existingAddress.getCountry());

        Address result = testObject.create(addressDTO);

        assertEquals(existingAddress.getAddressId(), result.getAddressId());
    }

    @Test
    void create_throws_IllegalArgumentException_on_null() {
        assertThrows(
                IllegalArgumentException.class,
                () -> testObject.create(null)
        );
    }

    @Test
    @DisplayName("Given id findById return expected object")
    void findById() {
        Address address = persistedAddresses.get(0);
        String id = address.getAddressId();

        Address result = testObject.findById(id);

        assertNotNull(result);
        assertEquals(id, address.getAddressId());
        assertEquals(address, result);
        
    }

    @Test
    @DisplayName("findAll return Collection of size 4")
    void findAll() {
        int expectedSize = 4;

        Collection<Address> result = testObject.findAll();

        assertEquals(expectedSize, result.size());
    }

    @Test
    void updateReplace() {
        String oldAddressId = persistedAddresses.get(1).getAddressId();
        Customer customer = persistedCustomers.get(2);
        customer.setShippingAddress(null);

        em.flush();

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet("Friskhetsvägen 2");
        addressDTO.setZipCode("352 52");
        addressDTO.setCity("Växjö");
        addressDTO.setCountry("Sweden");

        Address result = testObject.updateReplace(oldAddressId, addressDTO);

        em.flush();

        assertNotNull(result.getAddressId());
        assertNotEquals(oldAddressId, result.getAddressId());
    }

    @Test
    void findByCity() {
        String city = "Jönköping";
        int expectedSize = 2;

        Collection<Address> result = testObject.findByCity(city);

        assertEquals(expectedSize, result.size());
    }

    @Test
    void update() {
        Address unchanged = persistedAddresses.get(3);

        String street = "Friskhetsvägen 2";
        String city = "Växjö";
        String country = "Sweden";
        AddressDTO addressDTO = new AddressDTO();

        addressDTO.setAddressId(unchanged.getAddressId());
        addressDTO.setStreet(street);
        addressDTO.setZipCode("352 52");
        addressDTO.setCity(city);
        addressDTO.setCountry(country);

        Address result = testObject.update(unchanged.getAddressId(), addressDTO);

        assertEquals(unchanged.getAddressId(), result.getAddressId());
        assertEquals(street, result.getStreet());
        assertEquals("35252", result.getZipCode());
        assertEquals(city, result.getCity());
        assertEquals(country, result.getCountry());
    }

    @Test
    void delete() {
    }
}