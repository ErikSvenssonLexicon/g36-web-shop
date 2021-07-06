package se.lexicon.g36webshop.data;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.lexicon.g36webshop.model.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryDAO extends JpaRepository<ProductCategory, String> {

    @Query("SELECT c FROM ProductCategory c WHERE UPPER(c.value) LIKE (UPPER(CONCAT(:start, '%')))")
    List<ProductCategory> findByValueStartingWith(@Param("start") String start, Pageable pageable);

    @Query("SELECT c FROM ProductCategory c WHERE UPPER(c.value) LIKE UPPER(CONCAT('%', :value, '%'))")
    List<ProductCategory> findByValue(@Param("value") String value);
}
