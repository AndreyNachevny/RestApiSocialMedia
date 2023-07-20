package social.medai.api.RestApiSocialMedia.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RefreshJwtRequest {
    @NotEmpty
    private String refreshToken;
}
