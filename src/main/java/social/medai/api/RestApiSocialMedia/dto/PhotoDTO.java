package social.medai.api.RestApiSocialMedia.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PhotoDTO {
    private String name;
    @Schema(description = "Photo in base64 format")
    private String file;
}
