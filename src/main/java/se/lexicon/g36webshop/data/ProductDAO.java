package se.lexicon.g36webshop.data;

import org.springframework.data.jpa.repository.JpaRepository;
import se.lexicon.g36webshop.model.entity.Product;

import java.util.Collection;
import java.util.List;

public interface ProductDAO extends JpaRepository<Product, String> {

    List<Product> findByCategoriesValueIgnoreCase(String value);
    List<Product> findByCategoriesValueInIgnoreCase(Collection<String> values);

}
