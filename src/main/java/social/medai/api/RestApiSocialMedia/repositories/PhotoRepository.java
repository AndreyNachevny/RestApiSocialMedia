package social.medai.api.RestApiSocialMedia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import social.medai.api.RestApiSocialMedia.models.Photo;

import java.util.Optional;

@Repository
public interface PhotoRepository extends JpaRepository<Photo,Integer> {
    Optional<Photo> findByName(String name);
}
