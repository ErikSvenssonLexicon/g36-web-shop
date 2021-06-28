package se.lexicon.g36webshop.data;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.lexicon.g36webshop.model.entity.Address;

@DataJpaTest
public class AddressDAOTest {

    @Autowired
    private AddressDAO addressDAO;

    @Test
    void save() {
        Address address = new Address(null, "Hjalmar petris väg 32", "352 46", "Växjö", "Sweden");
        Address persisted = addressDAO.save(address);
        System.out.println(persisted);
    }
}
