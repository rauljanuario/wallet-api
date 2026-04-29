CREATE TABLE IF NOT EXISTS transactions(

    id BIGSERIAL PRIMARY KEY,
    sender_account_id BIGINT NOT NULL,
    receiver_account_id BIGINT NOT NULL,
    amount DECIMAL(19,2) NOT NULL,
    timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_sender_account FOREIGN KEY (sender_account_id) REFERENCES accounts(id),
    CONSTRAINT fk_receiver_account FOREIGN KEY (receiver_account_id) REFERENCES accounts(id)

);