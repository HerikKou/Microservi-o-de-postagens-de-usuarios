package Usuarios.Config;

import java.time.Instant;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import Usuarios.Model.UserModel;

@Component
public class TokenConfig {
    private  String secretKey ="ChaveSecreta";//Define o token 

  
    public String generatedToken(UserModel user){
        Algorithm algorithm = Algorithm.HMAC256(secretKey);//criptografa esse token com HMAC
            return JWT.create()//cria a estrutura
            .withClaim("userId", user.getId())//define um usuário
            .withSubject(user.getEmail())//define o email
            .withExpiresAt(Instant.now().plusSeconds(86400))//defini os segundos 
            .withIssuedAt(Instant.now())//pega  a data que foi gerado o token
            .sign(algorithm);//assina o token
    }
    public Optional<JWTUserData> validateToken(String token){
        try {
             Algorithm algorithm = Algorithm.HMAC256(secretKey);
         DecodedJWT decode = JWT.require(algorithm).build().verify(token); 
         return Optional.of(JWTUserData.builder().id(decode.getClaim("userId").asLong()).email(decode.getSubject()).build());
        } catch (JWTVerificationException e) {
            return Optional.empty();
        }
    }
}
