package se.lexicon.g36webshop.service.interfaces.conversion;

import se.lexicon.g36webshop.model.dto.app_user.AppUserDTO;
import se.lexicon.g36webshop.model.entity.AppUser;

public interface AppUserFactory {
    AppUser createFromDTO(AppUserDTO appUserDTO);
}
