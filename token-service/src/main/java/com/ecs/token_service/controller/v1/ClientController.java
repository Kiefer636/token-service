package com.ecs.token_service.controller.v1;

import com.ecs.token_service.api.v1.ClientApiV1;
import com.ecs.token_service.models.entity.ClientInfoEntity;
import com.ecs.token_service.models.v1.ClientCreateRequest;
import com.ecs.token_service.models.v1.ClientCreateResponse;
import com.ecs.token_service.service.ClientInfoService;
import com.ecs.token_service.util.ClientMappingUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ClientController implements ClientApiV1 {

    private final ClientInfoService clientInfoService;
    private static final ClientMappingUtil clientMappingUtil = new ClientMappingUtil();


    @Override
    public ResponseEntity<ClientCreateResponse> createClient(ClientCreateRequest request) {
        ClientInfoEntity entity = clientInfoService.createClient(request);
        log.info("Client: {} created successfully. Expires on: {}", entity.getClientId(), entity.getExpiryDate().toString());
        return ResponseEntity.ok(clientMappingUtil.createResponseFromEntity(entity));
    }
}
