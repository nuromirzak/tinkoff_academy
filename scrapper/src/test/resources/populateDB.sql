INSERT INTO link
VALUES (100001, 'https://github.com/nuromirzak/tinkoff_academy/', '2022-01-01 00:00:00', '2022-01-01 00:00:00');
INSERT INTO link
VALUES (100002, 'https://stackoverflow.com/questions/5585779/', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO link
VALUES (100003, 'https://github.com/torvalds/linux', '1970-01-01 00:00:00', '1970-01-01 00:00:00');
INSERT INTO link
VALUES (100004, 'https://stackoverflow.com/questions/24114676/', '2022-01-01 00:00:00', '2022-01-01 00:00:00');
INSERT INTO link
VALUES (100005, 'https://github.com/facebook/react', '2022-01-01 01:00:00', '2022-01-01 01:00:00');

INSERT INTO chat
VALUES (362037700, '2023-04-01 00:00:00');
INSERT INTO chat
VALUES (362037701, CURRENT_TIMESTAMP);

INSERT INTO link_chat
VALUES (362037700, 100001);
INSERT INTO link_chat
VALUES (362037700, 100002);
INSERT INTO link_chat
VALUES (362037701, 100003);
