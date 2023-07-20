package social.medai.api.RestApiSocialMedia.util;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import social.medai.api.RestApiSocialMedia.models.User;
import social.medai.api.RestApiSocialMedia.services.UserService;

import java.util.ArrayList;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class RegistrationUserValidator implements Validator {
    private final UserService userService;
    private final PasswordEncoder encoder;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        Optional<User> userCheckEmail = userService.getUserByEmail(user.getEmail());
        ArrayList<String> passwords = (ArrayList<String>) userService.getAllPassword();
        if(userCheckEmail.isPresent()){
            if (encoder.matches(user.getPassword(), userCheckEmail.get().getPassword())){
                errors.rejectValue("password","", "A user with the same email and password already exists");
                return;
            }
            errors.rejectValue("email", "", "A user with  email already exists");
            return;
        }
        for(String password: passwords){
            if (encoder.matches(user.getPassword(),password)){
                errors.rejectValue("password", "","This password already using");
            }
        }

    }
}
