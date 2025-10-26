package com.ecs.token_service.service;

import com.ecs.token_service.models.entity.ClientInfoEntity;
import com.ecs.token_service.models.v1.ClientCreateRequest;
import com.ecs.token_service.repository.ClientInfoRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientInfoService {

    private final ClientInfoRepository clientInfoRepository;
    private final AuthService authService;

    @Value("${token.password.default.expiration.days:365}")
    private String defaultExpiryDays;

    public ClientInfoEntity createClient(ClientCreateRequest clientCreateRequest) {
        ClientInfoEntity clientInfoEntity = new ClientInfoEntity();
        clientInfoEntity.setClientId(clientCreateRequest.getClientId().toLowerCase());
        clientInfoEntity.setClientPassword(authService.encodePassword(clientCreateRequest.getClientSecret()));
        if(clientCreateRequest.getRoles() != null && !clientCreateRequest.getRoles().isEmpty()) {
            clientInfoEntity.setRoles(rolesToString(clientCreateRequest.getRoles()).toLowerCase());
        }
        if(clientCreateRequest.getExpiryInDays() <= 0) {
            clientInfoEntity.setExpiryDate(Date.from(OffsetDateTime.now().plusDays(Long.parseLong(defaultExpiryDays)).toInstant()));
        }
        return clientInfoRepository.save(clientInfoEntity);
    }

    public ClientInfoEntity getClientInfoById(String clientId) {
        return clientInfoRepository.findById(clientId.toLowerCase()).orElse(null);
    }

    public List<ClientInfoEntity> getAllClientInfo() {
        return clientInfoRepository.findAll();
    }

    private String rolesToString(List<String> roles) {
        return String.join(",", roles);
    }
}
