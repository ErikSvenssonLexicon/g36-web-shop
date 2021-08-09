package se.lexicon.g36webshop.service.interfaces;

import org.springframework.data.domain.Pageable;
import se.lexicon.g36webshop.model.dto.product_category.ProductCategoryDTO;
import se.lexicon.g36webshop.model.entity.ProductCategory;

import java.util.Collection;

public interface ProductCategoryService extends BasicCRUD<ProductCategory, ProductCategoryDTO> {
    Collection<ProductCategory> findByFirstLetter(String firstLetter, Pageable pageable);

    Collection<ProductCategory> findByValueContains(String value);
}
