package se.lexicon.g36webshop.service.implementation.conversion;

import org.springframework.stereotype.Service;
import se.lexicon.g36webshop.model.dto.app_user.AppUserDTO;
import se.lexicon.g36webshop.model.entity.AppUser;
import se.lexicon.g36webshop.service.interfaces.conversion.AppUserFactory;

@Service
public class AppUserFactoryImpl implements AppUserFactory {
    @Override
    public AppUser createFromDTO(AppUserDTO appUserDTO) {
        if(appUserDTO == null) throw new IllegalArgumentException("AppUserDTO appUserDTO was null");

        AppUser appUser = new AppUser();
        appUser.setUserId(appUserDTO.getUserId());
        appUser.setUsername(appUserDTO.getUsername() == null ? null : appUserDTO.getUsername().trim());
        appUser.setPassword(appUserDTO.getPassword() == null ? null : appUserDTO.getPassword().trim());
        return appUser;
    }
}
