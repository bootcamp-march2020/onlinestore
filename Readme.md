

# Set up db in local machine
`brew install postgresql`

`brew services start postgresql`

`psql postgres`

#postgressql command:

`create user postgres;`
`ALTER USER postgres WITH SUPERUSER;`
`CREATE DATABASE movierental;`
`\c movierental`
`CREATE TABLE public.movie (
     mid bigint NOT NULL,
     title character varying(255)
 );`
 `CREATE SEQUENCE public.movie_mid_seq
      START WITH 1
      INCREMENT BY 1
      NO MINVALUE
      NO MAXVALUE
      CACHE 1;`
`INSERT INTO  movie (mid,title) VALUES(1, 'Iron Man'), (2, 'Super Man'), (3, 'Spider Man');`