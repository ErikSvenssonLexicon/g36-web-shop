package se.lexicon.g36webshop.data;

import org.springframework.data.jpa.repository.JpaRepository;
import se.lexicon.g36webshop.model.entity.OrderItem;

public interface OrderItemDAO extends JpaRepository<OrderItem, String> {
}
