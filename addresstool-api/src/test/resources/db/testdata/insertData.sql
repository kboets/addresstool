INSERT INTO addresstool.person (id, first_name, last_name, birth_date, street, number, box, postal_code, city_name)
VALUES (1, 'John', 'Doh', '1975-02-04', 'Kaulillerweg', 105, null, '3910', 'Pelt');

INSERT INTO addresstool.person (id, first_name, last_name, birth_date, street, number, box, postal_code, city_name)
VALUES (2, 'Jane', 'Smith', '1980-05-12', 'Hansastraat', 10, null, '3910', 'Pelt');

INSERT INTO addresstool.person (id, first_name, last_name, birth_date, street, number, box, postal_code, city_name)
VALUES (3, 'Alice', 'Johnson', '1992-11-23', 'Brusselsestraat', 105, null, '3000', 'Leuven');

INSERT INTO addresstool.person (id, first_name, last_name, birth_date, street, number, box, postal_code, city_name)
VALUES (4, 'John', 'Doh', '1992-12-04', 'Demerstraat', 79, null, '3271', 'Averbode');


-- Ensure the sequence is aligned with the max(id) in the table, so subsequent JPA inserts don't collide.
SELECT setval('addresstool.person_id_seq',
              (SELECT COALESCE(MAX(id), 0) FROM addresstool.person),
              true);
