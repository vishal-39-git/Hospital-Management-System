create database hospital;
use hospital;
create table patients(
id INT AUTO_INCREMENT PRIMARY KEY,
name varchar(50) NOT NULL,
age INT NOT NULL,
gender varchar(20) NOT NULL
);
create table doctors(
id INT AUTO_INCREMENT PRIMARY KEY,
name varchar(50) NOT NULL,
specialization VARCHAR(50) NOT NULL
);
create table appointments(
id INT auto_increment primary KEY,
patient_id INT NOT NULL,
doctor_id INT NOT NULL,
appointment_date DATE NOT NULL,
foreign key(patient_id) references patients(id),
foreign key(doctor_id) references doctors(id)
);
show tables;
INSERT INTO doctors(name,specialization) values("Ganesh Bhujbal","Physician");
INSERT INTO doctors(name,specialization) values("Deepak Nagawade","Neurosurgeon");
select * from doctors;


show databases;
use hospital;
describe patients;
show tables;
describe appointments;
select * from appointments;
use hospital;
select * from patients;