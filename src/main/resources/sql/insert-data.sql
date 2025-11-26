
DELETE FROM follower;
DELETE FROM post;
DELETE FROM "user";

INSERT INTO "user" (username, display_name, email, password_hash) VALUES
  ('jan',   'Jan Kowalski',     'jan.kowalski@example.pl',   '$2a$10$examplehash1'),
  ('anna',  'Anna Nowak',       'anna.nowak@example.pl',     '$2a$10$examplehash2'),
  ('marek', 'Marek Wiśniewski', 'marek.wisniewski@example.pl','$2a$10$examplehash3');

INSERT INTO post (author_id, content) VALUES
  ((SELECT id FROM "user" WHERE username = 'jan'),   'Cześć MicroBlog!'),
  ((SELECT id FROM "user" WHERE username = 'anna'),  'Witajcie, miłego dnia!'),
  ((SELECT id FROM "user" WHERE username = 'marek'), 'Dzisiaj testuję aplikację.');

INSERT INTO follower (follower_id, followed_id) VALUES
  ((SELECT id FROM "user" WHERE username = 'jan'),   (SELECT id FROM "user" WHERE username = 'anna')),
  ((SELECT id FROM "user" WHERE username = 'marek'), (SELECT id FROM "user" WHERE username = 'jan')),
  ((SELECT id FROM "user" WHERE username = 'anna'),  (SELECT id FROM "user" WHERE username = 'jan'));
