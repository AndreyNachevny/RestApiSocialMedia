package social.medai.api.RestApiSocialMedia.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import social.medai.api.RestApiSocialMedia.dto.ListPostDTOResponse;
import social.medai.api.RestApiSocialMedia.dto.PostDTOResponse;
import social.medai.api.RestApiSocialMedia.exception.NotFoundException;
import social.medai.api.RestApiSocialMedia.exception.OperationFailed;
import social.medai.api.RestApiSocialMedia.dto.PostDTO;
import social.medai.api.RestApiSocialMedia.fileManager.PhotoService;
import social.medai.api.RestApiSocialMedia.models.Photo;
import social.medai.api.RestApiSocialMedia.models.Post;
import social.medai.api.RestApiSocialMedia.models.User;
import social.medai.api.RestApiSocialMedia.repositories.PostRepository;
import social.medai.api.RestApiSocialMedia.util.PostMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final PostMapper postMapper;
    @Transactional
    public void createPost(PostDTO postDTO){
        Post postToSave = modelMapper.map(postDTO,Post.class);
        User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        postToSave.setUser(user);
        postToSave.setCreatedAt(LocalDateTime.now());
        if(postDTO.getPhotos() != null){
            List<String> paths = PhotoService.savePhoto(postDTO.getPhotos());
            List<Photo> photos = new ArrayList<>();
            for(int i = 0; i < paths.size(); i++){
                Photo photo = new Photo(paths.get(i));
                photo.setPost(postToSave);
                photo.setName(postDTO.getPhotos().get(i).getName());
                photos.add(photo);
            }
            postToSave.setPhotos(photos);
        }
        postRepository.save(postToSave);
    }

    public Page<PostDTOResponse> getLastPosts(int offset, int limit, String sort){
        Page<Post> posts;
        if (sort.equals("false")){
            posts = postRepository.getPostsFromUsers(userService
                    .getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                    .get().getId(), PageRequest.of(offset, limit));
        } else {
            posts = postRepository.getPostsFromUsers(userService
                    .getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                    .get().getId(), PageRequest.of(offset, limit, Sort.by("createdAt")));
        }
        return posts.map(postMapper);
    }



    @Transactional
    public void deletePost(int id){
        if(!SecurityContextHolder.getContext().getAuthentication().getName()
                .equals(postRepository.getReferenceById(id).getUser().getEmail())){
            throw new OperationFailed("You do not have permission to delete this post");
        }
        postRepository.deleteById(id);
    }

    @Transactional
    public void updatePost(PostDTO postDTO, int id){
        if(!SecurityContextHolder.getContext().getAuthentication().getName()
                .equals(postRepository.getReferenceById(id).getUser().getEmail())){
            throw new OperationFailed("You do not have permission to update this post");
        }
        Post postFromDatabase = postRepository.getReferenceById(id);
        Post postToUpdate = modelMapper.map(postDTO,Post.class);
        postToUpdate.setId(id);
        postToUpdate.setCreatedAt(postFromDatabase.getCreatedAt());
        postToUpdate.setUser(postFromDatabase.getUser());
        postRepository.save(postToUpdate);
    }

    public PostDTOResponse getPost(int id) {
        return postMapper.apply(postRepository.getPostById(id).orElseThrow(() -> new NotFoundException("Post with id not exists")));
    }
}
