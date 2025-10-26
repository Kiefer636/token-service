package com.ecs.token_service.models.v1;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ClientCreateResponse {

    @NotBlank(message = "Client ID is required")
    private String clientId;

    private List<String> roles;

    private Date expiryDate;
}
