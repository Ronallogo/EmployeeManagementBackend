package com.EmployeeManagment.Source.Security.Config;


import com.EmployeeManagment.Source.Security.entities.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
   private final UserRepository repository ;



   @Bean
    public UserDetailsService userDetailsService(){
    return   username -> repository.findByEmail(username)
            .orElseThrow(()-> new  UsernameNotFoundException("User not found"));
   }

   @Bean
    public AuthenticationProvider authenticationProvider(){
       DaoAuthenticationProvider authProvider    = new DaoAuthenticationProvider() ;
       authProvider.setUserDetailsService(userDetailsService());
       authProvider.setPasswordEncoder(passwordEncoder());
       return authProvider ;
   }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder( );
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws  Exception{
        return  config.getAuthenticationManager() ;
    }

    @Bean
    public LogoutHandler logoutHandler() {
        return new SecurityContextLogoutHandler();
    }


}
