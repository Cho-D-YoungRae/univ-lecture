-- drop exists TABLE
DROP TABLE IF EXISTS Charts;
DROP TABLE IF EXISTS Treatments;
DROP TABLE IF EXISTS Patients;
DROP TABLE IF EXISTS Nurses;
DROP TABLE IF EXISTS Doctors;

-- Doctors TABLE
CREATE TABLE Doctors (
	doc_id			INTEGER NOT NULL,
	major_treat		VARCHAR(25) NOT NULL, 
 	doc_name	 	VARCHAR(20) NOT NULL,
	doc_gen			CHAR(1) NOT NULL,
    doc_phone		VARCHAR(15) NULL,
 	doc_email	 	VARCHAR(50) UNIQUE,
 	doc_position	VARCHAR(20) NOT NULL
);
 
ALTER TABLE Doctors
	ADD CONSTRAINT doc_id_pk PRIMARY KEY (doc_id);

-- Nurses TABLE
CREATE TABLE Nurses(
	nur_id			INTEGER NOT NULL,
    major_job 		VARCHAR(25) NOT NULL,
 	nur_name		VARCHAR(20) NOT NULL,
	nur_gen 		CHAR(1) NOT NULL,
    nur_phone		VARCHAR(15) NULL,
	nur_email		VARCHAR(50) UNIQUE,
    nur_position	VARCHAR(20) NOT NULL
 );
 
 ALTER TABLE Nurses
	ADD CONSTRAINT nur_id_pk PRIMARY KEY (nur_id);

-- Patients TABLE 
CREATE TABLE Patients (
	pat_id		INTEGER NOT NULL,
	nur_id		INTEGER NOT NULL,
	doc_id		INTEGER NOT NULL,
	pat_name	VARCHAR(20) NOT NULL,
	pat_gen		CHAR(1) NOT NULL,
	pat_jumin	VARCHAR(14) NOT NULL,
	pat_addr	VARCHAR(100) NOT NULL,
	pat_phone	VARCHAR(15) NULL,
	pat_email	VARCHAR(50) UNIQUE,
	pat_job		VARCHAR(20) NOT NULL
);
 
ALTER TABLE Patients
	ADD CONSTRAINT pat_id_pk PRIMARY KEY (pat_id);
    
ALTER TABLE Patients 
	ADD (CONSTRAINT R_2 FOREIGN KEY (doc_id) REFERENCES Doctors (doc_id));

ALTER TABLE Patients
	ADD (CONSTRAINT R_3 FOREIGN KEY (nur_id) REFERENCES Nurses (nur_id));

-- Treatments TABLE
CREATE TABLE Treatments(
	treat_id		INTEGER NOT NULL,
    pat_id			INTEGER NOT NULL,
    doc_id 			INTEGER NOT NULL,
    treat_contents 	VARCHAR(1000) NOT NULL,
 	treat_date		DATE NOT NULL
);

ALTER TABLE Treatments
	ADD CONSTRAINT treat_pat_doc_id_pk PRIMARY KEY (treat_id, pat_id, doc_id);

ALTER TABLE Treatments 
	ADD (CONSTRAINT R_5 FOREIGN KEY (pat_id) 
						REFERENCES Patients (pat_id));

ALTER TABLE Treatments
 	ADD (CONSTRAINT R_6 FOREIGN KEY (doc_id) REFERENCES Doctors (doc_id));

-- Charts TABLE
CREATE TABLE Charts (
	chart_id		VARCHAR(20) NOT NULL,
    treat_id		INTEGER NOT NULL,
	doc_id			INTEGER NOT NULL,
    pat_id			INTEGER NOT NULL,
    nur_id			INTEGER NOT NULL,
    chart_contents	VARCHAR(1000) NOT NULL
);

ALTER TABLE Charts
	ADD CONSTRAINT chart_treat_doc_pat_id_pk 
		PRIMARY KEY (chart_id, treat_id, doc_id, pat_id);
 
ALTER TABLE Charts
	ADD (CONSTRAINT	R_4 FOREIGN KEY (nur_id) REFERENCES Nurses (nur_id));
    
ALTER TABLE Charts
	ADD (CONSTRAINT R_7 FOREIGN KEY (treat_id, pat_id, doc_id) 
			REFERENCES Treatments (treat_id, pat_id, doc_id));
