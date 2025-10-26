package com.ecs.token_service.util;

import com.ecs.token_service.models.entity.ClientInfoEntity;
import com.ecs.token_service.models.v1.ClientCreateResponse;

import java.util.Arrays;
import java.util.List;

public class ClientMappingUtil {

    public ClientCreateResponse createResponseFromEntity(ClientInfoEntity clientInfoEntity) {
        ClientCreateResponse response = new ClientCreateResponse();
        response.setClientId(clientInfoEntity.getClientId());
        response.setRoles(clientInfoEntity.getRoles() != null ? Arrays.asList(clientInfoEntity.getRoles().split(",")) : List.of());
        response.setExpiryDate(clientInfoEntity.getExpiryDate());
        return response;
    }

    public List<String> rolesFromString(String roles) {
        return roles != null ? Arrays.asList(roles.split(",")) : List.of();
    }
}
