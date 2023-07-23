package social.medai.api.RestApiSocialMedia.services;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import social.medai.api.RestApiSocialMedia.dto.PostDTOResponse;
import social.medai.api.RestApiSocialMedia.exception.NotFoundException;
import social.medai.api.RestApiSocialMedia.dto.PhotoDTO;
import social.medai.api.RestApiSocialMedia.dto.ListPostDTOResponse;
import social.medai.api.RestApiSocialMedia.exception.OperationFailed;
import social.medai.api.RestApiSocialMedia.fileManager.PhotoService;
import social.medai.api.RestApiSocialMedia.models.*;
import social.medai.api.RestApiSocialMedia.repositories.FollowerRepository;
import social.medai.api.RestApiSocialMedia.repositories.FriendRepository;
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
    private final FollowerRepository followerRepository;
    private final FriendRepository friendRepository;

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

    public ListPostDTOResponse getPostsByUserId(int id){
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with this id does not exist"));
        List<Post> posts = user.getPosts();
        List<PostDTOResponse> postDTOResponseList = new ArrayList<>();
        for (Post post:posts){

            PostDTOResponse postDTO = modelMapper.map(post,PostDTOResponse.class);

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
            postDTOResponseList.add(postDTO);
        }
        return  new ListPostDTOResponse(postDTOResponseList);
    }

    @Transactional
    public void addFollower(int idUser) {
        User user = userRepository.findById(idUser).orElseThrow(() -> new NotFoundException("The user you want to subscribe to does not exist"));
        Follower follower = new Follower();
        User userFollower = getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        follower.setIdFollower(userFollower.getId());
        follower.setUser(user);
        followerRepository.save(follower);
    }
    @Transactional
    public void addFriend(int id) {
        User user = getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        if(followerRepository.getFollowerByUserAndIdFollower(user,id).isPresent()){

            Friend friend = new Friend();
            friend.setUser(user);           // Adding a friend from the receiving side of the request
            friend.setIdFriend(id);
            friendRepository.save(friend);

            User followerToUser = userRepository.findById(id).get();
            Friend friend2 = new Friend();
            friend2.setUser(followerToUser);        // Adding a friend from the sender of the request
            friend2.setIdFriend(user.getId());
            friendRepository.save(friend2);

            Follower follower = new Follower();
            follower.setUser(followerToUser);             // Subscribe to someone you've made friends with
            follower.setIdFollower(user.getId());
            followerRepository.save(follower);
        } else throw new NotFoundException("You can't add a friend who hasn't sent a friend request");
    }

    @Transactional
    public void deleteFriend(int idFriendToDelete){
        User user = getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();

        friendRepository.delete(friendRepository.getFriendByUserAndIdFriend(user,idFriendToDelete)
                .orElseThrow(() -> new NotFoundException("This person is not your friend")));

        friendRepository.delete(friendRepository.getFriendByUserAndIdFriend(userRepository.findById(idFriendToDelete).get(),
                user.getId()).get());

        followerRepository.delete(followerRepository.getFollowerByUserAndIdFollower(user,idFriendToDelete)
                .orElseThrow(() -> new NotFoundException("You are don't subscribe to this person")));
    }

    @Transactional
    public void unfollow(int id){
        User user = userRepository.
                findById(id).orElseThrow(() -> new NotFoundException("This person dont exists"));
        User follower = getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .get();

        if(friendRepository.getFriendByUserAndIdFriend(user,follower.getId()).isEmpty()){
            Follower follow = followerRepository.getFollowerByUserAndIdFollower(user,
                    follower.getId()).orElseThrow(() -> new NotFoundException("You are dont subscribe to this person"));
            followerRepository.delete(follow);
        } else throw new OperationFailed("This person is your friend");
    }
}
