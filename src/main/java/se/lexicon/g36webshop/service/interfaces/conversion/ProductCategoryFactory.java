package se.lexicon.g36webshop.service.interfaces.conversion;

import se.lexicon.g36webshop.model.dto.product_category.ProductCategoryDTO;
import se.lexicon.g36webshop.model.entity.ProductCategory;

public interface ProductCategoryFactory {

    ProductCategory createFromDTO(ProductCategoryDTO productCategoryDTO);

}
