package se.lexicon.g36webshop.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.lexicon.g36webshop.model.entity.Address;

import java.util.List;
import java.util.Optional;

public interface AddressDAO extends JpaRepository<Address, String> {


    @Query("SELECT a FROM Address a WHERE UPPER(a.city) = UPPER(:city)")
    List<Address> findByCityIgnoreCase(@Param("city") String city);

    @Query("SELECT a FROM Address a WHERE UPPER(a.street) = UPPER(:street) AND UPPER(a.zipCode) = UPPER(:zipCode) AND UPPER(a.city) = UPPER(:city) AND UPPER(a.country) = UPPER(:country)")
    Optional<Address> findByStreetZipCodeCityCountry(
            @Param("street") String street,
            @Param("zipCode") String zipCode,
            @Param("city") String city,
            @Param("country") String country
    );

}
