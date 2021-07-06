package se.lexicon.g36webshop.service.interfaces.conversion;

import se.lexicon.g36webshop.model.dto.product.ProductDTO;
import se.lexicon.g36webshop.model.entity.Product;

public interface ProductFactory {
    Product createFromDTO(ProductDTO productDTO);
}
