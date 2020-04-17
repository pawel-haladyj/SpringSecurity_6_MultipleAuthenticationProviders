package pl.haladyj.springsecurity6.security.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class OptAuthentication extends UsernamePasswordAuthenticationToken {
    public OptAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public OptAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
