package com.ecs.token_service.models.v1;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class TokenValidationResponse {

    private boolean valid;

    private String subject;

    private List<String> scopes;

    private Long expiresAt;

    private Map<String, Object> claims;

    private String error;
}
