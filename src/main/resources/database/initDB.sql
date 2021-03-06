DROP TABLE IF EXISTS movie;
DROP TABLE IF EXISTS info;

CREATE TABLE movie
(
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL
) ENGINE = InnoDB;

CREATE TABLE info
(
    movie_id INTEGER,
    description VARCHAR(200) NOT NULL,
    year INTEGER,
    CONSTRAINT fk_movie FOREIGN KEY (movie_id) REFERENCES movie (id) ON DELETE CASCADE
) ENGINE = InnoDB;