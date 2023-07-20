package social.medai.api.RestApiSocialMedia.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserRegistrationDTO {
    @NotEmpty
    private String name;
    @NotEmpty
    private String gender;

    @JsonFormat(pattern = "dd-MM-yyyy",timezone="Europe/Moscow")
    @NotNull
    private Date dateOfBirth;

    @Email
    @NotNull
    private String email;

    @NotEmpty
    private String password;

}
