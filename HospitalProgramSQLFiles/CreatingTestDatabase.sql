create database patient_data;
use patient_data;

create table t_patients(

patient_ID int primary key,
first_name varchar(25),
last_name varchar(25),
date_of_birth date,
blood_type varchar(3),
marital_status char(1)

);

insert into patients values
(1, "VAHE", "OHIHOIN", "2007-08-08", "A+", "S", "M");
select * from patient_info;