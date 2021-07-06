package se.lexicon.g36webshop;

import se.lexicon.g36webshop.model.entity.Product;
import se.lexicon.g36webshop.model.entity.ProductCategory;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class TestObjectSeeder {

    public List<Product> products(){
        return Arrays.asList(
                new Product(null, "Product1", "Description1", BigDecimal.valueOf(9.90), null),
                new Product(null, "Product2", "Description2", BigDecimal.valueOf(9.90), null),
                new Product(null, "Product3", "Description3", BigDecimal.valueOf(9.90), null),
                new Product(null, "Product4", "Description4", BigDecimal.valueOf(9.90), null),
                new Product(null, "Product5", "Description5", BigDecimal.valueOf(9.90), null)
        );
    }


    public List<ProductCategory> categories(){
        return Arrays.asList(
                new ProductCategory(null, "Category1", null),
                new ProductCategory(null, "Category2", null),
                new ProductCategory(null, "Category3", null)
        );
    }

}
