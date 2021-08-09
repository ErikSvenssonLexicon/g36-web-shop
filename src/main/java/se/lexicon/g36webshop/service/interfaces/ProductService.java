package se.lexicon.g36webshop.service.interfaces;

import se.lexicon.g36webshop.model.dto.product.ProductDTO;
import se.lexicon.g36webshop.model.entity.Product;

import java.util.Collection;
public interface ProductService extends BasicCRUD<Product, ProductDTO> {

    Collection<Product> findByProductCategoryValue(String value);
    Collection<Product> findByProductNameContains(String productName);
    Product addProductCategory(String productId, String categoryId);
    Product removeProductCategory(String productId, String categoryId);
}
