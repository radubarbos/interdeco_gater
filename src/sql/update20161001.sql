alter table supplier add RegisterId varchar(255) NOT NULL;
alter table supplier add ContactLastName varchar(255);
alter table supplier add ContactFirstName varchar(255);
alter table supplier add ContactPhone varchar(255);
alter table supplier add ContactEmail varchar(255);

INSERT INTO `user` VALUES (3,'stoc','stoc1234','Operator stock',1);