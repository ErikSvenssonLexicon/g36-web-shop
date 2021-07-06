package se.lexicon.g36webshop.model.dto.product_category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import se.lexicon.g36webshop.model.dto.product.ProductDTOSmall;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductCategoryDTO {
    private String categoryId;
    private String value;
    private List<ProductDTOSmall> productsWithCategory;
}
