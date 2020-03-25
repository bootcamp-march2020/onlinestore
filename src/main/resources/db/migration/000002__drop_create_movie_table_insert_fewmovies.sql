
DROP TABLE IF EXISTS movie;

CREATE TABLE movie
(
    mid bigint NOT NULL DEFAULT nextval('movie_mid_seq'::regclass),
    title character varying(255) COLLATE pg_catalog."default",
    description character varying(255) COLLATE pg_catalog."default",
    poster_url character varying(255) COLLATE pg_catalog."default",
    release_date timestamp without time zone,
    type character varying(255) COLLATE pg_catalog."default",
    year integer,
    rated character varying(255) COLLATE pg_catalog."default",
    ratings double precision,
    CONSTRAINT movie_pkey PRIMARY KEY (mid)
)

INSERT INTO movie (mid, title, description, poster_url, release_date, type, year, rated, ratings) VALUES (1, 'Iron Man', 'After being held captive in an Afghan cave, billionaire engineer Tony Stark creates a unique weaponized suit of armor to fight evil.', 'https://m.media-amazon.com/images/M/MV5BMTczNTI2ODUwOF5BMl5BanBnXkFtZTcwMTU0NTIzMw@@._V1_SX300.jpg', '2008-05-02', 'Movie', 2008, 'PG-13', 7.9);
INSERT INTO movie (mid, title, description, poster_url, release_date, type, year, rated, ratings) VALUES (2, 'The Avengers', 'Earth`s mightiest heroes must come together and learn to fight as a team if they are going to stop the mischievous Loki and his alien army from enslaving humanity.', 'https://m.media-amazon.com/images/M/MV5BNDYxNjQyMjAtNTdiOS00NGYwLWFmNTAtNThmYjU5ZGI2YTI1XkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_SX300.jpg', '2012-05-04', 'Movie', 2012, 'PG-13', 8);
INSERT INTO movie (mid, title, description, poster_url, release_date, type, year, rated, ratings) VALUES (3, 'Spider', 'A mentally disturbed man takes residence in a halfway house. His mind gradually slips back into the realm created by his illness, where he replays a key part of his childhood.', 'https://m.media-amazon.com/images/M/MV5BMmY4OGRmNWMtNmIyNS00YWQ5LWJmMGUtMDI3MWRlMmQ0ZDQzL2ltYWdlXkEyXkFqcGdeQXVyNTAyODkwOQ@@._V1_SX300.jpg', '2002-11-13', 'Movie', 2002, 'R', 6.8);
INSERT INTO movie (mid, title, description, poster_url, release_date, type, year, rated, ratings) VALUES (4, 'Super Man', 'A man in a hospital wheelchair is brutally attacked with a bottle of his own urine. The attacker has his reasons, but revenge is not always sweet. A story about mental fragility, the randomness of fate and the complex nature of blame.', 'https://upload.wikimedia.org/wikipedia/en/3/35/Supermanflying.png', '2010-01-01', 'Movie', 2010, 'N/A', 5.9);
INSERT INTO movie (mid, title, description, poster_url, release_date, type, year, rated, ratings) VALUES (5, 'Lucy', 'A woman, accidentally caught in a dark deal, turns the tables on her captors and transforms into a merciless warrior evolved beyond human logic.', 'https://m.media-amazon.com/images/M/MV5BODcxMzY3ODY1NF5BMl5BanBnXkFtZTgwNzg1NDY4MTE@._V1_SX300.jpg', '2014-07-24', 'Movie', 2014, 'R', 6.4);
INSERT INTO movie (mid, title, description, poster_url, release_date, type, year, rated, ratings) VALUES (6, 'Looper', 'In 2074, when the mob wants to get rid of someone, the target is sent into the past, where a hired gun awaits - someone like Joe - who one day learns the mob wants to "close the loop" by sending back Joe`s future self for assassination.', 'https://m.media-amazon.com/images/M/MV5BMTg5NTA3NTg4NF5BMl5BanBnXkFtZTcwNTA0NDYzOA@@._V1_SX300.jpg', '2012-09-28', 'Movie', 2012, 'R', 7.4);
INSERT INTO movie (mid, title, description, poster_url, release_date, type, year, rated, ratings) VALUES (7, 'Pulpfiction', 'This program profiles writers of the 20s, 30s, and 40s, known as the Pulp Fiction-era. These writers created the iconic characters who live on today in our collective hearts and ...",', 'https://m.media-amazon.com/images/M/MV5BMTc2MjAwMjYxOV5BMl5BanBnXkFtZTgwODIxNzg4NjE@._V1_SX300.jpg', '2009-03-01', 'Movie', 2009, 'N/A', 1.8);
INSERT INTO movie (mid, title, description, poster_url, release_date, type, year, rated, ratings) VALUES (8, 'Megamind', 'The supervillain Megamind finally defeats his nemesis, the superhero Metro Man. But without a hero, he loses all purpose and must find new meaning to his life.', 'https://m.media-amazon.com/images/M/MV5BMTAzMzI0NTMzNDBeQTJeQWpwZ15BbWU3MDM3NTAyOTM@._V1_SX300.jpg', '2010-11-05', 'Movie', 2010, 'PG', 7.2);
INSERT INTO movie (mid, title, description, poster_url, release_date, type, year, rated, ratings) VALUES (9, 'Alice', 'A spoiled Manhattan housewife re-evaluates her life after visiting a Chinatown healer.', 'https://m.media-amazon.com/images/M/MV5BNDQ4YzdmY2MtYzkwOS00Njk5LWI5MWUtNGQ0NzVhODIxYzkwXkEyXkFqcGdeQXVyNzc5MjA3OA@@._V1_SX300.jpg', '1991-01-10', 'Movie', 1991, 'PG-13', 6.6);
INSERT INTO movie (mid, title, description, poster_url, release_date, type, year, rated, ratings) VALUES (10, 'Python', 'After a military plane crash near a small American town, a giant man-eating snake sets off on a killing spree. The locals must find a way to eliminate the snake, with the help of a scientist who knows about the snake and terminates it.', 'https://m.media-amazon.com/images/M/MV5BZGZlNWU0ZWEtNzY4MC00NDdmLTgzOTktOTgzMjBlNGMxN2ZmL2ltYWdlL2ltYWdlXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg', '2001-08-01', 'Movie', 2001, 'R', 3.7);