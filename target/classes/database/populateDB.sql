DELETE FROM info;
DELETE FROM movie;

ALTER TABLE movie AUTO_INCREMENT = 1;

INSERT INTO movie (title)
VALUES ('Movie1'), ('Movie2'), ('Movie3');

INSERT INTO info (movie_id, description, year)
VALUES (1, 'First Book', 2001), (2, 'Second book', 2002), (3, 'Third book', 2003);