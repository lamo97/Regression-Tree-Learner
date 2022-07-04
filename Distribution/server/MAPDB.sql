DROP USER IF EXISTS 'MapUser'@'localhost';
DROP DATABASE IF EXISTS MapDB;

CREATE DATABASE MapDB;
CREATE USER 'MapUser'@'localhost'IDENTIFIED BY 'map';
GRANT SELECT ON MapDB.* TO 'MapUser'@'localhost';

CREATE TABLE MapDB.provaC(
X varchar(10),
Y float(5,2),
C float(5,2)
);

insert into MapDB.provaC values('A',2,1);
insert into MapDB.provaC values('A',2,1);
insert into MapDB.provaC values('A',1,1);
insert into MapDB.provaC values('A',2,1);
insert into MapDB.provaC values('A',5,1.5);
insert into MapDB.provaC values('A',5,1.5);
insert into MapDB.provaC values('A',6,1.5);
insert into MapDB.provaC values('B',6,10);
insert into MapDB.provaC values('A',6,1.5);
insert into MapDB.provaC values('A',6,1.5);
insert into MapDB.provaC values('B',10,10);
insert into MapDB.provaC values('B',5,10);
insert into MapDB.provaC values('B',12,10);
insert into MapDB.provaC values('B',14,10);
insert into MapDB.provaC values('A',1,1);
commit;

USE MapDB;

CREATE TABLE emptytest(
X varchar(10),
Y float(5,2),
C float(5,2)
);

CREATE TABLE wrongTable(
X varchar(10)
);