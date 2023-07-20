package social.medai.api.RestApiSocialMedia.util;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import social.medai.api.RestApiSocialMedia.dto.PostDTO;

@AllArgsConstructor
@Component
public class PostValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {
        return PostDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PostDTO postDTO = (PostDTO) target;
        if(postDTO.getHeader() == null
                && postDTO.getText() == null
                && postDTO.getPhotos() == null){
            errors.rejectValue("text", "", "Can't create an empty post");
        }
    }
}
