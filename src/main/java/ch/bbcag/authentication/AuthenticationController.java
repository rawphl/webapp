package ch.bbcag.authentication;

import ch.bbcag.models.ApplicationUser;
import ch.bbcag.services.ApplicationUserService;
import com.auth0.jwt.JWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@RestController
public class AuthenticationController {
    private static Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    private static record Credentials(String username, String password){}
    private static record LoginSuccess(String token, String username, Date expires){}

    @Autowired
    private ApplicationUserService applicationUserService;
    @Autowired
    private final AuthenticationManager authenticationManager;

    @Value("${bbcag.app.jwtSecret}")
    private String jwtSecret;
    @Value("${bbcag.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    private String generateToken(String username, Date expires) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(expires)
                .sign(HMAC512(jwtSecret.getBytes()));
    }

    public AuthenticationController(ApplicationUserService applicationUserService, AuthenticationManager authenticationManager) {
        this.applicationUserService = applicationUserService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public LoginSuccess login(@RequestBody Credentials credentials) {
        ApplicationUser user = applicationUserService.findByName(credentials.username);

        if(user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentials.username,
                            credentials.password,
                            new ArrayList<>()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Date expires = new Date(System.currentTimeMillis() + jwtExpirationMs);
            String token = generateToken(credentials.username, expires);
            return new LoginSuccess(token, credentials.username, expires);
        } catch(BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PostMapping("/register")
    public ApplicationUser register(@RequestBody Credentials credentials) {
        ApplicationUser user = applicationUserService.findByName(credentials.username);

        if(user != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        ApplicationUser newUser = new ApplicationUser();
        newUser.setUsername(credentials.username);
        newUser.setPassword(credentials.password);
        applicationUserService.signUp(newUser);
        return newUser;
    }

}
