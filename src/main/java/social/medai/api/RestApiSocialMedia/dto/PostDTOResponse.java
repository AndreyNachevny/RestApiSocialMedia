package social.medai.api.RestApiSocialMedia.dto;

import lombok.RequiredArgsConstructor;

import java.util.List;
@RequiredArgsConstructor
public class PostDTOResponse {
    private final List<PostDTO> postDTOList;

    public List<PostDTO> getPostDTOList() {
        return postDTOList;
    }
}
