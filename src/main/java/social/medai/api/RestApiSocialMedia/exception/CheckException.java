package social.medai.api.RestApiSocialMedia.exception;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class CheckException {
    public static void checkException(BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error: errors){
                errorMsg.append("Error: ").append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(";").append(" ");
            }
            throw new NotCreatedException(errorMsg.toString());
        }
    }
}
