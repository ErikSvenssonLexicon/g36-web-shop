package se.lexicon.g36webshop.service.interfaces;

import se.lexicon.g36webshop.model.dto.app_user.AppUserDTO;
import se.lexicon.g36webshop.model.entity.AppUser;

public interface AppUserService extends BasicCRUD<AppUser, AppUserDTO>{
    AppUser findByUsername(String username);
}
