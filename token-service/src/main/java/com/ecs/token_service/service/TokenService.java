package com.ecs.token_service.service;

import com.ecs.token_service.models.entity.ClientInfoEntity;
import com.ecs.token_service.models.v1.TokenRequest;
import com.ecs.token_service.models.v1.TokenResponse;
import com.ecs.token_service.models.v1.TokenValidationRequest;
import com.ecs.token_service.models.v1.TokenValidationResponse;
import com.ecs.token_service.util.ClientMappingUtil;
import com.nimbusds.jose.*;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {
    
    private final JWSSigner signer;
    private final JWSVerifier verifier;

    private final ClientInfoService clientInfoService;
    private final AuthService authService;
    private static final ClientMappingUtil clientMappingUtil = new ClientMappingUtil();
    
    @Value("${token.expiration.access:3600}")
    private long accessTokenExpiration;
    
    @Value("${token.expiration.refresh:86400}")
    private long refreshTokenExpiration;

    @Value("${token.issuer:token-service}")
    private String issuer;

    
    public TokenResponse generateToken(TokenRequest request) {
        try {
            ClientInfoEntity clientInfoEntity = validateClient(request);
            List<String> scopes = clientMappingUtil.rolesFromString(clientInfoEntity.getRoles());
            List<String> refreshScope = List.of("refresh");
            
            String accessToken = createJWT(request.getClientId(), accessTokenExpiration, scopes);
            String refreshToken = createJWT(request.getClientId(), refreshTokenExpiration, refreshScope);
            
            return TokenResponse.builder()
                    .accessToken(accessToken)
                    .tokenType("Bearer")
                    .expiresIn(accessTokenExpiration)
                    .refreshToken(refreshToken)
                    .scope(scopes != null ? String.join(",", scopes) : "")
                    .build();
        } catch (Exception e) {
            log.error("Error generating token", e);
            throw new RuntimeException("Failed to generate token", e);
        }
    }
    
    public TokenValidationResponse validateToken(TokenValidationRequest request) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(request.getToken());
            
            if (!signedJWT.verify(verifier)) {
                return TokenValidationResponse.builder()
                        .valid(false)
                        .error("Invalid signature")
                        .build();
            }
            
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
            Date expirationTime = claims.getExpirationTime();
            
            if (expirationTime.before(new Date())) {
                return TokenValidationResponse.builder()
                        .valid(false)
                        .error("Token expired")
                        .build();
            }
            
            return TokenValidationResponse.builder()
                    .valid(true)
                    .subject(claims.getSubject())
                    .scopes(claims.getStringListClaim("scopes"))
                    .expiresAt(expirationTime.getTime() / 1000)
                    .claims(claims.getClaims())
                    .build();
        } catch (Exception e) {
            log.error("Error validating token", e);
            return TokenValidationResponse.builder()
                    .valid(false)
                    .error("Invalid token format")
                    .build();
        }
    }
    
    public TokenResponse refreshToken(TokenRequest request) {
        TokenValidationRequest validationRequest = new TokenValidationRequest();
        validationRequest.setToken(request.getRefreshToken());
        
        TokenValidationResponse validation = validateToken(validationRequest);
        if (!validation.isValid()) {
            throw new RuntimeException("Invalid refresh token");
        }

        return generateToken(request);
    }
    
    private String createJWT(String subject, long expirationSeconds, java.util.List<String> scopes) throws JOSEException {
        Instant now = Instant.now();
        
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(subject)
                .issuer(issuer)
                .issueTime(Date.from(now))
                .expirationTime(Date.from(now.plusSeconds(expirationSeconds)))
                .jwtID(UUID.randomUUID().toString())
                .claim("scopes", scopes)
                .build();
        
        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader(JWSAlgorithm.HS256),
                claimsSet
        );
        
        signedJWT.sign(signer);
        return signedJWT.serialize();
    }
    
    private ClientInfoEntity validateClient(TokenRequest request) {
        ClientInfoEntity clientInfo = clientInfoService.getClientInfoById(request.getClientId());
        if(authService.matchesPassword(request.getClientSecret(), clientInfo.getClientPassword())) {
            log.info("Client {} validated successfully", request.getClientId());
            return clientInfo;
        } else {
            throw new RuntimeException("Invalid client credentials");
        }
    }
}
