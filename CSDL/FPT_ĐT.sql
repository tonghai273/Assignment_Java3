USE Master					
	GO					
	IF  EXISTS (SELECT * FROM SYSdatabases  WHERE Name = N'FPL_�T')					
	Drop Database FPL_�T				
	Go	

--------------------
create database FPL_�T
go
use FPL_�T
go

	IF  EXISTS (SELECT * FROM SYSOBJECTS  WHERE Name = N'STUDENTS')					
	Drop table STUDENTS					
	Go					
	Create table STUDENTS(
		MASV NVARCHAR(50) NOT NULL,
		HOTEN NVARCHAR(50) NULL,
		EMAIL NVARCHAR(50) NULL,
		SODT NVARCHAR(15) NULL,
		GIOITINH BIT NULL,
		DIACHI NVARCHAR(50) NULL,
		HINH NVARCHAR(MAX) NULL,
		CONSTRAINT PK_STUDENTS PRIMARY KEY (MASV)	
	)

    IF  EXISTS (SELECT * FROM SYSOBJECTS  WHERE Name = N'GRADE')					
	Drop table GRADE					
	Go					
	Create table GRADE(
		ID INT IDENTITY NOT NULL,
		MASV NVARCHAR(50) NOT NULL,
		TIENGANH FLOAT NULL,
		TINHOC FLOAT NULL,
		GDTC FLOAT NULL,
		CONSTRAINT PK_GRADE PRIMARY KEY (ID),
		CONSTRAINT FK_GRADE_STUDENTS FOREIGN KEY(MASV) REFERENCES STUDENTS
	)

    IF  EXISTS (SELECT * FROM SYSOBJECTS  WHERE Name = N'USERS')					
	Drop table USERS					
	Go					
	Create table USERS(
		USERNAME NVARCHAR(50) NOT NULL,
		PASSWORDD NVARCHAR(50) NULL,
		ROLE NVARCHAR(50) NULL,
		CONSTRAINT PK_USERS PRIMARY KEY(USERNAME)
	)