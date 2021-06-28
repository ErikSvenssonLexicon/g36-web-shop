package se.lexicon.g36webshop.data;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.lexicon.g36webshop.model.entity.Address;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
public class AddressDAOTest {

    @Autowired
    private AddressDAO addressDAO;

    @Test
    void save() {
        Address address = new Address(null, "Hjalmar petris väg 32", "352 46", "Växjö", "Sweden");
        addressDAO.save(address);


        List<Address> result = addressDAO.findByCityIgnoreCase("växJö");

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }
}
