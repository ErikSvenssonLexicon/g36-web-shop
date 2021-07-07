package se.lexicon.g36webshop.model.dto.address;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddressDTO {
    private String addressId;
    private String street;
    private String zipCode;
    private String city;
    private String country;
}
