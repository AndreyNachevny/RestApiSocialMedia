package social.medai.api.RestApiSocialMedia.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class PostDTOResponse {

    private int id;

    private String header;

    private String text;

    private List<PhotoDTO> photos;
}
