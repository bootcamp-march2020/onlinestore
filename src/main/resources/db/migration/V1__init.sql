-- public.actor definition

-- Drop table

-- DROP TABLE public.actor;

CREATE TABLE public.actor (
	id bigserial NOT NULL,
	"name" varchar(255) NULL,
	CONSTRAINT actor_pkey PRIMARY KEY (id)
);


-- public.director definition

-- Drop table

-- DROP TABLE public.director;

CREATE TABLE public.director (
	id bigserial NOT NULL,
	"name" varchar(255) NULL,
	CONSTRAINT director_pkey PRIMARY KEY (id)
);


-- public.genre definition

-- Drop table

-- DROP TABLE public.genre;

CREATE TABLE public.genre (
	id bigserial NOT NULL,
	"name" varchar(255) NULL,
	CONSTRAINT genre_pkey PRIMARY KEY (id)
);


-- public.imported_file definition

-- Drop table

-- DROP TABLE public.imported_file;

CREATE TABLE public.imported_file (
	id bigserial NOT NULL,
	file_name varchar(255) NULL,
	imported_time timestamp NULL,
	status_message varchar(255) NULL,
	CONSTRAINT imported_file_pkey PRIMARY KEY (id)
);


-- public.languages definition

-- Drop table

-- DROP TABLE public.languages;

CREATE TABLE public.languages (
	id bigserial NOT NULL,
	"name" varchar(255) NULL,
	CONSTRAINT languages_pkey PRIMARY KEY (id)
);


-- public.pricing_caregory definition

-- Drop table

-- DROP TABLE public.pricing_caregory;

CREATE TABLE public.pricing_caregory (
	id bigserial NOT NULL,
	additional_cost float8 NULL,
	cutoff_days int4 NULL,
	initial_cost float8 NULL,
	"name" varchar(255) NULL,
	CONSTRAINT pricing_caregory_pkey PRIMARY KEY (id)
);


-- public.users definition

-- Drop table

-- DROP TABLE public.users;

CREATE TABLE public.users (
	user_id varchar(255) NOT NULL,
	email varchar(255) NULL,
	"name" varchar(255) NULL,
	CONSTRAINT users_pkey PRIMARY KEY (user_id)
);


-- public.movie definition

-- Drop table

-- DROP TABLE public.movie;

CREATE TABLE public.movie (
	mid bigserial NOT NULL,
	description varchar(255) NULL,
	poster_url varchar(255) NULL,
	rated varchar(255) NULL,
	ratings float8 NULL,
	release_date timestamp NULL,
	title varchar(255) NULL,
	"type" varchar(255) NULL,
	"year" int4 NULL,
	pricing_category_id int8 NULL,
	CONSTRAINT movie_pkey PRIMARY KEY (mid),
	CONSTRAINT fkli1u0uk5x8018ic44lfnlfdcb FOREIGN KEY (pricing_category_id) REFERENCES pricing_caregory(id)
);


-- public.movie_actors definition

-- Drop table

-- DROP TABLE public.movie_actors;

CREATE TABLE public.movie_actors (
	movie_id int8 NOT NULL,
	actor_id int8 NOT NULL,
	CONSTRAINT movie_actors_pkey PRIMARY KEY (movie_id, actor_id),
	CONSTRAINT fkbsto8yef4btokhveihmkg8876 FOREIGN KEY (movie_id) REFERENCES movie(mid),
	CONSTRAINT fkcify69o6k32mj8hoya3skqghv FOREIGN KEY (actor_id) REFERENCES actor(id)
);


-- public.movie_directors definition

-- Drop table

-- DROP TABLE public.movie_directors;

CREATE TABLE public.movie_directors (
	movie_id int8 NOT NULL,
	director_id int8 NOT NULL,
	CONSTRAINT movie_directors_pkey PRIMARY KEY (movie_id, director_id),
	CONSTRAINT fklq7fbgdea8tjb0nuoj0h90ohu FOREIGN KEY (director_id) REFERENCES director(id),
	CONSTRAINT fktoqb71lhitfu7eyqnf2oxctyv FOREIGN KEY (movie_id) REFERENCES movie(mid)
);


-- public.movie_genres definition

-- Drop table

-- DROP TABLE public.movie_genres;

CREATE TABLE public.movie_genres (
	movie_id int8 NOT NULL,
	genre_id int8 NOT NULL,
	CONSTRAINT movie_genres_pkey PRIMARY KEY (movie_id, genre_id),
	CONSTRAINT fknup1hm4tk18om6dgawfgo5ay9 FOREIGN KEY (genre_id) REFERENCES genre(id),
	CONSTRAINT fks2xl3sirbon75mjcongwhrbi3 FOREIGN KEY (movie_id) REFERENCES movie(mid)
);


-- public.movie_inventory definition

-- Drop table

-- DROP TABLE public.movie_inventory;

CREATE TABLE public.movie_inventory (
	id bigserial NOT NULL,
	available_count int4 NULL,
	total_count int4 NULL,
	movie_id int8 NULL,
	CONSTRAINT movie_inventory_pkey PRIMARY KEY (id),
	CONSTRAINT fk5uumswmn3ft55aghefxfa4nnp FOREIGN KEY (movie_id) REFERENCES movie(mid)
);


-- public.movie_languages definition

-- Drop table

-- DROP TABLE public.movie_languages;

CREATE TABLE public.movie_languages (
	movie_id int8 NOT NULL,
	language_id int8 NOT NULL,
	CONSTRAINT movie_languages_pkey PRIMARY KEY (movie_id, language_id),
	CONSTRAINT fkl9pko34urm8ncqqeabmnad8x FOREIGN KEY (movie_id) REFERENCES movie(mid),
	CONSTRAINT fkn9wn0gt0dthk59mt3in6o14fq FOREIGN KEY (language_id) REFERENCES languages(id)
);