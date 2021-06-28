package se.lexicon.g36webshop.data;

import org.springframework.data.jpa.repository.JpaRepository;
import se.lexicon.g36webshop.model.entity.Product;

public interface ProductDAO extends JpaRepository<Product, String> {
}
