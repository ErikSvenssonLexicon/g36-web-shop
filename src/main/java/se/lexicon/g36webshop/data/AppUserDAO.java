package se.lexicon.g36webshop.data;

import org.springframework.data.jpa.repository.JpaRepository;
import se.lexicon.g36webshop.model.entity.AppUser;

public interface AppUserDAO extends JpaRepository<AppUser, String> {
}
