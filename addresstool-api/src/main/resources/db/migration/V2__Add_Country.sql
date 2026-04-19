CREATE SEQUENCE country_id_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;

CREATE TABLE country(
                       id INT primary key,
                       name VARCHAR NOT NULL,
                       country_code VARCHAR NOT NULL,
                       flag_url VARCHAR NOT NULL,
                       phone_code VARCHAR NOT NULL
);

CREATE INDEX country_name_idx ON country(name, country_code);
