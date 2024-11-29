create database patient_data;
use patient_data;

create table t_patients(

patient_ID int primary key,
first_name varchar(25),
last_name varchar(25),
date_of_birth date,
blood_type varchar(3),
marital_status char(1),
sex char(1)

);

create table patients(

patient_ID int primary key,
first_name varchar(25),
last_name varchar(25),
date_of_birth date,
blood_type varchar(3),
marital_status char(1),
sex char(1)

);

create table t_dates (

date_ID int,
date_visited date,
patient_ID int,
PRIMARY KEY (date_ID),
FOREIGN KEY (patient_ID) REFERENCES t_patients(patient_ID)

);

create table dates (

date_ID int,
date_visited date,
patient_ID int,
PRIMARY KEY (date_ID),
FOREIGN KEY (patient_ID) REFERENCES patients(patient_ID)

);

USE patient_data;
SELECT * FROM patients;