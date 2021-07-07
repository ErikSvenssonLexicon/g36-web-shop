package se.lexicon.g36webshop.service.interfaces.conversion;

import se.lexicon.g36webshop.model.dto.address.AddressDTO;
import se.lexicon.g36webshop.model.entity.Address;

public interface AddressFactory {
    Address createFromDTO(AddressDTO addressDTO);
}
