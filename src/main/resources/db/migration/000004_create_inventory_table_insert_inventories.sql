CREATE TABLE public.movie_inventory (
    id bigint NOT NULL,
    available_count integer,
    total_count integer,
    movie_id bigint
);

CREATE SEQUENCE public.movie_inventory_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE ONLY public.movie_inventory
    ADD CONSTRAINT movie_inventory_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.movie_inventory
    ADD CONSTRAINT fk5uumswmn3ft55aghefxfa4nnp FOREIGN KEY (movie_id) REFERENCES public.movie(mid);


