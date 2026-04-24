CREATE SEQUENCE city_id_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;

CREATE TABLE city(
                        id INT primary key,
                        name VARCHAR NOT NULL,
                        postal_code VARCHAR NOT NULL,
                        main BOOLEAN
);

CREATE INDEX city_name_idx ON city(name, postal_code);
