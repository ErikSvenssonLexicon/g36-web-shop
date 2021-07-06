package se.lexicon.g36webshop.service.implementation;

import org.springframework.stereotype.Service;
import se.lexicon.g36webshop.model.dto.ProductDTO;
import se.lexicon.g36webshop.model.entity.Product;
import se.lexicon.g36webshop.service.interfaces.ProductFactory;

@Service
public class ProductFactoryImpl implements ProductFactory {
    @Override
    public Product createFromDTO(ProductDTO productDTO) {
        if(productDTO == null) throw new IllegalArgumentException("Create aborted, cause: productDTO was null");
        Product newProduct = new Product();
        newProduct.setProductName(productDTO.getProductName().trim());
        newProduct.setProductPrice(productDTO.getProductPrice());
        newProduct.setDescription(productDTO.getDescription().trim());
        return newProduct;
    }
}
