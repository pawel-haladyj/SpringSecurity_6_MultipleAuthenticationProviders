package pl.haladyj.springsecurity6.security.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.haladyj.springsecurity6.entity.Otp;
import pl.haladyj.springsecurity6.repository.OtpRepository;
import pl.haladyj.springsecurity6.security.authentication.OptAuthentication;
import pl.haladyj.springsecurity6.security.authentication.UsernamePasswordAuthentication;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

@Component
public class UsernamePasswordAuthFilter extends OncePerRequestFilter {

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private AuthenticationManager manager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        //step 1: username and password
        //step 2: username and otp

        var username = request.getHeader("username");
        var password = request.getHeader("password");
        var otp = request.getHeader("otp");

        if(otp==null){
            //step 1
            Authentication a = new UsernamePasswordAuthentication(username,password);
            a = manager.authenticate(a);
            SecurityContextHolder.getContext().setAuthentication(a);
            String code = String.valueOf(new Random().nextInt(8999)+1000);

            Otp otpEntity = new Otp();
            otpEntity.setUsername(username);
            otpEntity.setOtp(code);
            otpRepository.save(otpEntity);
        } else{
            //step 2
            Authentication a = new OptAuthentication(username,otp);
            a = manager.authenticate(a);
            SecurityContextHolder.getContext().setAuthentication(a);
            response.setHeader("Authorization", UUID.randomUUID().toString());
        }

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/login");
    }
}
