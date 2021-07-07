package se.lexicon.g36webshop.service.implementation.conversion;

import org.springframework.stereotype.Service;
import se.lexicon.g36webshop.model.dto.address.AddressDTO;
import se.lexicon.g36webshop.model.entity.Address;
import se.lexicon.g36webshop.service.interfaces.conversion.AddressFactory;

@Service
public class AddressFactoryImpl implements AddressFactory {
    @Override
    public Address createFromDTO(AddressDTO addressDTO) {
        if(addressDTO == null) throw new IllegalArgumentException("AddressDTO addressDTO was null");
        Address address = new Address();
        address.setAddressId(addressDTO.getAddressId());
        address.setStreet(addressDTO.getStreet().trim());
        if(addressDTO.getZipCode() != null){
            address.setZipCode(addressDTO.getZipCode().trim().replaceAll("\\p{Punct}", "").replaceAll("\\p{Space}", ""));
        }
        address.setCity(addressDTO.getCity().trim());
        address.setCountry(addressDTO.getCountry().trim());
        return address;
    }
}
