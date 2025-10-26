CREATE TABLE client_info (
    client_id VARCHAR(50) PRIMARY KEY,
    client_password VARCHAR(255) NOT NULL,
    roles VARCHAR(1000) NOT NULL,
    create_date TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    update_date TIMESTAMP WITH TIME ZONE,
    last_password_change_date TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    expiry_date TIMESTAMP WITH TIME ZONE,
    CONSTRAINT chk_client_password CHECK (client_password <> '')
);