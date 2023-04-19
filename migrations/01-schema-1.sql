CREATE TABLE link
(
    link_id      SERIAL PRIMARY KEY,
    url          VARCHAR(256) NOT NULL UNIQUE,
    last_updated TIMESTAMP    NOT NULL,
    json_props   JSONB
);

CREATE TABLE link_chat
(
    chat_id BIGINT NOT NULL,
    link_id BIGINT NOT NULL,
    PRIMARY KEY (chat_id, link_id),
    FOREIGN KEY (link_id) REFERENCES link (link_id)
);

CREATE TABLE chat
(
    chat_id  BIGINT PRIMARY KEY,
    reg_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);