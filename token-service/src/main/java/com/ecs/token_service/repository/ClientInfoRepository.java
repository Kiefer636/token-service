package com.ecs.token_service.repository;

import com.ecs.token_service.models.entity.ClientInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientInfoRepository extends JpaRepository<ClientInfoEntity, String> {
}
