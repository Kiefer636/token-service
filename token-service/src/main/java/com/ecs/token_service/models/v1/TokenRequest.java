package com.ecs.token_service.models.v1;

import com.ecs.token_service.models.validationGroups.ClientCredentials;
import com.ecs.token_service.models.validationGroups.RefreshToken;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TokenRequest {
    @NotNull(message = "Client ID is required", groups = {ClientCredentials.class})
    @NotBlank(message = "Client ID is required", groups = {ClientCredentials.class})
    private String clientId;

    @NotNull(message = "Client secret is required", groups = {ClientCredentials.class})
    @NotBlank(message = "Client secret is required", groups = {ClientCredentials.class})
    private String clientSecret;

    @NotNull(message = "Grant type is required", groups = {ClientCredentials.class, RefreshToken.class})
    @NotBlank(message = "Grant type is required", groups = {ClientCredentials.class, RefreshToken.class})
    private String grantType;

    @NotNull(message = "Scope is required", groups = {RefreshToken.class})
    @NotBlank(message = "Scope is required", groups = {RefreshToken.class})
    private String refreshToken;
}
