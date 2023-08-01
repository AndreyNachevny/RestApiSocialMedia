package social.medai.api.RestApiSocialMedia.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.security.auth.message.AuthException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import social.medai.api.RestApiSocialMedia.dto.RefreshJwtRequest;
import social.medai.api.RestApiSocialMedia.dto.UserAuthenticationDTO;
import social.medai.api.RestApiSocialMedia.dto.UserRegistrationDTO;
import social.medai.api.RestApiSocialMedia.models.User;
import social.medai.api.RestApiSocialMedia.security.AuthService;
import social.medai.api.RestApiSocialMedia.dto.JWTResponse;
import social.medai.api.RestApiSocialMedia.services.UserService;
import social.medai.api.RestApiSocialMedia.exception.CheckException;
import social.medai.api.RestApiSocialMedia.exception.ErrorResponse;
import social.medai.api.RestApiSocialMedia.exception.NotCreatedException;
import social.medai.api.RestApiSocialMedia.util.RegistrationUserValidator;


@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
@Tag(name = "AuthenticationController",description = "It make authentication authorization and issue of tokens ")
public class AuthenticationController {

    private final AuthService authService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final RegistrationUserValidator registrationUserValidator;


    @Operation(
            summary = "Authentication of user"
    )
    @PostMapping("/login")
    public ResponseEntity<JWTResponse> login(@RequestBody @Valid UserAuthenticationDTO userDTO,
                                             BindingResult bindingResult) throws AuthException {
        CheckException.checkException(bindingResult);
        final JWTResponse token = authService.login(userDTO);
        return ResponseEntity.ok(token);
    }

    @Operation(
            summary = "Issues a json web token",
            description = "Issues a JWT by refresh token"
    )
    @PostMapping("/token")
    public ResponseEntity<JWTResponse> token(@RequestBody @Valid RefreshJwtRequest request) throws AuthException {
        final JWTResponse response = authService.getAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Issues a JWT and refresh token",
            description = "Issues refreshing JWT and refresh token by refresh token when refresh token expires "
    )
    @SecurityRequirement(name = "JWT")
    @PostMapping("/refresh")
    public ResponseEntity<JWTResponse> getNewRefreshToken(@RequestBody @Valid RefreshJwtRequest request) throws AuthException {
        final JWTResponse token = authService.refresh(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/registration")
    @Operation(
            summary = "It make registration of new users"
    )
    public ResponseEntity<JWTResponse> registration(@RequestBody @Valid UserRegistrationDTO userDTO,
                                                    BindingResult bindingResult){
        User userToSave = converToUSer(userDTO);
        registrationUserValidator.validate(userToSave,bindingResult);
        CheckException.checkException(bindingResult);
        userService.registration(userToSave);
        final JWTResponse token = authService.registration(userToSave);
        return ResponseEntity.ok(token);
    }




    private User converToUSer(UserRegistrationDTO userDTO){
        return modelMapper.map(userDTO,User.class);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handAuthException(AuthException authException){
        ErrorResponse response = new ErrorResponse(
                authException.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handNotCreated(NotCreatedException notCreatedException){
        ErrorResponse response = new ErrorResponse(
                notCreatedException.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}
