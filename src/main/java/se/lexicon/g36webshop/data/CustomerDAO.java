package se.lexicon.g36webshop.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.lexicon.g36webshop.model.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDAO extends JpaRepository<Customer, String> {

    @Query("SELECT c FROM Customer c WHERE UPPER(c.firstName) LIKE UPPER(CONCAT('%', :name, '%')) OR UPPER(c.lastName) LIKE UPPER(CONCAT('%', :name, '%'))")
    List<Customer> findByNameContains(@Param("name") String name);
    Optional<Customer> findByEmailIgnoreCase(String email);
    @Query("SELECT c FROM Customer c WHERE c.shippingAddress.addressId = :addressId")
    List<Customer> findByShippingAddress(@Param("addressId") String addressId);
}
