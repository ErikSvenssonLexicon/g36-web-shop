package se.lexicon.g36webshop.service.implementation.conversion;

import org.springframework.stereotype.Service;
import se.lexicon.g36webshop.model.dto.product.ProductDTO;
import se.lexicon.g36webshop.model.entity.Product;
import se.lexicon.g36webshop.service.interfaces.conversion.ProductFactory;

@Service
public class ProductFactoryImpl implements ProductFactory {
    @Override
    public Product createFromDTO(ProductDTO productDTO) {
        if(productDTO == null) throw new IllegalArgumentException("Create aborted, cause: productDTO was null");
        Product newProduct = new Product();
        newProduct.setProductId(productDTO.getProductId());
        newProduct.setProductName(productDTO.getProductName() == null ? null : productDTO.getProductName().trim());
        newProduct.setProductPrice(productDTO.getProductPrice());
        newProduct.setDescription(productDTO.getDescription() == null ? null : productDTO.getDescription().trim());
        return newProduct;
    }
}
