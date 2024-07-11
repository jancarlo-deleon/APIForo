package com.jdeleonc.foro.forohub.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import com.jdeleonc.foro.forohub.model.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.secret}")
    private String apiSecret;

    public String generarToken(Usuario usuario){
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            String token = JWT.create()
                    .withIssuer("voll med")
                    .withSubject(usuario.getUsername())
                    .withClaim("id",usuario.getId())
                    .withExpiresAt(generarFechExpiracion())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception){
            throw new RuntimeException();
        }
    }

    private Instant generarFechExpiracion(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-06:00"));
    }

    public String getSubject(String token){

        if (token == null){
            throw  new RuntimeException("Token es nnulo");
        }

        DecodedJWT decodedJWT = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("voll med")
                    .build();

            decodedJWT = verifier.verify(token);
            decodedJWT.getSubject();
        } catch (JWTVerificationException exception){
            exception.getStackTrace();
        }
        if (decodedJWT.getSubject() == null){
            throw  new RuntimeException("Verifier Invalido");
        }
        return decodedJWT.getSubject();
    }

}