CREATE TABLE link
(
    link_id      SERIAL PRIMARY KEY,
    url          VARCHAR(256) NOT NULL UNIQUE,
    last_updated TIMESTAMP    NOT NULL,
    last_scrapped TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    json_props   JSON
);

COMMENT ON COLUMN link.link_id IS 'Уникальный идентификатор ссылки в базе данных';
COMMENT ON COLUMN link.url IS 'URL отслеживаемой ссылки';
COMMENT ON COLUMN link.last_updated IS 'Время последнего действия с этой ссылкой';
COMMENT ON COLUMN link.last_scrapped IS 'Время последнего скрапинга этой ссылки';
COMMENT ON COLUMN link.json_props IS 'Дополнительные свойства ссылки в формате JSON';

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

COMMENT ON COLUMN chat.chat_id IS 'Telegram ID чата';
COMMENT ON COLUMN chat.reg_date IS 'Дата регистрации чата';