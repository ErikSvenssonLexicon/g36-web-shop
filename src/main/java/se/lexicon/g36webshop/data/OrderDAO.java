package se.lexicon.g36webshop.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.lexicon.g36webshop.model.entity.Order;

import java.util.List;

public interface OrderDAO extends JpaRepository<Order, String> {
    List<Order> findByCustomerCustomerId(String customerId);

    @Query("SELECT o FROM Order o JOIN FETCH o.orderContent AS item WHERE UPPER(item.product.productName) = UPPER(:name)")
    List<Order> findByProductName(@Param("name") String name);
}
