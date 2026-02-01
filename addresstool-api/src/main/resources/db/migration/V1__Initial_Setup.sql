CREATE SEQUENCE person_id_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;

CREATE TABLE person(
                       id INT primary key,
                       last_name VARCHAR NOT NULL,
                       first_name VARCHAR NOT NULL,
                       birth_date DATE NOT NULL,
                       street VARCHAR NOT NULL,
                       number NUMERIC NOT NULL,
                       box VARCHAR,
                       postal_code VARCHAR NOT NULL,
                       city_name VARCHAR NOT NULL
);

CREATE INDEX person_name_idx ON person(first_name, last_name);
