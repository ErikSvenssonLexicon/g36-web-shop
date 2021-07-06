package se.lexicon.g36webshop.service.interfaces;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import se.lexicon.g36webshop.model.dto.product_category.ProductCategoryDTO;
import se.lexicon.g36webshop.model.entity.ProductCategory;
import se.lexicon.g36webshop.service.interfaces.BasicCRUD;

import java.util.Collection;

public interface ProductCategoryService extends BasicCRUD<ProductCategory, ProductCategoryDTO> {
    Collection<ProductCategory> findByFirstLetter(String firstLetter, Pageable pageable);

    Collection<ProductCategory> findByValueContains(String value);
}
