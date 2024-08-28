package com.example.posts.post_params.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.posts.post_params.domain.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {


    @Value("${api.security.token.secret}")
    private String secret;

    public String gerenateToken(User user){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getLogin())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
            return token;
        }catch (JWTCreationException exception){
            throw  new RuntimeException("Erro ao gerar a token: ", exception);
        }
    }

    public String validadeToken(String token){
       try{
           Algorithm algorithm = Algorithm.HMAC256(secret);
           return JWT.require(algorithm)
                   .withIssuer("auth-api")
                   .build()
                   .verify(token)
                   .getSubject();
       }catch (TokenExpiredException exception) {

           throw new RuntimeException("Token expirado!", exception);
       } catch (JWTCreationException exception) {

           throw new RuntimeException("Erro ao validar o token: ", exception);
       }
    }

    private Instant generateExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
