package social.medai.api.RestApiSocialMedia.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import social.medai.api.RestApiSocialMedia.models.Photo;
import social.medai.api.RestApiSocialMedia.repositories.PhotoRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PhotoService {
    private final PhotoRepository photoRepository;

    public Optional<Photo> getByName(String name){
        return photoRepository.findByName(name);
    }
}
