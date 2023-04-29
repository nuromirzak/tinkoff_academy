INSERT INTO link VALUES (20, 'https://github.com/nuromirzak/tinkoff_academy/', '2022-01-01 00:00:00', '2022-01-01 00:00:00');
INSERT INTO link VALUES (30, 'https://stackoverflow.com/questions/5585779/', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO link VALUES (40, 'https://github.com/torvalds/linux', '1970-01-01 00:00:00', '1970-01-01 00:00:00');
INSERT INTO link VALUES (50, 'https://stackoverflow.com/questions/24114676/', '2022-01-01 00:00:00', '2022-01-01 00:00:00');
INSERT INTO link VALUES (60, 'https://github.com/facebook/react', '2022-01-01 01:00:00', '2022-01-01 01:00:00');

INSERT INTO chat VALUES (362037700, '2023-04-01 00:00:00');
INSERT INTO chat VALUES (362037701, CURRENT_TIMESTAMP);

INSERT INTO link_chat VALUES (362037700, 20);
INSERT INTO link_chat VALUES (362037700, 30);
INSERT INTO link_chat VALUES (362037701, 40);