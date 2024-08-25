package com.EmployeeManagment.Source.Security.auth;


import com.EmployeeManagment.Source.Security.Config.JwtService;
import com.EmployeeManagment.Source.Security.Token.Token;
import com.EmployeeManagment.Source.Security.Token.TokenRepository;
import com.EmployeeManagment.Source.Security.Token.TypeToken;
import com.EmployeeManagment.Source.Security.entities.User;
import com.EmployeeManagment.Source.Security.entities.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository ;
    private  final TokenRepository  tokenRepository;
    private final PasswordEncoder passwordEncoder ;
    private  final JwtService jwtService ;
    private final AuthenticationManager authenticationManager ;


    public AuthenticationResponse register(RegisterRequest request){
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        var saveUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        saveUserToken(saveUser , jwtToken);
        return  AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void revokeAllUserToken(User user){
        var validUserToken = tokenRepository.findAllValidTokenByUser(user.getId());
        if(validUserToken.isEmpty()){
            return;
        }
        validUserToken.forEach(token -> {
                token.setExpired(true) ;
                token.setRevoked(true);

        });
        tokenRepository.saveAll(validUserToken);
    }


    private void saveUserToken(User user , String jwtToken){
           var token  = Token.builder()
                   .user(user)
                   .token(jwtToken)
                   .tokenType(TypeToken.BEARER)
                   .expired(false)
                   .revoked(false)
                   .build() ;

           tokenRepository.save(token);
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail() ,
                request.getPassword()
            )
        ) ;
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateRefreshToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserToken(user);
        saveUserToken(user , jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build() ;


    }

    public void refreshToken(
        HttpServletRequest request ,
        HttpServletResponse response
    ) throws IOException {
        final  String authHeader  =  request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken ;
        final  String userEmail ;
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if(userEmail != null){
            var user = this.repository.findByEmail(userEmail)
                    .orElseThrow();

            if(jwtService.isTokenValid(refreshToken , user)){
                var accessToken = jwtService.generateToken(user);
                revokeAllUserToken(user);
                saveUserToken(user , accessToken);

                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream() , authResponse);
            }
        }


    }


    public List<User> getAllUser(){
        return repository.findAll() ;
    }

}
