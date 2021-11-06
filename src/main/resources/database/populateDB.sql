DELETE FROM info;
DELETE FROM movie;

ALTER TABLE movie AUTO_INCREMENT = 1;

INSERT INTO movie (title)
VALUES ('Movie1'), ('Movie2'), ('Movie3');

INSERT INTO info (movie_id, description, year)
VALUES (1, 'First Movie', 2001), (2, 'Second Movie', 2002), (3, 'Third Movie', 2003);