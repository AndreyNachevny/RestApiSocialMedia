package social.medai.api.RestApiSocialMedia.security;

import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import social.medai.api.RestApiSocialMedia.dto.JWTResponse;
import social.medai.api.RestApiSocialMedia.dto.UserAuthenticationDTO;
import social.medai.api.RestApiSocialMedia.models.User;
import social.medai.api.RestApiSocialMedia.services.UserService;
@Component
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public JWTResponse login(UserAuthenticationDTO authenticationRequest) throws AuthException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),authenticationRequest.getPassword()));
        } catch (BadCredentialsException e){
            throw new AuthException("Incorrect email or password");
        }
        final User user = userService.getUserByEmail(authenticationRequest.getEmail())
                .orElseThrow( () -> new AuthException("User is not found"));
            final String accessToken = jwtService.generateAccessToken(user);
            final String refreshToken = jwtService.generateRefreshToken(user);
            return new JWTResponse(accessToken,refreshToken);
    }
    public JWTResponse registration(User user) {
        User userForToken = userService.getUserByEmail(user.getEmail()).get();
        final String accessToken = jwtService.generateAccessToken(userForToken);
        final String refreshToken = jwtService.generateRefreshToken(userForToken);
        return new JWTResponse(accessToken,refreshToken);
    }

    public JWTResponse getAccessToken(String refreshToken) throws AuthException {
        if(jwtService.validateRefreshToken(refreshToken)){
            final Claims claims = jwtService.getRefreshClaims(refreshToken);
            final String email= claims.getSubject();
            final User user = userService.getUserByEmail(email)
                    .orElseThrow( () -> new AuthException("User is not found"));
            final String accessToken = jwtService.generateAccessToken(user);
            return new JWTResponse(accessToken,null);
        }
        return new JWTResponse(null,null);
    }

    public JWTResponse refresh(String refreshToken) throws AuthException {
        if (jwtService.validateRefreshToken(refreshToken)){
            final Claims claims = jwtService.getRefreshClaims(refreshToken);
            final String email =  claims.getSubject();
            final User user = userService.getUserByEmail(email)
                    .orElseThrow( () -> new AuthException("User is not found"));
            final String accessToken = jwtService.generateAccessToken(user);
            final String newRefreshToken = jwtService.generateRefreshToken(user);
            return new JWTResponse(accessToken, newRefreshToken);
        }
        throw new AuthException("invalid JWT token");
    }

}
