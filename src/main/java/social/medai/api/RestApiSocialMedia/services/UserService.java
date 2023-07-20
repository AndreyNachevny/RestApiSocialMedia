package social.medai.api.RestApiSocialMedia.services;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import social.medai.api.RestApiSocialMedia.Exception.NotFoundException;
import social.medai.api.RestApiSocialMedia.dto.PhotoDTO;
import social.medai.api.RestApiSocialMedia.dto.PostDTO;
import social.medai.api.RestApiSocialMedia.dto.PostDTOResponse;
import social.medai.api.RestApiSocialMedia.fileManager.PhotoService;
import social.medai.api.RestApiSocialMedia.models.Photo;
import social.medai.api.RestApiSocialMedia.models.Post;
import social.medai.api.RestApiSocialMedia.models.User;
import social.medai.api.RestApiSocialMedia.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final ModelMapper modelMapper;

    @Transactional
    public void registration(User user){
        user.setPassword(encoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<String> getAllPassword(){
        return userRepository.getAllPassword();
    }

    public PostDTOResponse getPostsById(int id){
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with this id does not exist"));
        List<Post> posts = user.getPosts();
        List<PostDTO> postDTOList = new ArrayList<>();
        for (Post post:posts){

            PostDTO postDTO = modelMapper.map(post,PostDTO.class);

            if(post.getPhotos() != null){

                List<PhotoDTO> photoDTOList = new ArrayList<>();

                for(Photo photo:post.getPhotos()) {

                    PhotoDTO photoDTO = new PhotoDTO();

                    photoDTO.setFile(PhotoService.getPhotos(photo.getPath()));
                    photoDTO.setName(photo.getName());
                    photoDTOList.add(photoDTO);
                }
                postDTO.setPhotos(photoDTOList);
            }
            postDTOList.add(postDTO);
        }
        return  new PostDTOResponse(postDTOList);
    }

}
