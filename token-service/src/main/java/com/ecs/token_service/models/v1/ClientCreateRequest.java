package com.ecs.token_service.models.v1;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ClientCreateRequest {

    @NotNull(message = "Client name is required")
    @NotBlank(message = "Client ID is required")
    private String clientId;

    @NotNull(message = "Client secret is required")
    @NotBlank(message = "Client secret is required")
    private String clientSecret;

    private List<String> roles;

    private int expiryInDays;
}
