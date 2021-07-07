package se.lexicon.g36webshop.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.g36webshop.data.AddressDAO;
import se.lexicon.g36webshop.data.CustomerDAO;
import se.lexicon.g36webshop.exception.AppResourceNotFoundException;
import se.lexicon.g36webshop.model.dto.address.AddressDTO;
import se.lexicon.g36webshop.model.entity.Address;
import se.lexicon.g36webshop.service.interfaces.AddressService;
import se.lexicon.g36webshop.service.interfaces.conversion.AddressFactory;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressDAO addressDAO;
    private final CustomerDAO customerDAO;
    private final AddressFactory addressFactory;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Address create(AddressDTO addressDTO) {
        Address address = addressFactory.createFromDTO(addressDTO);
        address = addressDAO.findByStreetZipCodeCityCountry(
                address.getStreet(),
                address.getZipCode(),
                address.getCity(),
                address.getCountry()
        ).orElse(addressDAO.save(address));

        return address;
    }

    @Override
    @Transactional(readOnly = true)
    public Address findById(String id) {
        return addressDAO.findById(id)
                .orElseThrow(() -> new AppResourceNotFoundException("Address with id " + id + " was not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Address> findAll() {
        return addressDAO.findAll();
    }


    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Address updateReplace(String oldAddressId, AddressDTO addressDTO) {
        Address newAddress = create(addressDTO);
        delete(oldAddressId);
        return newAddress;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Address> findByCity(String city) {
        return addressDAO.findByCityIgnoreCase(city.trim());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Address update(String id, AddressDTO addressDTO){
        if(addressDTO == null) throw new IllegalArgumentException("Update aborted, cause is AddressDTO addressDTO was null");
        if(addressDTO.getAddressId() != null && !addressDTO.getAddressId().equals(id)){
            throw new IllegalArgumentException("Update aborted, cause is AddressDTO.addressId did not match id");
        }
        Address address = findById(id);
        if(addressDTO.getStreet() != null){
            address.setStreet(addressDTO.getStreet().trim());
        }
        if(addressDTO.getZipCode() != null){
            address.setZipCode(addressDTO.getZipCode().trim().replaceAll("\\p{Punct}", "").replaceAll("\\p{Space}", ""));
        }
        if(addressDTO.getCity() != null){
            address.setCity(addressDTO.getCity().trim());
        }
        if(addressDTO.getCountry() != null){
            address.setCountry(addressDTO.getCountry().trim());
        }

        address = addressDAO.save(address);

        return address;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(String id) {
        Address address = findById(id);
        if(customerDAO.findByShippingAddress(id).isEmpty()){
            addressDAO.delete(address);
        }
    }
}
