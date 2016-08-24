--
-- Table structure for table `inventory_machine`
--

DROP TABLE IF EXISTS `inventory_machine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inventory_machine` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `label` varchar(50) NOT NULL,
  `type` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `prodution_product`
--

DROP TABLE IF EXISTS `production_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `production_product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `entrydate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `quantity` int(11) NOT NULL,
  `packageno` int NOT NULL,
  `lumbertype` int(11) NOT NULL,
  `userid` int(11) NOT NULL,
  `machineid` int(11) NOT NULL,
  `productid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_prodution_product_lumbertype` (`lumbertype`),
  KEY `fk_production_product_user` (`userid`),
  KEY `fk_production_product_machine` (`machineid`),
  KEY `fk_production_product_product` (`productid`),
  CONSTRAINT `productionproduct_ltypefk_1` FOREIGN KEY (`lumbertype`) REFERENCES `lumbertype` (`id`),
  CONSTRAINT `productionproduct_userfk_1` FOREIGN KEY (`userid`) REFERENCES `user` (`id`),
  CONSTRAINT `productionproduct_machinefk_1` FOREIGN KEY (`machineid`) REFERENCES `inventory_machine` (`id`),
  CONSTRAINT `productionproduct_productfk_1` FOREIGN KEY (`productid`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS supplier;

CREATE TABLE supplier (
  id bigint NOT NULL AUTO_INCREMENT,
  entrydate datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  registerno varchar(255) NOT NULL,
  title varchar(100) NOT NULL,
  address text,
  usestatus bit NOT NULL DEFAULT 0,
  areacode varchar(30),
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS LumberLogTransportCertificate;

CREATE TABLE LumberLogTransportCertificate (
  Id bigint NOT NULL AUTO_INCREMENT,
  EntryDate datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  SerialCode varchar(255) NOT NULL,
  SerialNo varchar(255) NOT NULL,
  SupplierId bigint NOT NULL,
  Code varchar(20) NOT NULL,
  CodeCreationTime datetime NOT NULL,
  Document varchar(255),
  LoadPlace varchar(255) NOT NULL,
  TransportLeaveDate datetime NOT NULL,
  UnloadPlace varchar(255) NOT NULL,
  TransportArrivalDate datetime NOT NULL,
  DocCreator varchar(255),
  DocCreatorName varchar(255),
  TransportName varchar(255),
  TransportPlate varchar(15) NOT NULL,
  DriverName varchar(255) NOT NULL,
  DriverId varchar(15),
  PRIMARY KEY (`Id`),
  CONSTRAINT `transportcertificate_supplier` FOREIGN KEY (`SupplierId`) REFERENCES `supplier` (`id`)
) DEFAULT CHARSET=utf8;

alter table lumberlog add Status int default 0;
alter table lumberlog SupplierId bigint NOT NULL;
insert into supplier(registerno,title,address,usestatus) values('test','test','test', 1);
update lumberlog set SupplierId=2;
alter table lumberlog add CONSTRAINT `lumberlog_supplier_fk` FOREIGN KEY (`SupplierId`) REFERENCES `supplier` (`id`);
alter table lumberlog add TransportCertificateId bigint NULL;
#alter table lumberlog add CONSTRAINT `lumberlog_transportcertificate_fk` FOREIGN KEY (`TransportCertificateId`) REFERENCES `LumberLogTransportCertificate` (`Id`);
alter table lumberlog add Margin int default 0;
alter table lumberlog add MarginVolume decimal(10,2);
alter table lumberlog add RealMarginVolume decimal(10,2);


CREATE TABLE LumberLogTransportEntry (
  Id bigint NOT NULL AUTO_INCREMENT,
  EntryDate datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UserId int NOT NULL,
  Status int default 0,
  PRIMARY KEY (`Id`),
  CONSTRAINT `transportentry_user` FOREIGN KEY (`UserId`) REFERENCES `user` (`ID`)
) DEFAULT CHARSET=utf8;

alter table lumberlog add TransportEntryId bigint NOT NULL;
#alter table lumberlog add CONSTRAINT `lumberlog_transportentry_fk` FOREIGN KEY (`TransportEntryId`) REFERENCES `LumberLogTransportEntry` (`Id`);