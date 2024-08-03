use patient_data;

select * from t_patients;
select * from t_dates;

select * from patients;
select * from dates;


DELETE FROM patients WHERE patient_ID > -1;
truncate table patient_dates;
SELECT * FROM t_patients;
INSERT INTO patients VALUES

(1, "SEUN", "ONI", "2006-11-09", "A+", "S", "M"),
(2, "MARVELOUS", "ONI", "2009-10-20", "A+", "S", "F");

INSERT INTO t_patients SELECT * FROM patients;

ALTER TABLE t_patients
ADD sex char;

UPDATE patient_info

SET 

sex = "F"

WHERE

patient_ID = 1 OR
patient_ID = 3 OR
patient_ID = 4;

UPDATE patient_info

SET

sex = "M"

WHERE 

patient_ID = 0 OR
patient_ID = 2;

DELETE FROM patient_info

WHERE patient_ID = 5;

UPDATE dates
SET patient_ID = 0 
WHERE patient_ID = 1;

UPDATE t_patients
SET patient_ID = 1 
WHERE patient_ID = 3;

 

SET 

patient_ID = 5;

SELECT * FROM patients;

patient_ID = 6;


create table t_patient_dates (

date_ID int,
date_visited date,
patient_ID int,
PRIMARY KEY (date_ID),
FOREIGN KEY (patient_ID) REFERENCES t_patients(patient_ID)

)
;
ALTER TABLE t_patient_dates	DROP FOREIGN KEY patient_ID;
ALTER TABLE t_patient_dates DROP COLUMN patient_ID;

DROP TABLE t_patient_dates;
TRUNCATE TABLE dates;

INSERT INTO patient_dates VALUES
(1, "2007-08-08", 1),
(2,"2004-12-08",1);

SELECT * FROM  patient_dates;

SELECT COUNT(*) AS NUMBER_OF_COLUMNS  FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = "dates";

SELECT COUNT(*) AS NUMBER_OF_COLUMNS  FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = "patient_dates";

SELECT * FROM t_patients;

DELETE FROM t_patients WHERE patient_ID > -1