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