package com.cmp.springionic.config;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.cmp.springionic.domain.Client;

@Service
public class JWTTokenService {
	
	@Value("${jwt.secret}")
	private String jwtSecret;
	
	@Value("${jwt.expiration}")
	private Integer jwtExpiration;
	
	@Value("${jwt.issuer}")
	private String issuer;
    
    public String generateToken(Client client){
        try {
            Algorithm algorithm = Algorithm.HMAC512(jwtSecret);

            String token = JWT.create()
                    .withIssuer(issuer)
                    .withSubject(client.getEmail())
                    .withExpiresAt(this.generateExpirationDate())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception){
            throw new RuntimeException("Error while authenticating");
        }
    }

    public boolean validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC512(jwtSecret);
            var username = JWT.require(algorithm)
            		.withIssuer(issuer)
            		.build()
            		.verify(token)
            		.getSubject();
            
            if (username != null) {
            	return true;
            }
            
//            return JWT.require(algorithm)
//                    .withIssuer(issuer)
//                    .build()
//                    .verify(token)
//                    .getSubject();
        } catch (JWTVerificationException exception) {
        	return false;
//            return null;
        }
		return false;
    }

    private Instant generateExpirationDate(){
        return LocalDateTime.now().plusSeconds(jwtExpiration).toInstant(ZoneOffset.of("-03:00"));
    }

	public String getUsername(String token) {
        Algorithm algorithm = Algorithm.HMAC512(jwtSecret);
        var decodedToken = JWT.require(algorithm).withIssuer(issuer).build().verify(token);
        if (decodedToken != null) {
            return decodedToken.getSubject();        	
        }
		return null;
	}
}
