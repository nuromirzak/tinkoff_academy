CREATE TABLE link
(
    link_id      BIGINT       NOT NULL PRIMARY KEY,
    url          VARCHAR(256) NOT NULL UNIQUE,
    last_updated TIMESTAMP    NOT NULL
);

CREATE TABLE chat
(
    chat_id BIGINT NOT NULL,
    link_id BIGINT NOT NULL,
    FOREIGN KEY (link_id) REFERENCES Link (link_id)
);