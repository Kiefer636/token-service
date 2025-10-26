package com.ecs.token_service.models.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CLIENT_INFO", schema = "TOKEN-SERVICE")
public class ClientInfoEntity {

    @Id
    @Column(name = "CLIENT_ID")
    private String clientId;

    @Column(name = "CLIENT_PASSWORD")
    private String clientPassword;

    @Column(name = "ROLES")
    private String roles;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DATE")
    private Date createDate = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATE_DATE")
    private Date updateDate = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_PASSWORD_CHANGE_DATE")
    private Date lastPasswordChangeDate = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIRY_DATE")
    private Date expiryDate = new Date();
}
