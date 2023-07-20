package social.medai.api.RestApiSocialMedia.security;

import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collections;

public class JwtUtils {
    static public UsernamePasswordAuthenticationToken generate(Claims claims){
        return new UsernamePasswordAuthenticationToken(
                claims.getSubject(),
                null,
                new ArrayList<>(Collections.singleton(new SimpleGrantedAuthority("USER")))
        );
    }
}
