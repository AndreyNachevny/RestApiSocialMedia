package social.medai.api.RestApiSocialMedia.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import social.medai.api.RestApiSocialMedia.models.Post;

import java.util.Optional;


@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {

    @Query("FROM Post p WHERE p.user IN (SELECT f.user FROM Follower f WHERE f.idFollower = :id) AND p.id = (SELECT MAX(p2.id) FROM Post p2)")
    Page<Post> getPostsFromUsers(@Param("id")int id, Pageable pageable);

    Optional<Post> getPostById(int id);
}
