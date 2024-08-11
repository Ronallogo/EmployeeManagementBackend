package com.EmployeeManagment.Source.Security.Config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static com.EmployeeManagment.Source.Security.entities.Permission.*;
import static com.EmployeeManagment.Source.Security.entities.Roles.ADMIN;
import static com.EmployeeManagment.Source.Security.entities.Roles.USER;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity


public class SecurityConfiguration {

    private static   final  String[] WHITE_LIST_URL = {
            "/api/employee_manager/auth/**",
            "/api/employee_manager/absence/**",
            "/api/employee_manager/content/**",
            "/api/employee_manager/employee/**",
            "/api/employee_manager/payStub/**",
            "/api/employee_manager/position/**",
            "/api/employee_manager/task/**",
            "/api/employee_manager/taskInserted/**",
            "/api/employee_manager/taskSchedule/**",
            "/api/employee_manager/timeOff/**",

    };

    private final JwtAuthenticationFilter jwtAuthenticationFilter ;
    private final AuthenticationProvider authenticationProvider ;
    private final LogoutHandler logoutHandler ;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws  Exception{
        http
            .csrf(AbstractHttpConfigurer:: disable)
            .authorizeHttpRequests(req ->
                req.requestMatchers(WHITE_LIST_URL)
                    .permitAll()
                        .requestMatchers("/api/employee_manager/**").hasAnyRole(ADMIN.name() , USER.name())
                        .requestMatchers(GET , "/api/employee_manager/**").hasAnyRole(ADMIN_READ.name() , USER_READ.name())
                        .requestMatchers(POST , "/api/employee_manager/**").hasAnyRole(ADMIN_CREATE.name() , USER_CREATE.name())
                        .requestMatchers(PUT , "/api/employee_manager/**").hasAnyRole(ADMIN_UPDATE.name() , USER_UPDATE.name())
                        .requestMatchers(DELETE , "/api/employee_manager/**").hasAnyRole(ADMIN_DELETE.name())
                        .anyRequest()
                        .authenticated()

                    )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter , UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                            logout.logoutUrl("/api/employee_manager/auth/logout")
                                    .addLogoutHandler(logoutHandler)
                                    .logoutSuccessHandler((request, response, authentication) ->  SecurityContextHolder.clearContext())

                        );


        return  http.build() ;

    }

}
