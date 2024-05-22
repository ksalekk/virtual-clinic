USE virtual_clinic;

-- DOCTORS
INSERT INTO app_user(user_role, username, user_password) 
VALUES (1, 'doctor1', '5f0a9c7f624afc79798e66d7fe80c2f0b85553e2d663fbabd45d4c47645e79b0');
SET @last_id = last_insert_id();
INSERT INTO doctor(id, firstname, lastname, license_number, phone_number, speciality) 
VALUES (@last_id, 'Ben', 'Saunders', '11171', '104837265', 'Cardiologist');

INSERT INTO app_user(user_role, username, user_password) 
VALUES (1, 'doctor2', 'a8b466a5ddeeb379556f30243d3bcb7377cc621860c3eca95fb18c23de9fefd9');
SET @last_id = last_insert_id();
INSERT INTO doctor(id, firstname, lastname, license_number, phone_number, speciality) 
VALUES (@last_id, 'Elizabeth', 'Combs', '72840', '592804602', 'Dermatologist');

INSERT INTO app_user(user_role, username, user_password) 
VALUES (1, 'doctor3', '6d958925e445546d1bf3f7580d4d610226b3460918e6c4c029490541803bc7c5');
SET @last_id = last_insert_id();
INSERT INTO doctor(id, firstname, lastname, license_number, phone_number, speciality) 
VALUES (@last_id, 'Mike', 'Grant', '27593', '104829340', 'Surgeon');


-- STAFF
INSERT INTO app_user(user_role, username, user_password) 
VALUES (2, 'staff1', '010f4928749bd109657b1b4ef213359ac420678c72932b0d5bc110076afc52f7');
SET @last_id = last_insert_id();
INSERT INTO staff(id, firstname, lastname) 
VALUES (@last_id, 'Scotty', 'Flowers');


-- ADDRESS
INSERT INTO address(country, city, street, post_code) VALUES ('US', 'Los Santos', 'Grove Street 1', '42-4525');
INSERT INTO address(country, city, street, post_code) VALUES ('US', 'San Fierro', 'West Street 54a', '59-2594');
INSERT INTO address(country, city, street, post_code) VALUES ('US', 'San Fierro', 'Castle Street 53', '52-3642');
INSERT INTO address(country, city, street, post_code) VALUES ('US', 'New York', 'Wall Street 122', '98-3525');


-- PATIENTS
INSERT INTO app_user(user_role, username, user_password) 
VALUES (0, 'patient1', 'd3d7b232f030f5c1c3cbdbcdaf876630549bbd7ede675a5fb0f8b88685b41de4');
SET @last_id = last_insert_id();
INSERT INTO patient(id, firstname, lastname, date_of_birth, pesel, gender, phone_number, address_id) 
VALUES (@last_id, 'Olga', 'Roberson', '1973-12-05', '84798512647', 'female', '795121678', 1);

INSERT INTO app_user(user_role, username, user_password) 
VALUES (0, 'patient2', '35dd16d588bd48e2d4410199fdae3b5ded6ea110823c5fb7614ba267506a12bc');
SET @last_id = last_insert_id();
INSERT INTO patient(id, firstname, lastname, date_of_birth, pesel, gender, phone_number, address_id) 
VALUES (@last_id, 'Carmine', 'Lara', '1995-04-23', '78498254124', 'female', '789458216', 2);

INSERT INTO app_user(user_role, username, user_password) 
VALUES (0, 'patient3', 'd6d9049621a533762c6ad00fe9585c42d8d7a34647b18e55cfcc8b003b0cb849');
SET @last_id = last_insert_id();
INSERT INTO patient(id, firstname, lastname, date_of_birth, pesel, gender, phone_number, address_id) 
VALUES (@last_id, 'Fernando', 'Lynn', '2001-05-30', '87198174684', 'male', '498165048', 3);

INSERT INTO app_user(user_role, username, user_password) 
VALUES (0, 'patient4', '350b81357c2321dfcf6d7dc202065d93b8cc129b7e976b690dd8604a2614a8ac');
SET @last_id = last_insert_id();
INSERT INTO patient(id, firstname, lastname, date_of_birth, pesel, gender, phone_number, address_id) 
VALUES (@last_id, 'Jody', 'Peck', '1952-02-27', '48798154687', 'male', '487226541', 4);

-- APPOINTMENTS
SET @rand_patient = (SELECT id FROM patient ORDER BY RAND() LIMIT 1);
SET @rand_doctor = (SELECT id FROM doctor ORDER BY RAND() LIMIT 1);
INSERT INTO appointment(appointment_datetime, summary, treatment_plan, patient_id, doctor_id)
VALUES ('2024-05-14 17:30', 'Summary for appointment 1', 'Treatment plan for appointment 1', @rand_patient, @rand_doctor);

SET @rand_patient = (SELECT id FROM patient ORDER BY RAND() LIMIT 1);
SET @rand_doctor = (SELECT id FROM doctor ORDER BY RAND() LIMIT 1);
INSERT INTO appointment(appointment_datetime, summary, treatment_plan, patient_id, doctor_id)
VALUES ('2024-06-14 17:30', 'Summary for appointment 2', 'Treatment plan for appointment 2', @rand_patient, @rand_doctor);

SET @rand_patient = (SELECT id FROM patient ORDER BY RAND() LIMIT 1);
SET @rand_doctor = (SELECT id FROM doctor ORDER BY RAND() LIMIT 1);
INSERT INTO appointment(appointment_datetime, summary, treatment_plan, patient_id, doctor_id)
VALUES ('2024-07-14 10:00', 'Summary for appointment 3', 'Treatment plan for appointment 3', @rand_patient, @rand_doctor);

SET @rand_patient = (SELECT id FROM patient ORDER BY RAND() LIMIT 1);
SET @rand_doctor = (SELECT id FROM doctor ORDER BY RAND() LIMIT 1);
INSERT INTO appointment(appointment_datetime, summary, treatment_plan, patient_id, doctor_id)
VALUES ('2024-08-14 12:30', 'Summary for appointment 4', 'Treatment plan for appointment 4', @rand_patient, @rand_doctor);

SET @rand_patient = (SELECT id FROM patient ORDER BY RAND() LIMIT 1);
SET @rand_doctor = (SELECT id FROM doctor ORDER BY RAND() LIMIT 1);
INSERT INTO appointment(appointment_datetime, patient_id, doctor_id)
VALUES ('2024-05-14 17:30', @rand_patient, @rand_doctor);


SELECT * FROM app_user;
SELECT * FROM patient;
SELECT * FROM address;
SELECT * FROM doctor;
SELECT * FROM appointment;
SELECT * FROM staff;

