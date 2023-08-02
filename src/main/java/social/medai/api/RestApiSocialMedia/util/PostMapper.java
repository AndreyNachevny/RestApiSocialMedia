package social.medai.api.RestApiSocialMedia.util;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import social.medai.api.RestApiSocialMedia.dto.PhotoDTO;
import social.medai.api.RestApiSocialMedia.dto.PostDTOResponse;
import social.medai.api.RestApiSocialMedia.fileManager.PhotoService;
import social.medai.api.RestApiSocialMedia.models.Photo;
import social.medai.api.RestApiSocialMedia.models.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@RequiredArgsConstructor
@Component
public class PostMapper implements Function<Post, PostDTOResponse> {
    private final ModelMapper modelMapper;
    @Override
    public PostDTOResponse apply(Post post) {
        PostDTOResponse postDTOResponse = modelMapper.map(post,PostDTOResponse.class);
        if(post.getPhotos() != null){

            List<PhotoDTO> photoDTOList = new ArrayList<>();

            for(Photo photo:post.getPhotos()) {

                PhotoDTO photoDTO = new PhotoDTO();

                photoDTO.setFile(PhotoService.getPhotos(photo.getPath()));
                photoDTO.setName(photo.getName());
                photoDTOList.add(photoDTO);
            }
            postDTOResponse.setPhotos(photoDTOList);
        }
        return postDTOResponse;
    }
}
