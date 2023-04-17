INSERT INTO link VALUES (20, 'https://github.com/nuromirzak/tinkoff_academy/', '2022-01-01 00:00:00');
INSERT INTO link VALUES (30, 'https://stackoverflow.com/questions/5585779/', CURRENT_TIMESTAMP);
INSERT INTO link VALUES (40, 'https://github.com/torvalds/linux', '1970-01-01 00:00:00');

INSERT INTO subscription (chat_id, link_id) VALUES (362037700, 20);
INSERT INTO subscription (chat_id, link_id) VALUES (362037700, 30);