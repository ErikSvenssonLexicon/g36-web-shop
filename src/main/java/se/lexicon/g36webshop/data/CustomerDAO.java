package se.lexicon.g36webshop.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.lexicon.g36webshop.model.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDAO extends JpaRepository<Customer, String> {
    Optional<Customer> findByEmailIgnoreCase(String email);
    List<Customer> findByShippingAddressAddressId(String addressId);
    @Query("SELECT c FROM Customer c WHERE c.shippingAddress.addressId = :addressId")
    List<Customer> findByShippingAddress(@Param("addressId") String addressId);
}
