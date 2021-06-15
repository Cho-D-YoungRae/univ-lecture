
/* madang DB 자료 생성 */
/* 이후 실습은 MySQL Workbench에서 초기화면에서 +를 눌러 madang connection을 만들어 접속하여 사용한다. */

USE madang;

SET foreign_key_checks = 0;
drop table if exists Department;
drop table if exists Professor;
drop table if exists Student;
drop table if exists Lecture;f
drop table if exists Club;
drop table if exists Tuition;
drop table if exists Professor_has_student;
drop table if exists Lecture_has_student;
drop table if exists Professor_has_department;
drop table if exists Student_has_club;
SET foreign_key_checks = 1;

CREATE TABLE Department (
  departmentId INT ,
  name VARCHAR(20),
  phone VARCHAR(20),
  office VARCHAR(45),
  president_professorId INT,
  PRIMARY KEY (departmentId)
  );

CREATE TABLE Professor (
  professorId INT,
  name VARCHAR(20),
  address VARCHAR(45),
  phone VARCHAR(20),
  email VARCHAR(45),
  departmentId INT,
  PRIMARY KEY (`professorId`),
  FOREIGN KEY (`departmentId`) REFERENCES `Department` (`departmentId`)
);
 

CREATE TABLE Student (
  studentId INT,
  name VARCHAR(20),
  address VARCHAR(45),
  phone VARCHAR(20),
  email VARCHAR(45),
  departmentId_major INT,
  professorId INT,
  accunt VARCHAR(20) , 
  departmentId_minor INT NULL,
  PRIMARY KEY (studentId),
  foreign key(professorId) references Professor(professorId),
  foreign key(departmentId_major) references Department(departmentId),
  foreign key(departmentId_minor) references Department(departmentId)
  );
  
  
CREATE TABLE Lecture (
  lectureId INT,
  splitNum INT,
  professorId INT,
  name VARCHAR(20) ,
  day VARCHAR(10) ,
  period INT ,
  credit INT ,
  hour INT ,
  departmentId INT ,
  lectureroom VARCHAR(45) ,
  PRIMARY KEY (lectureId),
  FOREIGN KEY (professorId) REFERENCES Professor (professorId),
  FOREIGN KEY (departmentId) REFERENCES Department (departmentId)
  );
  
  
CREATE TABLE Club (
  clubId INT ,
  name VARCHAR(20) ,
  totalStudents INT ,
  president_studentId INT ,
  professorId INT,
  clubroom VARCHAR(45) ,
  PRIMARY KEY (clubId),
  FOREIGN KEY (professorId) REFERENCES Professor (professorId)
);
 

CREATE TABLE Tuition (
  tuitionID INT AUTO_INCREMENT,
  studentId INT,
  paymentYear int,
  paymentSemester INT,
  totalTuition INT,
  totalPayment INT,
  lastpaymentDate DATE,
  PRIMARY KEY (tuitionId),
  FOREIGN KEY (studentId)  REFERENCES Student (studentId)  
  );

CREATE TABLE Professor_has_student (
  professorId INT,
  studentId INT,
  grade INT NULL,
  semester INT NULL,
  PRIMARY KEY (studentId, professorId),
  FOREIGN KEY (professorId) REFERENCES Professor(professorId),
  FOREIGN KEY (studentId) REFERENCES Student(studentId)
    );
    

CREATE TABLE Lecture_has_student(
  lectureId INT ,
  studentId INT,
  professorId INT ,
  attendanceScore INT ,
  midtermScore INT,
  finalScore INT,
  otherScore INT,
  totalScore INT,
  grade CHAR(1),
  gpa double,
  year int(4) ,
  semester INT,
  PRIMARY KEY (studentId,lectureId, professorId),
  FOREIGN KEY (`lectureId`) REFERENCES Lecture (`lectureId`),
  FOREIGN KEY (`studentId`) REFERENCES Student (`studentId`)
  );

CREATE TABLE Professor_has_department (
  professorId INT ,
  departmentId INT ,
  PRIMARY KEY (professorId, departmentId),
  FOREIGN KEY (professorId) REFERENCES Professor (professorId),
  FOREIGN KEY (departmentId) REFERENCES Department (departmentId)
  );
  
 

CREATE TABLE Student_has_club (
  studentId INT,
  clubId INT,
  PRIMARY KEY (studentId, clubId),
  FOREIGN KEY (studentId) REFERENCES Student(studentId),
  FOREIGN KEY (clubId) REFERENCES Club (clubId)
    );
    
