package pl.haladyj.springsecurity6.security.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import pl.haladyj.springsecurity6.repository.OtpRepository;
import pl.haladyj.springsecurity6.security.authentication.OptAuthentication;

import java.util.List;

@Component
public class OtpProvider implements AuthenticationProvider {

    @Autowired
    private OtpRepository repository;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        String username = authentication.getName();
        String otp = authentication.getCredentials().toString();

        var o = repository.findOptByUsername(username);
        if(o.isPresent()){
            return new OptAuthentication(username,otp, List.of(()->"read"));
        }
        throw new BadCredentialsException("bad credentials otp");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return OptAuthentication.class.equals(aClass);
    }
}
