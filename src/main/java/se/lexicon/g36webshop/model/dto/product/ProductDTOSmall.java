package se.lexicon.g36webshop.model.dto.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTOSmall {
    private String productId;
    private String productName;
    private String description;
    private BigDecimal productPrice;
}
