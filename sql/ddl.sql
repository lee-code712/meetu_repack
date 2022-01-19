DROP SEQUENCE alert_seq;

CREATE SEQUENCE alert_seq
	INCREMENT BY 1
	START WITH 100000
	MAXVALUE 199999
	CACHE 20;

DROP SEQUENCE consult_seq;

CREATE SEQUENCE consult_seq
	INCREMENT BY 1
	START WITH 10000
	MAXVALUE 19999
	CACHE 20;

DROP SEQUENCE message_seq;

CREATE SEQUENCE message_seq
	INCREMENT BY 1
	START WITH 1000
	MAXVALUE 9999
	CACHE 20;

DROP SEQUENCE notice_seq;

CREATE SEQUENCE notice_seq
	INCREMENT BY 1
	START WITH 1
	MAXVALUE 99
	CACHE 20;

DROP TABLE CLASS CASCADE CONSTRAINTS PURGE;

DROP TABLE COURSE CASCADE CONSTRAINTS PURGE;

DROP TABLE CONSULTABLE_TIME CASCADE CONSTRAINTS PURGE;

DROP TABLE MESSAGE CASCADE CONSTRAINTS PURGE;

DROP TABLE CONSULT_RECORD CASCADE CONSTRAINTS PURGE;

DROP TABLE CONSULT_BACKUP CASCADE CONSTRAINTS PURGE;

DROP TABLE PROFESSOR CASCADE CONSTRAINTS PURGE;

DROP TABLE OFFICE CASCADE CONSTRAINTS PURGE;

DROP TABLE STUDENT CASCADE CONSTRAINTS PURGE;

DROP TABLE CONSULT CASCADE CONSTRAINTS PURGE;

DROP TABLE ALERT CASCADE CONSTRAINTS PURGE;

DROP TABLE ALERT_TYPE CASCADE CONSTRAINTS PURGE;

DROP TABLE SERVICE_USER CASCADE CONSTRAINTS PURGE;

DROP TABLE MEMBER CASCADE CONSTRAINTS PURGE;

DROP TABLE DEPARTMENT CASCADE CONSTRAINTS PURGE;

DROP TABLE COLLEGE CASCADE CONSTRAINTS PURGE;

CREATE TABLE COLLEGE
(
	college_no           VARCHAR2(8) NOT NULL ,
	college_name         VARCHAR2(40) NOT NULL ,
 PRIMARY KEY (college_no)
);

CREATE TABLE DEPARTMENT
(
	dept_no              VARCHAR2(8) NOT NULL ,
	dept_name            VARCHAR2(40) NOT NULL ,
	college_no           VARCHAR2(8) NOT NULL ,
 PRIMARY KEY (dept_no),
FOREIGN KEY (college_no) REFERENCES COLLEGE (college_no)
);

CREATE TABLE MEMBER
(
	member_no            VARCHAR2(10) NOT NULL ,
	role                 NUMBER(38,0) NOT NULL  CHECK (role >= 0 and role <= 2),
	email                VARCHAR2(30) NULL ,
	dept_no              VARCHAR2(8) NULL ,
	name                 VARCHAR2(20) NULL ,
 PRIMARY KEY (member_no),
FOREIGN KEY (dept_no) REFERENCES DEPARTMENT (dept_no)
);

CREATE TABLE SERVICE_USER
(
	user_id              VARCHAR2(18) NOT NULL ,
	password             VARCHAR2(20) NOT NULL ,
	phone                VARCHAR2(15) NULL ,
	member_no            VARCHAR2(10) NOT NULL ,
 PRIMARY KEY (user_id),
FOREIGN KEY (member_no) REFERENCES MEMBER (member_no)
);

CREATE TABLE ALERT_TYPE
(
	type_no              NUMBER(38,0) NOT NULL ,
	title                VARCHAR2(30) NOT NULL ,
 PRIMARY KEY (type_no)
);

CREATE TABLE ALERT
(
	alert_id             VARCHAR2(8) NOT NULL ,
	alert_date           DATE NOT NULL ,
	alert_msg            VARCHAR2(1000) NOT NULL ,
	is_read              NUMBER(38,0) DEFAULT  0  NULL ,
	user_id              VARCHAR2(18) NOT NULL ,
	type_no              NUMBER(38,0) NOT NULL ,
 PRIMARY KEY (alert_id),
FOREIGN KEY (user_id) REFERENCES SERVICE_USER (user_id),
FOREIGN KEY (type_no) REFERENCES ALERT_TYPE (type_no)
);

CREATE TABLE CONSULT
(
	consult_id           VARCHAR2(8) NOT NULL ,
	start_date           DATE NOT NULL ,
	end_date             DATE NULL ,
	reason               VARCHAR2(50) NOT NULL ,
	type                 NUMBER(38,0) NOT NULL ,
	state                NUMBER(38,0) NULL ,
	prof_id              VARCHAR2(18) NOT NULL ,
	stu_id               VARCHAR2(18) NOT NULL ,
	cancel_msg           VARCHAR2(400) NULL ,
 PRIMARY KEY (consult_id),
FOREIGN KEY (prof_id) REFERENCES SERVICE_USER (user_id),
FOREIGN KEY (stu_id) REFERENCES SERVICE_USER (user_id)
);

CREATE TABLE STUDENT
(
	year                 NUMBER(38,0) NULL  CHECK (year >= 1 and year <= 4),
	member_no            VARCHAR2(10) NOT NULL ,
 PRIMARY KEY (member_no),
FOREIGN KEY (member_no) REFERENCES MEMBER (member_no)
);

CREATE TABLE OFFICE
(
	office_no            VARCHAR(20) NOT NULL ,
	longitude            NUMBER NULL ,
	latitude             NUMBER NULL ,
 PRIMARY KEY (office_no)
);

CREATE TABLE PROFESSOR
(
	member_no            VARCHAR2(10) NOT NULL ,
	major                VARCHAR2(100) NULL ,
	office_no            VARCHAR(20) NOT NULL ,
 PRIMARY KEY (member_no),
FOREIGN KEY (member_no) REFERENCES MEMBER (member_no),
FOREIGN KEY (office_no) REFERENCES OFFICE (office_no)
);

CREATE TABLE CONSULT_BACKUP
(
	backup_id            CHAR(18) NOT NULL ,
	start_date           CHAR(18) NULL ,
	end_date             CHAR(18) NULL ,
	reason               CHAR(18) NULL ,
	type                 CHAR(18) NULL ,
	content              CHAR(18) NULL ,
	consult_id           VARCHAR2(8) NULL ,
	stu_no               VARCHAR2(10) NOT NULL ,
	prof_no              VARCHAR2(10) NOT NULL ,
 PRIMARY KEY (backup_id),
FOREIGN KEY (consult_id) REFERENCES CONSULT (consult_id) ON DELETE SET NULL,
FOREIGN KEY (stu_no) REFERENCES STUDENT (member_no),
FOREIGN KEY (prof_no) REFERENCES PROFESSOR (member_no)
);

CREATE TABLE CONSULT_RECORD
(
	content              VARCHAR2(4000) NULL ,
	consult_id           VARCHAR2(8) NOT NULL ,
 PRIMARY KEY (consult_id),
FOREIGN KEY (consult_id) REFERENCES CONSULT (consult_id)
);

CREATE TABLE MESSAGE
(
	msg_id               NUMBER(4,0) NOT NULL ,
	sent_date            DATE NOT NULL ,
	content              VARCHAR2(1000) NOT NULL ,
	is_read              NUMBER(38,0) DEFAULT  0  NULL  CHECK (is_read = 0 or is_read = 1),
	send_id              VARCHAR2(18) NOT NULL ,
	recv_id              VARCHAR2(18) NOT NULL ,
 PRIMARY KEY (msg_id),
FOREIGN KEY (send_id) REFERENCES SERVICE_USER (user_id),
FOREIGN KEY (recv_id) REFERENCES SERVICE_USER (user_id)
);

CREATE TABLE CONSULTABLE_TIME
(
	able_date            NUMBER(38,0) NOT NULL  CHECK (able_date >= 0 and able_date <= 6),
	able_time            VARCHAR2(20) NOT NULL ,
	prof_id              VARCHAR2(18) NOT NULL ,
 PRIMARY KEY (able_date,able_time,prof_id),
FOREIGN KEY (prof_id) REFERENCES SERVICE_USER (user_id)
);

CREATE TABLE COURSE
(
	course_no            VARCHAR2(20) NOT NULL ,
	title                VARCHAR2(100) NOT NULL ,
	dept_no              VARCHAR2(8) NOT NULL ,
 PRIMARY KEY (course_no),
FOREIGN KEY (dept_no) REFERENCES DEPARTMENT (dept_no)
);

CREATE TABLE CLASS
(
	course_no            VARCHAR2(20) NOT NULL ,
	prof_no              VARCHAR2(10) NOT NULL ,
 PRIMARY KEY (course_no,prof_no),
FOREIGN KEY (course_no) REFERENCES COURSE (course_no),
FOREIGN KEY (prof_no) REFERENCES PROFESSOR (member_no)
);
