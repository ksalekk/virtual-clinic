-- DROP DATABASE virtual_clinic;

CREATE DATABASE IF NOT EXISTS virtual_clinic;
USE virtual_clinic;

CREATE TABLE app_user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_role TINYINT CHECK (user_role between 0 and 2),
    username VARCHAR(30) UNIQUE,
    user_password VARCHAR(100),
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS address (
  id INT AUTO_INCREMENT PRIMARY KEY,
  country VARCHAR(30),
  city VARCHAR(30),
  street VARCHAR(30),
  post_code VARCHAR(30)
);

CREATE TABLE patient (
    id INT AUTO_INCREMENT PRIMARY KEY,
    firstname VARCHAR(50),
    lastname VARCHAR(50),
    date_of_birth DATE,
    pesel CHAR(11),
	gender VARCHAR(10),
    phone_number VARCHAR(15),
    address_id INT,
    FOREIGN KEY (address_id) REFERENCES address(id)
);

CREATE TABLE doctor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    firstname VARCHAR(50),
    lastname VARCHAR(50),
    license_number VARCHAR(15),
    phone_number VARCHAR(15),
    speciality VARCHAR(30)
);

CREATE TABLE appointment (
    id INT AUTO_INCREMENT PRIMARY KEY,
    appointment_datetime DATETIME,
    appointment_status TINYINT CHECK (appointment_status between 0 and 2) DEFAULT 0,
    summary TEXT,
    treatment_plan TEXT,
    patient_id INT,
    doctor_id INT,
    FOREIGN KEY (patient_id) REFERENCES patient(id),
    FOREIGN KEY (doctor_id) REFERENCES doctor(id)
);

CREATE TABLE staff (
    id INT AUTO_INCREMENT PRIMARY KEY,
    firstname VARCHAR(50),
    lastname VARCHAR(50)
);