package com.ecs.token_service.config;


import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Value("${token.secret}")
    private String secret;

    @Bean
    public JWSSigner getJWSSigner() throws Exception {
        byte[] secretKey = java.util.Base64.getDecoder().decode(secret);
        if (secretKey.length < 32) {
            throw new IllegalArgumentException("JWT secret must be at least 256 bits");
        }
        return new MACSigner(secretKey);
    }

    @Bean
    public JWSVerifier jwsVerifier() throws Exception {
        byte[] secretKey = java.util.Base64.getDecoder().decode(secret);
        return new MACVerifier(secretKey);
    }
}
