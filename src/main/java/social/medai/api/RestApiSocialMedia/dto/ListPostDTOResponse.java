package social.medai.api.RestApiSocialMedia.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
@RequiredArgsConstructor
@Setter
@Getter
public class ListPostDTOResponse {
    private final List<PostDTOResponse> postDTOResponseList;

}
