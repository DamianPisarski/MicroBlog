DELETE FROM follower;
DELETE FROM post;
DELETE FROM "user";

INSERT INTO "user" (username, display_name, email, password_hash) VALUES
  ('jan',   'Jan Kowalski',     'jan.kowalski@example.pl',   '$2a$10$noxdBSQeRSYTYb15ovMMseanNgwAgMFWJwoi/rnmHICEqFLA2YUp2'),
  ('anna',  'Anna Nowak',       'anna.nowak@example.pl',     '$2a$10$U2C7jDr5IppJFZGBqvORi./6yVQwsGv9sqEM8gEpbGzrTH8r9Agja'),
  ('marek', 'Marek Wiśniewski', 'marek.wisniewski@example.pl','$2a$10$mgruYX53ifsXmH9UQABUtOshS4T5ysxgOsBtdnIhNkHv7QW2q1Wsq');

INSERT INTO post (author_id, content) VALUES
  ((SELECT id FROM "user" WHERE username = 'marek'),   'Cześć MicroBlog!'),
  ((SELECT id FROM "user" WHERE username = 'jan'),  'Witajcie, miłego dnia!'),
  ((SELECT id FROM "user" WHERE username = 'jan'), 'Dzisiaj testuję aplikację.');

INSERT INTO follower (follower_id, followed_id) VALUES
  ((SELECT id FROM "user" WHERE username = 'marek'),   (SELECT id FROM "user" WHERE username = 'jan')),
  ((SELECT id FROM "user" WHERE username = 'anna'), (SELECT id FROM "user" WHERE username = 'jan')),
  ((SELECT id FROM "user" WHERE username = 'jan'),  (SELECT id FROM "user" WHERE username = 'anna'));
