package se.lexicon.g36webshop.service.interfaces;

import org.springframework.transaction.annotation.Transactional;
import se.lexicon.g36webshop.model.dto.address.AddressDTO;
import se.lexicon.g36webshop.model.entity.Address;

import java.util.Collection;

public interface AddressService extends BasicCRUD<Address, AddressDTO>{
    /**
     * Intended to be used when a customer moves to a different address. Therefore it will
     * perform a search for an existing address with same street, zipCode, city and country as defined in AddressDTO addressDTO.
     * If address is found it will be returned else a new Address will be persisted.
     * @param oldAddressId - String representing the old Address.
     * @param addressDTO - AddressDTO representing the new Address.
     * @return Address which is either persisted or found in the database
     */
    @Transactional(rollbackFor = RuntimeException.class)
    Address updateReplace(String oldAddressId, AddressDTO addressDTO);

    Collection<Address> findByCity(String city);
}
