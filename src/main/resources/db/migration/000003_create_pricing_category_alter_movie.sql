CREATE TABLE pricing_caregory
(
    id bigint NOT NULL DEFAULT nextval('pricing_caregory_id_seq'::regclass),
    additional_cost double precision,
    cutoff_days integer,
    initial_cost double precision,
    name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT pricing_caregory_pkey PRIMARY KEY (id)
);


ALTER TABLE movie
    ADD COLUMN pricing_category_id bigint;

ALTER TABLE movie
    ADD CONSTRAINT movie_pricing_category_fk
    FOREIGN KEY (pricing_category_id)
    REFERENCES pricing_caregory(id);
