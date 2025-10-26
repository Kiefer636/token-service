package com.ecs.token_service.api.v1;

import com.ecs.token_service.models.v1.TokenRequest;
import com.ecs.token_service.models.v1.TokenResponse;
import com.ecs.token_service.models.v1.TokenValidationRequest;
import com.ecs.token_service.models.v1.TokenValidationResponse;
import com.ecs.token_service.models.validationGroups.ClientCredentials;
import com.ecs.token_service.models.validationGroups.RefreshToken;
import jakarta.validation.Valid;
import jakarta.validation.groups.ConvertGroup;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/token")
public interface TokenApiV1 {

    @PostMapping("/generate")
    ResponseEntity<TokenResponse> generateToken(@Valid @RequestBody @ConvertGroup(to = ClientCredentials.class) TokenRequest request);

    @PostMapping("/validate")
    ResponseEntity<TokenValidationResponse> validateToken(@Valid @RequestBody TokenValidationRequest request);

    @PostMapping("/refresh")
    ResponseEntity<TokenResponse> refreshToken(@Valid @RequestBody @ConvertGroup(to = RefreshToken.class) TokenRequest request);
}