package social.medai.api.RestApiSocialMedia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import social.medai.api.RestApiSocialMedia.models.Follower;
import social.medai.api.RestApiSocialMedia.models.User;

import java.util.Optional;

@Repository
public interface FollowerRepository extends JpaRepository<Follower,Integer>{

    Optional<Follower> getFollowerByUserAndIdFollower(User user, int idFollower);
}
