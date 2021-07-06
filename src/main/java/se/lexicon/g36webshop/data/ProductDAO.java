package se.lexicon.g36webshop.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.lexicon.g36webshop.model.entity.Product;

import java.util.List;

public interface ProductDAO extends JpaRepository<Product, String> {

    List<Product> findByCategoriesValueIgnoreCase(String value);
    @Query("SELECT p FROM Product p WHERE UPPER(p.productName) LIKE UPPER(CONCAT('%', :productName, '%'))")
    List<Product> findByProductNameContains(@Param("productName") String productName);



}
