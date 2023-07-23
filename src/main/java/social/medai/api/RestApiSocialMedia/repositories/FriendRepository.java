package social.medai.api.RestApiSocialMedia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import social.medai.api.RestApiSocialMedia.models.Friend;
import social.medai.api.RestApiSocialMedia.models.User;

import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend,Integer> {
    Optional<Friend> getFriendByUserAndIdFriend(User user, int idFriend);
}
