package pl.haladyj.springsecurity6.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.haladyj.springsecurity6.entity.User;
import pl.haladyj.springsecurity6.repository.UserRepository;
import pl.haladyj.springsecurity6.security.model.SecurityUser;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var o = repository.findByUsername(username);
        User u = o.orElseThrow(()->new UsernameNotFoundException("User does not exist in db scope"));

        return new SecurityUser(u);
    }
}
