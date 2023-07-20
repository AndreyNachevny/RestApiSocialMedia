package social.medai.api.RestApiSocialMedia.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JWTResponse {
    private final String type = "Bearer ";
    private String accessToken;
    private String refreshToken;
}
