package com.ecs.token_service.controller.v1;

import com.ecs.token_service.api.v1.TokenApiV1;
import com.ecs.token_service.models.v1.TokenRequest;
import com.ecs.token_service.models.v1.TokenResponse;
import com.ecs.token_service.models.v1.TokenValidationRequest;
import com.ecs.token_service.models.v1.TokenValidationResponse;
import com.ecs.token_service.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenController implements TokenApiV1 {

    private final TokenService tokenService;

    @Override
    public ResponseEntity<TokenResponse> generateToken(TokenRequest request) {
        TokenResponse response = tokenService.generateToken(request);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<TokenValidationResponse> validateToken(TokenValidationRequest request) {
        TokenValidationResponse response = tokenService.validateToken(request);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<TokenResponse> refreshToken(TokenRequest request) {
        TokenResponse response = tokenService.refreshToken(request);
        return ResponseEntity.ok(response);
    }
}