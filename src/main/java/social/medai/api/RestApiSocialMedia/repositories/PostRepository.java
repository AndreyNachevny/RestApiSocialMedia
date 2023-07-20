package social.medai.api.RestApiSocialMedia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import social.medai.api.RestApiSocialMedia.models.Post;
@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {
}
