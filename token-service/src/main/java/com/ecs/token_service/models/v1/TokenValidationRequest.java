package com.ecs.token_service.models.v1;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TokenValidationRequest {

    @NotBlank(message = "Token is required")
    private String token;
}
