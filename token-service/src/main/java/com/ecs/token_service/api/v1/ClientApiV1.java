package com.ecs.token_service.api.v1;

import com.ecs.token_service.models.v1.ClientCreateRequest;
import com.ecs.token_service.models.v1.ClientCreateResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/client")
public interface ClientApiV1 {

    @PostMapping("/create")
    ResponseEntity<ClientCreateResponse> createClient(@Valid @RequestBody ClientCreateRequest request);
}
