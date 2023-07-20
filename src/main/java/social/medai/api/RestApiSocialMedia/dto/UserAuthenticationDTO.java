package social.medai.api.RestApiSocialMedia.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAuthenticationDTO {

    @Email
    @NotNull
    private String email;

    @NotNull
    private String password;
}
