package se.lexicon.g36webshop.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.lexicon.g36webshop.model.entity.Address;

import java.util.List;

public interface AddressDAO extends JpaRepository<Address, String> {

    List<Address> findByCityIgnoreCase(String city);
    @Query("SELECT a FROM Address a WHERE UPPER(a.city) = UPPER(:city)")
    List<Address> findByCityIgnoreCaseJPQL(@Param("city") String city);

}
