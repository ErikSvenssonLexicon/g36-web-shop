package se.lexicon.g36webshop.service.interfaces;

import se.lexicon.g36webshop.model.dto.ProductDTO;
import se.lexicon.g36webshop.model.entity.Product;

public interface ProductFactory {
    Product createFromDTO(ProductDTO productDTO);
}
