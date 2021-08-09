package se.lexicon.g36webshop.model.dto.app_user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AppUserDTO {
    private String userId;
    private String username;
    private String password;
}
