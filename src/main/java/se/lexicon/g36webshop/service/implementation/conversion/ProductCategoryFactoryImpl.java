package se.lexicon.g36webshop.service.implementation.conversion;

import org.springframework.stereotype.Service;
import se.lexicon.g36webshop.model.dto.product_category.ProductCategoryDTO;
import se.lexicon.g36webshop.model.entity.ProductCategory;
import se.lexicon.g36webshop.service.interfaces.conversion.ProductCategoryFactory;

@Service
public class ProductCategoryFactoryImpl implements ProductCategoryFactory {

    @Override
    public ProductCategory createFromDTO(ProductCategoryDTO productCategoryDTO) {
       if(productCategoryDTO == null) throw new IllegalArgumentException("ProductCategoryDTO productCategoryDTO was null");

       ProductCategory productCategory = new ProductCategory();
       productCategory.setCategoryId(productCategoryDTO.getCategoryId());
       productCategory.setValue(productCategoryDTO.getValue() == null ? null : productCategoryDTO.getValue().trim());
       return productCategory;
    }
}
