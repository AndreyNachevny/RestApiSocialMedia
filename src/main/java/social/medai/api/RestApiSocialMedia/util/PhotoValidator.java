package social.medai.api.RestApiSocialMedia.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import social.medai.api.RestApiSocialMedia.dto.PhotoDTO;
import social.medai.api.RestApiSocialMedia.services.PhotoService;

@Component
@RequiredArgsConstructor
public class PhotoValidator implements Validator {

    private final PhotoService photoService;
    @Override
    public boolean supports(Class<?> clazz) {
        return PhotoDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PhotoDTO photoDTO = (PhotoDTO) target;
        if (photoDTO.getName() == null){
            errors.rejectValue("photos","","can't send file without name");
        }
        if(photoDTO.getFile() == null){
            errors.rejectValue("photos","","can't send file without file");
        }
        if(photoService.getByName(photoDTO.getName()).isPresent()){
            errors.rejectValue("photos","","A photo with this name already exists");
        }
    }
}
