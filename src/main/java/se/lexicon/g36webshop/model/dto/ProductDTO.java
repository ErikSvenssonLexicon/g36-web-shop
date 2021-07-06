package se.lexicon.g36webshop.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {

    private String productId;
    private String productName;
    private String description;
    private BigDecimal productPrice;
    private List<ProductCategoryDTOSmall> categories;

}
