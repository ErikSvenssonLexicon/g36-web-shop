package se.lexicon.g36webshop.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.g36webshop.data.AppUserDAO;
import se.lexicon.g36webshop.exception.AppResourceNotFoundException;
import se.lexicon.g36webshop.model.dto.app_user.AppUserDTO;
import se.lexicon.g36webshop.model.entity.AppUser;
import se.lexicon.g36webshop.service.interfaces.AppUserService;
import se.lexicon.g36webshop.service.interfaces.conversion.AppUserFactory;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserDAO appUserDAO;
    private final AppUserFactory appUserFactory;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public AppUser create(AppUserDTO appUserDTO) {
        AppUser appUser = appUserFactory.createFromDTO(appUserDTO);
        return appUserDAO.save(appUser);
    }

    @Override
    @Transactional(readOnly = true)
    public AppUser findByUsername(String username){
        return appUserDAO.findByUsername(username)
                .orElseThrow(() -> new AppResourceNotFoundException("Could not find AppUser with username " + username));
    }

    @Override
    @Transactional(readOnly = true)
    public AppUser findById(String id) {
        return appUserDAO.findById(id)
                .orElseThrow(() -> new AppResourceNotFoundException("Could not find AppUser with id " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<AppUser> findAll() {
        return appUserDAO.findAll();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public AppUser update(String id, AppUserDTO appUserDTO) {
        if(appUserDTO == null) throw new IllegalArgumentException("Update aborted AppUserDTO appUserDTO was null");
        if(appUserDTO.getUserId() != null && !appUserDTO.getUserId().equals(id)){
            throw new IllegalArgumentException("Update aborted, cause: appUserDTO.userId did not match id");
        }
        AppUser appUser = findById(id);

        if(appUserDTO.getUsername() != null){
            appUser.setUsername(appUserDTO.getUsername().trim());
        }

        if(appUserDTO.getPassword() != null){
            appUser.setPassword(appUserDTO.getPassword().trim());
        }

        appUser = appUserDAO.save(appUser);

        return appUser;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(String id) {
        appUserDAO.deleteById(id);
    }
}
