-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: localhost    Database: gater
-- ------------------------------------------------------
-- Server version	5.6.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `blades`
--

DROP TABLE IF EXISTS `blades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `blades` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(30) DEFAULT NULL,
  `Thick` decimal(10,2) DEFAULT NULL,
  `Metric` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_blade_metric` (`Metric`),
  CONSTRAINT `blades_ibfk_1` FOREIGN KEY (`Metric`) REFERENCES `metric` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blades`
--

LOCK TABLES `blades` WRITE;
/*!40000 ALTER TABLE `blades` DISABLE KEYS */;
INSERT INTO `blades` VALUES (1,'Lama gater',2.70,1),(2,'Multilama',3.50,1);
/*!40000 ALTER TABLE `blades` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cutplan`
--

DROP TABLE IF EXISTS `cutplan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cutplan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `description` varchar(250) NOT NULL,
  `entrydate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` int(11) DEFAULT '1',
  `complete` double DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cutplan`
--

LOCK TABLES `cutplan` WRITE;
/*!40000 ALTER TABLE `cutplan` DISABLE KEYS */;
INSERT INTO `cutplan` VALUES (1,'plan1','','2013-11-28 06:00:00',1,0.0023139996981739524),(3,'tttt','','2013-12-11 06:00:00',1,0),(4,'test','','2014-01-19 06:00:00',1,0),(5,'aaaaa','','2014-01-23 06:00:00',1,0);
/*!40000 ALTER TABLE `cutplan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cutplanlumberlogdiagram`
--

DROP TABLE IF EXISTS `cutplanlumberlogdiagram`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cutplanlumberlogdiagram` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PlanId` int(11) NOT NULL,
  `LumberLogIDPlate` varchar(100) DEFAULT NULL,
  `CutDiagramResult` text NOT NULL,
  `CutDiagram` blob NOT NULL,
  `Percentage` double DEFAULT '0',
  PRIMARY KEY (`ID`),
  KEY `fk_CutPlanLumberLogDiagram_planid` (`PlanId`),
  CONSTRAINT `cutplanlumberlogdiagram_ibfk_1` FOREIGN KEY (`PlanId`) REFERENCES `cutplan` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cutplanlumberlogdiagram`
--

LOCK TABLES `cutplanlumberlogdiagram` WRITE;
/*!40000 ALTER TABLE `cutplanlumberlogdiagram` DISABLE KEYS */;
INSERT INTO `cutplanlumberlogdiagram` VALUES (1,1,'1-2','','¨Ì\0sr\0/ro.barbos.gater.cutprocessor.diagram.CutDiagram~‘Ré\r’\0L\0cutInfot\05Lro/barbos/gater/cutprocessor/diagram/CutDiagramInfo;L\0debugSquaret\0Ljava/lang/Double;L\0gaterCutFlowt\0Ljava/util/List;L\0multiBladeSlidesq\0~\0L\0stepst\00Lro/barbos/gater/cutprocessor/LumberLogCutSteps;xpsr\03ro.barbos.gater.cutprocessor.diagram.CutDiagramInfo‰œ“Ÿ]\0D\0cutLayoutEfficencyD\0lumberLogVolumeD\0smallEndAreaD\0usedCutAreaL\0colorst\0Ljava/util/Map;L\0	cutPiecesq\0~\0xp@QöµamA«˝ÍNΩp§A5$˜€ƒ4A\0Wò\0\0\0\0sr\0java.util.HashMap⁄¡√`—\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0105x17x3.1msr\0java.awt.Color•Éè3u\0F\0falphaI\0valueL\0cst\0Ljava/awt/color/ColorSpace;[\0	frgbvaluet\0[F[\0fvalueq\0~\0xp\0\0\0\0ˇˇ\0\0pppxsq\0~\0	?@\0\0\0\0\0w\0\0\0\0\0\0q\0~\0sr\0java.lang.Integer‚†§˜Åá8\0I\0valuexr\0java.lang.NumberÜ¨ïî‡ã\0\0xp\0\0\0Kxsr\0java.lang.DoubleÄ≥¬J)k˚\0D\0valuexq\0~\0@uæX6~Cwsr\0java.util.ArrayListxÅ“ô«aù\0I\0sizexp\0\0\0w\0\0\0sr\0/ro.barbos.gater.cutprocessor.diagram.GaterSlideM?‹)ƒœ|\0D\0heightD\0\npieceWidthI\0piecesD\0\nrightLimitD\0xD\0yL\0colort\0Ljava/awt/Color;xp@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0Ac–\0\0\0\0¿J@\0\0\0\0\0¿n\0\0\0\0\0q\0~\0sq\0~\0@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0Ac–\0\0\0\0¿J@\0\0\0\0\0¿kπôôôôúq\0~\0sq\0~\0@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0Ac–\0\0\0\0¿Z∞\0\0\0\0\0¿is33336q\0~\0sq\0~\0@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0Ac–\0\0\0\0¿d \0\0\0\0\0¿g,ÃÃÃÃ–q\0~\0sr\00ro.barbos.gater.cutprocessor.diagram.GaterRotate†â\\\'öR8\0\0xpsq\0~\0@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0@dÊffffj¿J@\0\0\0\0\0¿n\0\0\0\0\0q\0~\0sq\0~\0@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0@dÊffffj¿J@\0\0\0\0\0¿kπôôôôúq\0~\0sq\0~\0@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0@dÊffffj¿Z∞\0\0\0\0\0¿is33336q\0~\0sq\0~\0@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0@dÊffffj¿d \0\0\0\0\0¿g,ÃÃÃÃ–q\0~\0sq\0~\0@Z@\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0@dÊffffj\0\0\0\0\0\0\0\0¿dÊffffjpsq\0~\0@Z@\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0@dÊffffj\0\0\0\0\0\0\0\0¿NÄ\0\0\0\0psq\0~\0@Z@\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0@dÊffffj\0\0\0\0\0\0\0\0@Fôôôôôåpsq\0~\0@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0@dÊffffj¿cYôôôôñ@bÏÃÃÃÃ…q\0~\0sq\0~\0@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0@dÊffffj¿Z∞\0\0\0\0\0@e33333/q\0~\0sq\0~\0@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0@dÊffffj¿Z∞\0\0\0\0\0@gyôôôôïq\0~\0sq\0~\0@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0@dÊffffj¿J@\0\0\0\0\0@iøˇˇˇˇ˚q\0~\0xsq\0~\0\0\0\0w\0\0\0sr\07ro.barbos.gater.cutprocessor.diagram.MultibladeCutSlide§Y§q¶∆næ\0D\0heightD\0\npieceWidthI\0piecesD\0widthD\0xD\0yL\0colorq\0~\0xp@Z@\0\0\0\0\0@1\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0¿f9ôôôôñ¿dÊffffjq\0~\0sq\0~\0,@Z@\0\0\0\0\0@1\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0¿kYôôôôñ¿NÄ\0\0\0\0q\0~\0sq\0~\0,@Z@\0\0\0\0\0@1\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0¿f9ôôôôñ@Fôôôôôåq\0~\0xsr\0.ro.barbos.gater.cutprocessor.LumberLogCutSteps¸‡‹a:46ª\0L\0bottomt\04Lro/barbos/gater/cutprocessor/LumberLogSegmentSteps;L\0leftq\0~\01L\0\nmultiBladeq\0~\01L\0rightq\0~\01L\0topq\0~\01xpsr\02ro.barbos.gater.cutprocessor.LumberLogSegmentStepsπ±{÷\"0˘\0L\0cutsq\0~\0xpsq\0~\0\0\0\0w\0\0\0sq\0~\0@9ÃÃÃÃÃısq\0~\0@F\0\0\0\0\0sq\0~\0@Oôôôô™sq\0~\0@Tôôôô°sq\0~\0@X¶ffffnxpsq\0~\03sq\0~\0\0\0\0w\0\0\0sq\0~\0@gyôôôôñsq\0~\0@r_ˇˇˇˇ˛sq\0~\0@y33332xsq\0~\03sq\0~\0\0\0\0w\0\0\0sq\0~\0@!ôôôôôÄsq\0~\0@:ˇˇˇˇˇsq\0~\0@Fôôôôôêsq\0~\0@O≥3333(sq\0~\0@Tfffff`xsq\0~\03sq\0~\0\0\0\0w\0\0\0sq\0~\0@!ôôôôôÄsq\0~\0@:ˇˇˇˇˇsq\0~\0@Fôôôôôêsq\0~\0@O≥3333(sq\0~\0@Tfffff`x',0.0037728255948488356),(2,1,'1-1','','¨Ì\0sr\0/ro.barbos.gater.cutprocessor.diagram.CutDiagram~‘Ré\r’\0L\0cutInfot\05Lro/barbos/gater/cutprocessor/diagram/CutDiagramInfo;L\0debugSquaret\0Ljava/lang/Double;L\0gaterCutFlowt\0Ljava/util/List;L\0multiBladeSlidesq\0~\0L\0stepst\00Lro/barbos/gater/cutprocessor/LumberLogCutSteps;xpsr\03ro.barbos.gater.cutprocessor.diagram.CutDiagramInfo‰œ“Ÿ]\0D\0cutLayoutEfficencyD\0lumberLogVolumeD\0smallEndAreaD\0usedCutAreaL\0colorst\0Ljava/util/Map;L\0	cutPiecesq\0~\0xp@QA´8ï≥A¿xs%—ÎÖ@˝vˆ∆*m]@Ù‡\0\0\0\0sr\0java.util.HashMap⁄¡√`—\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0105x17x3.1msr\0java.awt.Color•Éè3u\0F\0falphaI\0valueL\0cst\0Ljava/awt/color/ColorSpace;[\0	frgbvaluet\0[F[\0fvalueq\0~\0xp\0\0\0\0ˇˇ\0\0pppxsq\0~\0	?@\0\0\0\0\0w\0\0\0\0\0\0q\0~\0sr\0java.lang.Integer‚†§˜Åá8\0I\0valuexr\0java.lang.NumberÜ¨ïî‡ã\0\0xp\0\0\0.xsr\0java.lang.DoubleÄ≥¬J)k˚\0D\0valuexq\0~\0@qR˘Fyi…sr\0java.util.ArrayListxÅ“ô«aù\0I\0sizexp\0\0\0w\0\0\0sr\0/ro.barbos.gater.cutprocessor.diagram.GaterSlideM?‹)ƒœ|\0D\0heightD\0\npieceWidthI\0piecesD\0\nrightLimitD\0xD\0yL\0colort\0Ljava/awt/Color;xp@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0Ac–\0\0\0\0¿J@\0\0\0\0\0¿gs33336q\0~\0sq\0~\0@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0Ac–\0\0\0\0¿J@\0\0\0\0\0¿e,ÃÃÃÃ–q\0~\0sq\0~\0@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0Ac–\0\0\0\0¿Z∞\0\0\0\0\0¿bÊffffjq\0~\0sq\0~\0@Z@\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0Ac–\0\0\0\0\0\0\0\0\0\0\0\0¿`†\0\0\0\0psq\0~\0@Z@\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0Ac–\0\0\0\0\0\0\0\0\0\0\0\0¿:ÃÃÃÃÃÌpsq\0~\0@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0Ac–\0\0\0\0¿d \0\0\0\0\0@SŸôôôôíq\0~\0sq\0~\0@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0Ac–\0\0\0\0¿Z∞\0\0\0\0\0@Xfffff_q\0~\0sq\0~\0@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0Ac–\0\0\0\0¿Z∞\0\0\0\0\0@\\Û3333,q\0~\0sq\0~\0@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0Ac–\0\0\0\0¿Z∞\0\0\0\0\0@`øˇˇˇˇ¸q\0~\0sq\0~\0@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0Ac–\0\0\0\0¿J@\0\0\0\0\0@cffffbq\0~\0sq\0~\0@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0Ac–\0\0\0\0¿J@\0\0\0\0\0@eLÃÃÃÃ»q\0~\0xsq\0~\0\0\0\0w\0\0\0sr\07ro.barbos.gater.cutprocessor.diagram.MultibladeCutSlide§Y§q¶∆næ\0D\0heightD\0\npieceWidthI\0piecesD\0widthD\0xD\0yL\0colorq\0~\0xp@Z@\0\0\0\0\0@1\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0¿a\0\0\0\0\0¿`†\0\0\0\0q\0~\0sq\0~\0&@Z@\0\0\0\0\0@1\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0¿fp\0\0\0\0\0¿:ÃÃÃÃÃÌq\0~\0xsr\0.ro.barbos.gater.cutprocessor.LumberLogCutSteps¸‡‹a:46ª\0L\0bottomt\04Lro/barbos/gater/cutprocessor/LumberLogSegmentSteps;L\0leftq\0~\0*L\0\nmultiBladeq\0~\0*L\0rightq\0~\0*L\0topq\0~\0*xpsr\02ro.barbos.gater.cutprocessor.LumberLogSegmentStepsπ±{÷\"0˘\0L\0cutsq\0~\0xpsq\0~\0\0\0\0w\0\0\0sq\0~\0@&ÃÃÃÃÕ\Zsq\0~\0@=ôôôôôΩsq\0~\0@GÊffffvsq\0~\0@PÄ\0\0\0\0sq\0~\0@UÃÃÃÃ‘sq\0~\0@Yôôôôô°sq\0~\0@^&ffffnxpsq\0~\0,sq\0~\0\0\0\0w\0\0\0sq\0~\0@eˇˇˇˇ¸sq\0~\0@qc33331xpsq\0~\0,sq\0~\0\0\0\0w\0\0\0sq\0~\0@&fffff:sq\0~\0@=fffffPsq\0~\0@GÃÃÃÃÃ¿sq\0~\0@Ps3333,x',0.0023139996981739524),(7,3,'1-5','','¨Ì\0sr\0/ro.barbos.gater.cutprocessor.diagram.CutDiagram~‘Ré\r’\0L\0cutInfot\05Lro/barbos/gater/cutprocessor/diagram/CutDiagramInfo;L\0debugSquaret\0Ljava/lang/Double;L\0gaterCutFlowt\0Ljava/util/List;L\0multiBladeSlidesq\0~\0L\0stepst\00Lro/barbos/gater/cutprocessor/LumberLogCutSteps;xpsr\03ro.barbos.gater.cutprocessor.diagram.CutDiagramInfo‰œ“Ÿ]\0D\0cutLayoutEfficencyD\0lumberLogVolumeD\0smallEndAreaD\0usedCutAreaL\0colorst\0Ljava/util/Map;L\0	cutPiecesq\0~\0xp@R‚ËEº∆A«˝ÍNΩp§A5$˜€ƒ4A\0«(\0\0\0\0sr\0java.util.HashMap⁄¡√`—\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0105x17x3.1msr\0java.awt.Color•Éè3u\0F\0falphaI\0valueL\0cst\0Ljava/awt/color/ColorSpace;[\0	frgbvaluet\0[F[\0fvalueq\0~\0xp\0\0\0\0ˇˇ\0\0pppxsq\0~\0	?@\0\0\0\0\0w\0\0\0\0\0\0q\0~\0sr\0java.lang.Integer‚†§˜Åá8\0I\0valuexr\0java.lang.NumberÜ¨ïî‡ã\0\0xp\0\0\0Mxsr\0java.lang.DoubleÄ≥¬J)k˚\0D\0valuexq\0~\0@uæX6~Cwsr\0java.util.ArrayListxÅ“ô«aù\0I\0sizexp\0\0\0w\0\0\0sr\0/ro.barbos.gater.cutprocessor.diagram.GaterSlide\'E‚	î”…\0D\0heightD\0\npieceWidthI\0piecesD\0\nrightLimitD\0xD\0yL\0colort\0Ljava/awt/Color;L\0phaset\0\'Lro/barbos/gater/cutprocessor/CutPhase;xp@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0Ac–\0\0\0\0¿J@\0\0\0\0\0¿m–\0\0\0\0\0q\0~\0~r\0%ro.barbos.gater.cutprocessor.CutPhase\0\0\0\0\0\0\0\0\0\0xr\0java.lang.Enum\0\0\0\0\0\0\0\0\0\0xpt\0FIRSTsq\0~\0@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0Ac–\0\0\0\0¿Z∞\0\0\0\0\0¿k`\0\0\0\0\0q\0~\0q\0~\0sq\0~\0@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0Ac–\0\0\0\0¿Z∞\0\0\0\0\0¿h\0\0\0\0\0q\0~\0q\0~\0sr\00ro.barbos.gater.cutprocessor.diagram.GaterRotate†â\\\'öR8\0\0xpsq\0~\0@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0@fÄ\0\0\0\0\0¿J@\0\0\0\0\0¿m–\0\0\0\0\0q\0~\0~q\0~\0t\0THIRDsq\0~\0@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0@fÄ\0\0\0\0\0¿Z∞\0\0\0\0\0¿k`\0\0\0\0\0q\0~\0q\0~\0%sq\0~\0@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0@fÄ\0\0\0\0\0¿Z∞\0\0\0\0\0¿h\0\0\0\0\0q\0~\0q\0~\0%sq\0~\0@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0@fÄ\0\0\0\0\0¿d \0\0\0\0\0¿fÄ\0\0\0\0\0q\0~\0q\0~\0%sq\0~\0@Z@\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0@fÄ\0\0\0\0\0\0\0\0\0\0\0\0\0¿d\0\0\0\0\0ppsq\0~\0@Z@\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0@fÄ\0\0\0\0\0\0\0\0\0\0\0\0\0¿JÄ\0\0\0\0\0ppsq\0~\0@Z@\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0@fÄ\0\0\0\0\0\0\0\0\0\0\0\0\0@K@\0\0\0\0\0ppsq\0~\0@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0@fÄ\0\0\0\0\0¿d \0\0\0\0\0@d@\0\0\0\0\0q\0~\0~q\0~\0t\0SECONDsq\0~\0@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0@fÄ\0\0\0\0\0¿Z∞\0\0\0\0\0@f∞\0\0\0\0\0q\0~\0q\0~\0.sq\0~\0@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0@fÄ\0\0\0\0\0¿Z∞\0\0\0\0\0@i \0\0\0\0\0q\0~\0q\0~\0.sq\0~\0@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0@fÄ\0\0\0\0\0¿J@\0\0\0\0\0@kê\0\0\0\0\0q\0~\0q\0~\0.xsq\0~\0\0\0\0w\0\0\0sr\07ro.barbos.gater.cutprocessor.diagram.MultibladeCutSlide »LxúUì\0D\0heightD\0\npieceWidthI\0piecesD\0widthD\0xD\0yL\0colorq\0~\0L\0phaseq\0~\0\Zxp@Z@\0\0\0\0\0@1\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0¿g0\0\0\0\0\0¿d\0\0\0\0\0q\0~\0q\0~\0%sq\0~\04@Z@\0\0\0\0\0@1\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0¿lP\0\0\0\0\0¿JÄ\0\0\0\0\0q\0~\0q\0~\0%sq\0~\04@Z@\0\0\0\0\0@1\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0¿g0\0\0\0\0\0@K@\0\0\0\0\0q\0~\0q\0~\0%xsr\0.ro.barbos.gater.cutprocessor.LumberLogCutSteps\0\0\0\0\0\0\0\0L\0bottomt\04Lro/barbos/gater/cutprocessor/LumberLogSegmentSteps;L\0leftq\0~\09L\0\nmultiBladeq\0~\09L\0rightq\0~\09L\0stepSequenceq\0~\0L\0topq\0~\09xpsr\02ro.barbos.gater.cutprocessor.LumberLogSegmentStepsπ±{÷\"0˘\0L\0cutsq\0~\0xpsq\0~\0\0\0\0w\0\0\0sq\0~\0@í¶fffffsq\0~\0@1\0\0\0\0\0\0sq\0~\0@1\0\0\0\0\0\0sq\0~\0@1\0\0\0\0\0\0sq\0~\0@1\0\0\0\0\0\0xpsq\0~\0;sq\0~\0\0\0\0w\0\0\0sq\0~\0@Z@\0\0\0\0\0sq\0~\0@Z@\0\0\0\0\0sq\0~\0@Z@\0\0\0\0\0xsq\0~\0;sq\0~\0\0\0\0w\0\0\0sq\0~\0@í™fffffsq\0~\0@1\0\0\0\0\0\0sq\0~\0@1\0\0\0\0\0\0sq\0~\0@1\0\0\0\0\0\0xpsq\0~\0;sq\0~\0\0\0\0w\0\0\0sq\0~\0@ë@fffffsq\0~\0@1\0\0\0\0\0\0sq\0~\0@1\0\0\0\0\0\0sq\0~\0@1\0\0\0\0\0\0sq\0~\0@1\0\0\0\0\0\0x',0.425414364640884),(8,4,'1-24','','¨Ì\0sr\0/ro.barbos.gater.cutprocessor.diagram.CutDiagram~‘Ré\r’\0L\0cutInfot\05Lro/barbos/gater/cutprocessor/diagram/CutDiagramInfo;L\0debugSquaret\0Ljava/lang/Double;L\0gaterCutFlowt\0Ljava/util/List;L\0multiBladeSlidesq\0~\0L\0stepst\00Lro/barbos/gater/cutprocessor/LumberLogCutSteps;xpsr\03ro.barbos.gater.cutprocessor.diagram.CutDiagramInfo‰œ“Ÿ]\0D\0cutLayoutEfficencyD\0lumberLogVolumeD\0smallEndAreaD\0usedCutAreaL\0colorst\0Ljava/util/Map;L\0	cutPiecesq\0~\0xp@NßM©>A™2è.{@Yc(˘u@‰‡\0\0\0\0sr\0java.util.HashMap⁄¡√`—\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0105x17x3.1msr\0java.awt.Color•Éè3u\0F\0falphaI\0valueL\0cst\0Ljava/awt/color/ColorSpace;[\0	frgbvaluet\0[F[\0fvalueq\0~\0xp\0\0\0\0ˇˇ\0\0pppxsq\0~\0	?@\0\0\0\0\0w\0\0\0\0\0\0q\0~\0sr\0java.lang.Integer‚†§˜Åá8\0I\0valuexr\0java.lang.NumberÜ¨ïî‡ã\0\0xp\0\0\0xsr\0java.lang.DoubleÄ≥¬J)k˚\0D\0valuexq\0~\0@iœ4¨È 6sr\0java.util.ArrayListxÅ“ô«aù\0I\0sizexp\0\0\0w\0\0\0sr\0/ro.barbos.gater.cutprocessor.diagram.GaterSlide\'E‚	î”…\0D\0heightD\0\npieceWidthI\0piecesD\0\nrightLimitD\0xD\0yL\0colort\0Ljava/awt/Color;L\0phaset\0\'Lro/barbos/gater/cutprocessor/CutPhase;xp@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0Ac–\0\0\0\0¿J@\0\0\0\0\0¿a\0\0\0\0\0\0q\0~\0~r\0%ro.barbos.gater.cutprocessor.CutPhase\0\0\0\0\0\0\0\0\0\0xr\0java.lang.Enum\0\0\0\0\0\0\0\0\0\0xpt\0FIRSTsq\0~\0@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0Ac–\0\0\0\0¿J@\0\0\0\0\0¿] \0\0\0\0\0q\0~\0q\0~\0sr\00ro.barbos.gater.cutprocessor.diagram.GaterRotate†â\\\'öR8\0\0xpsq\0~\0@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0@X@\0\0\0\0\0¿J@\0\0\0\0\0¿a\0\0\0\0\0\0q\0~\0~q\0~\0t\0THIRDsq\0~\0@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0@X@\0\0\0\0\0¿J@\0\0\0\0\0¿] \0\0\0\0\0q\0~\0q\0~\0$sq\0~\0@Z@\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0@X@\0\0\0\0\0\0\0\0\0\0\0\0\0¿X@\0\0\0\0\0ppsq\0~\0@Z@\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0@X@\0\0\0\0\0\0\0\0\0\0\0\0\0@%\0\0\0\0\0\0ppsq\0~\0@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0@X@\0\0\0\0\0¿J@\0\0\0\0\0@]Ä\0\0\0\0\0q\0~\0~q\0~\0t\0SECONDxsq\0~\0\0\0\0w\0\0\0sr\07ro.barbos.gater.cutprocessor.diagram.MultibladeCutSlide »LxúUì\0D\0heightD\0\npieceWidthI\0piecesD\0widthD\0xD\0yL\0colorq\0~\0L\0phaseq\0~\0\Zxp@Z@\0\0\0\0\0@1\0\0\0\0\0\0\0\0\0\n\0\0\0\0\0\0\0\0¿Z \0\0\0\0\0¿X@\0\0\0\0\0q\0~\0q\0~\0$sq\0~\0-@Z@\0\0\0\0\0@1\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0¿V@\0\0\0\0\0@%\0\0\0\0\0\0q\0~\0q\0~\0$xsr\0.ro.barbos.gater.cutprocessor.LumberLogCutSteps\0\0\0\0\0\0\0\0L\0bottomt\04Lro/barbos/gater/cutprocessor/LumberLogSegmentSteps;L\0leftq\0~\01L\0\nmultiBladeq\0~\01L\0rightq\0~\01L\0stepSequenceq\0~\0L\0topq\0~\01xpsr\02ro.barbos.gater.cutprocessor.LumberLogSegmentStepsπ±{÷\"0˘\0L\0cutsq\0~\0xpsq\0~\0\0\0\0w\0\0\0sq\0~\0@çl\0\0\0\0\0sq\0~\0@1\0\0\0\0\0\0xpsq\0~\03sq\0~\0\0\0\0w\0\0\0sq\0~\0@Z@\0\0\0\0\0sq\0~\0@Z@\0\0\0\0\0xsq\0~\03sq\0~\0\0\0\0w\0\0\0sq\0~\0@çt\0\0\0\0\0sq\0~\0@1\0\0\0\0\0\0sq\0~\0@1\0\0\0\0\0\0xsq\0~\0\0\0\0w\0\0\0sr\01ro.barbos.gater.cutprocessor.diagram.GaterCutStep˛…hÑßÎ\0Z\0isRotateZ\0isStartL\0valueq\0~\0xp\0q\0~\0>sq\0~\0B\0\0q\0~\0?sq\0~\0B\0\0q\0~\0@sq\0~\0B\0sq\0~\0@VÄ\0\0\0\0\0sq\0~\0B\0q\0~\06sq\0~\0B\0\0q\0~\07sq\0~\0B\0sq\0~\0@fÄ\0\0\0\0\0sq\0~\0B\0sq\0~\0@å`\0\0\0\0\0sq\0~\0B\0\0sq\0~\0@1\0\0\0\0\0\0sq\0~\0B\0\0sq\0~\0@1\0\0\0\0\0\0sq\0~\0B\0\0q\0~\0:xsq\0~\03sq\0~\0\0\0\0w\0\0\0q\0~\0Mq\0~\0Oq\0~\0Qx',0.0011569998490869762),(9,5,'2-3','','¨Ì\0sr\0/ro.barbos.gater.cutprocessor.diagram.CutDiagram~‘Ré\r’\0L\0cutInfot\05Lro/barbos/gater/cutprocessor/diagram/CutDiagramInfo;L\0debugSquaret\0Ljava/lang/Double;L\0gaterCutFlowt\0Ljava/util/List;L\0multiBladeSlidesq\0~\0L\0stepst\00Lro/barbos/gater/cutprocessor/LumberLogCutSteps;xpsr\03ro.barbos.gater.cutprocessor.diagram.CutDiagramInfolÍ\0%Ù¿Œ\0D\0cutLayoutEfficencyD\0	cutVolumeD\0lumberLogVolumeD\0smallEndAreaD\0usedCutAreaL\0colorst\0Ljava/util/Map;L\0	cutPiecesq\0~\0xp@PF]¯ƒ!A§ø3¯\0\0\0A¥-¬p£◊@˙àË°èöª@˛\0\0\0\0sr\0java.util.HashMap⁄¡√`—\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0130X19X2,9 msr\0java.awt.Color•Éè3u\0F\0falphaI\0valueL\0cst\0Ljava/awt/color/ColorSpace;[\0	frgbvaluet\0[F[\0fvalueq\0~\0xp\0\0\0\0ˇ\0\0\0pppt\0105X17X2,5 msq\0~\0\0\0\0\0ˇ\0ˇ\0pppt\0105x17x3.1msq\0~\0\0\0\0\0ˇˇ\0\0pppxsq\0~\0	?@\0\0\0\0\0w\0\0\0\0\0\0q\0~\0sr\0java.lang.Integer‚†§˜Åá8\0I\0valuexr\0java.lang.NumberÜ¨ïî‡ã\0\0xp\0\0\0\'xsr\0java.lang.DoubleÄ≥¬J)k˚\0D\0valuexq\0~\0@pp≥xqssr\0java.util.ArrayListxÅ“ô«aù\0I\0sizexp\0\0\0	w\0\0\0	sr\0/ro.barbos.gater.cutprocessor.diagram.GaterSlide\'E‚	î”…\0D\0heightD\0\npieceWidthI\0piecesD\0\nrightLimitD\0xD\0yL\0colort\0Ljava/awt/Color;L\0phaset\0\'Lro/barbos/gater/cutprocessor/CutPhase;xp@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0Ac–\0\0\0\0¿J@\0\0\0\0\0¿f\0\0\0\0\0\0q\0~\0~r\0%ro.barbos.gater.cutprocessor.CutPhase\0\0\0\0\0\0\0\0\0\0xr\0java.lang.Enum\0\0\0\0\0\0\0\0\0\0xpt\0SECONDsq\0~\0@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0Ac–\0\0\0\0¿J@\0\0\0\0\0¿cê\0\0\0\0\0q\0~\0q\0~\0\"sq\0~\0@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0Ac–\0\0\0\0¿Z∞\0\0\0\0\0¿a \0\0\0\0\0q\0~\0q\0~\0\"sq\0~\0@Z@\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0Ac–\0\0\0\0\0\0\0\0\0\0\0\0¿]`\0\0\0\0\0ppsq\0~\0@Z@\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0Ac–\0\0\0\0\0\0\0\0\0\0\0\0¿$\0\0\0\0\0\0ppsq\0~\0@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0Ac–\0\0\0\0¿Z∞\0\0\0\0\0@X`\0\0\0\0\0q\0~\0~q\0~\0 t\0FIRSTsq\0~\0@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0Ac–\0\0\0\0¿Z∞\0\0\0\0\0@]@\0\0\0\0\0q\0~\0q\0~\0)sq\0~\0@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0Ac–\0\0\0\0¿J@\0\0\0\0\0@a\0\0\0\0\0q\0~\0q\0~\0)sq\0~\0@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0Ac–\0\0\0\0¿J@\0\0\0\0\0@cÄ\0\0\0\0\0q\0~\0q\0~\0)xsq\0~\0\Z\0\0\0w\0\0\0sr\07ro.barbos.gater.cutprocessor.diagram.MultibladeCutSlide »LxúUì\0D\0heightD\0\npieceWidthI\0piecesD\0widthD\0xD\0yL\0colorq\0~\0L\0phaseq\0~\0xp@Z@\0\0\0\0\0@1\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0¿b\0\0\0\0\0\0¿]`\0\0\0\0\0q\0~\0q\0~\0\"sq\0~\0/@Z@\0\0\0\0\0@1\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0¿c\0\0\0\0\0¿$\0\0\0\0\0\0q\0~\0q\0~\0\"xsr\0.ro.barbos.gater.cutprocessor.LumberLogCutSteps\0\0\0\0\0\0\0\0L\0bottomt\04Lro/barbos/gater/cutprocessor/LumberLogSegmentSteps;L\0leftq\0~\03L\0\nmultiBladeq\0~\03L\0rightq\0~\03L\0stepSequenceq\0~\0L\0topq\0~\03xpsr\02ro.barbos.gater.cutprocessor.LumberLogSegmentStepsπ±{÷\"0˘\0L\0cutsq\0~\0xpsq\0~\0\Z\0\0\0w\0\0\0sq\0~\0@è‹\0\0\0\0\0sq\0~\0@1\0\0\0\0\0\0sq\0~\0@1\0\0\0\0\0\0sq\0~\0@1\0\0\0\0\0\0sq\0~\0@1\0\0\0\0\0\0xpsq\0~\05sq\0~\0\Z\0\0\0w\0\0\0sq\0~\0@Z@\0\0\0\0\0sq\0~\0@Z@\0\0\0\0\0xpsq\0~\0\Z\0\0\0w\0\0\0sr\01ro.barbos.gater.cutprocessor.diagram.GaterCutStep˛…hÑßÎ\0Z\0isRotateZ\0isStartL\0valueq\0~\0xp\0q\0~\08sq\0~\0B\0\0q\0~\09sq\0~\0B\0\0q\0~\0:sq\0~\0B\0\0q\0~\0;sq\0~\0B\0\0q\0~\0<sq\0~\0B\0sq\0~\0@fÄ\0\0\0\0\0sq\0~\0B\0sq\0~\0@å¸\0\0\0\0\0sq\0~\0B\0\0sq\0~\0@1\0\0\0\0\0\0sq\0~\0B\0\0sq\0~\0@1\0\0\0\0\0\0sq\0~\0B\0\0sq\0~\0@1\0\0\0\0\0\0sq\0~\0B\0\0q\0~\0?xsq\0~\05sq\0~\0\Z\0\0\0w\0\0\0q\0~\0Kq\0~\0Mq\0~\0Oq\0~\0Qx',0.01740294511378849),(10,5,'2-5','','¨Ì\0sr\0/ro.barbos.gater.cutprocessor.diagram.CutDiagram~‘Ré\r’\0L\0cutInfot\05Lro/barbos/gater/cutprocessor/diagram/CutDiagramInfo;L\0debugSquaret\0Ljava/lang/Double;L\0gaterCutFlowt\0Ljava/util/List;L\0multiBladeSlidesq\0~\0L\0stepst\00Lro/barbos/gater/cutprocessor/LumberLogCutSteps;xpsr\03ro.barbos.gater.cutprocessor.diagram.CutDiagramInfolÍ\0%Ù¿Œ\0D\0cutLayoutEfficencyD\0	cutVolumeD\0lumberLogVolumeD\0smallEndAreaD\0usedCutAreaL\0colorst\0Ljava/util/Map;L\0	cutPiecesq\0~\0xp@P’.p4—¢A≠F@\0\0\0Aπwk„◊\n@˝vˆ∆*m]@Û÷¿\0\0\0\0sr\0java.util.HashMap⁄¡√`—\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0130X19X2,9 msr\0java.awt.Color•Éè3u\0F\0falphaI\0valueL\0cst\0Ljava/awt/color/ColorSpace;[\0	frgbvaluet\0[F[\0fvalueq\0~\0xp\0\0\0\0ˇ\0\0\0pppt\0105X17X2,5 msq\0~\0\0\0\0\0ˇ\0ˇ\0pppt\0105x17x3.1msq\0~\0\0\0\0\0ˇˇ\0\0pppxsq\0~\0	?@\0\0\0\0\0w\0\0\0\0\0\0q\0~\0sr\0java.lang.Integer‚†§˜Åá8\0I\0valuexr\0java.lang.NumberÜ¨ïî‡ã\0\0xp\0\0\0q\0~\0sq\0~\0\0\0\0xsr\0java.lang.DoubleÄ≥¬J)k˚\0D\0valuexq\0~\0@qR˘Fyi…sr\0java.util.ArrayListxÅ“ô«aù\0I\0sizexp\0\0\0w\0\0\0sr\0/ro.barbos.gater.cutprocessor.diagram.GaterSlide\'E‚	î”…\0D\0heightD\0\npieceWidthI\0piecesD\0\nrightLimitD\0xD\0yL\0colort\0Ljava/awt/Color;L\0phaset\0\'Lro/barbos/gater/cutprocessor/CutPhase;xp@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0Ac–\0\0\0\0¿J@\0\0\0\0\0¿g@\0\0\0\0\0q\0~\0~r\0%ro.barbos.gater.cutprocessor.CutPhase\0\0\0\0\0\0\0\0\0\0xr\0java.lang.Enum\0\0\0\0\0\0\0\0\0\0xpt\0FIRSTsq\0~\0@3\0\0\0\0\0\0@`@\0\0\0\0\0\0\0\0Ac–\0\0\0\0¿P@\0\0\0\0\0¿d–\0\0\0\0\0q\0~\0q\0~\0#sq\0~\0@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0Ac–\0\0\0\0¿Z∞\0\0\0\0\0¿b \0\0\0\0\0q\0~\0q\0~\0#sr\00ro.barbos.gater.cutprocessor.diagram.GaterRotate†â\\\'öR8\0\0xpsq\0~\0@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0@_`\0\0\0\0\0¿J@\0\0\0\0\0¿g@\0\0\0\0\0q\0~\0~q\0~\0!t\0THIRDsq\0~\0@3\0\0\0\0\0\0@`@\0\0\0\0\0\0\0\0@_`\0\0\0\0\0¿P@\0\0\0\0\0¿d–\0\0\0\0\0q\0~\0q\0~\0*sq\0~\0@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0@_`\0\0\0\0\0¿Z∞\0\0\0\0\0¿b \0\0\0\0\0q\0~\0q\0~\0*sq\0~\0@`@\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0@_`\0\0\0\0\0\0\0\0\0\0\0\0\0¿_`\0\0\0\0\0ppsq\0~\0@Z@\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0@_`\0\0\0\0\0\0\0\0\0\0\0\0\0@\0\0\0\0\0\0ppsq\0~\0@3\0\0\0\0\0\0@`@\0\0\0\0\0\0\0\0@_`\0\0\0\0\0¿a@\0\0\0\0\0@\\†\0\0\0\0\0q\0~\0~q\0~\0!t\0SECONDsq\0~\0@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0@_`\0\0\0\0\0¿Z∞\0\0\0\0\0@a\0\0\0\0\0\0q\0~\0q\0~\01sq\0~\0@3\0\0\0\0\0\0@`@\0\0\0\0\0\0\0\0@_`\0\0\0\0\0¿P@\0\0\0\0\0@cp\0\0\0\0\0q\0~\0q\0~\01xsq\0~\0\0\0\0w\0\0\0sr\07ro.barbos.gater.cutprocessor.diagram.MultibladeCutSlide »LxúUì\0D\0heightD\0\npieceWidthI\0piecesD\0widthD\0xD\0yL\0colorq\0~\0L\0phaseq\0~\0xp@`@\0\0\0\0\0@3\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0¿a†\0\0\0\0\0¿_`\0\0\0\0\0q\0~\0q\0~\0*sq\0~\06@Z@\0\0\0\0\0@1\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0¿c¿\0\0\0\0\0@\0\0\0\0\0\0q\0~\0q\0~\0*xsr\0.ro.barbos.gater.cutprocessor.LumberLogCutSteps\0\0\0\0\0\0\0\0L\0bottomt\04Lro/barbos/gater/cutprocessor/LumberLogSegmentSteps;L\0leftq\0~\0:L\0\nmultiBladeq\0~\0:L\0rightq\0~\0:L\0stepSequenceq\0~\0L\0topq\0~\0:xpsr\02ro.barbos.gater.cutprocessor.LumberLogSegmentStepsπ±{÷\"0˘\0L\0cutsq\0~\0xpsq\0~\0\0\0\0w\0\0\0sq\0~\0@ê\0\0\0\0\0sq\0~\0@3\0\0\0\0\0\0sq\0~\0@1\0\0\0\0\0\0sq\0~\0@3\0\0\0\0\0\0xpsq\0~\0<sq\0~\0\0\0\0w\0\0\0sq\0~\0@`@\0\0\0\0\0sq\0~\0@Z@\0\0\0\0\0xsq\0~\0<sq\0~\0\0\0\0w\0\0\0sq\0~\0@êJ\0\0\0\0\0sq\0~\0@1\0\0\0\0\0\0sq\0~\0@3\0\0\0\0\0\0sq\0~\0@1\0\0\0\0\0\0xsq\0~\0\0\0\0w\0\0\0sr\01ro.barbos.gater.cutprocessor.diagram.GaterCutStep˛…hÑßÎ\0Z\0isRotateZ\0isStartL\0valueq\0~\0xp\0q\0~\0Isq\0~\0N\0\0q\0~\0Jsq\0~\0N\0\0q\0~\0Ksq\0~\0N\0\0q\0~\0Lsq\0~\0N\0sq\0~\0@VÄ\0\0\0\0\0sq\0~\0N\0q\0~\0?sq\0~\0N\0\0q\0~\0@sq\0~\0N\0\0q\0~\0Asq\0~\0N\0\0q\0~\0Bsq\0~\0N\0sq\0~\0@fÄ\0\0\0\0\0sq\0~\0N\0sq\0~\0@ç‘\0\0\0\0\0sq\0~\0N\0\0sq\0~\0@1\0\0\0\0\0\0sq\0~\0N\0\0sq\0~\0@3\0\0\0\0\0\0sq\0~\0N\0\0sq\0~\0@1\0\0\0\0\0\0sq\0~\0N\0\0q\0~\0Exsq\0~\0<sq\0~\0\0\0\0w\0\0\0q\0~\0\\q\0~\0^q\0~\0`q\0~\0bx',0.024337075021696304),(11,5,'2-4','','¨Ì\0sr\0/ro.barbos.gater.cutprocessor.diagram.CutDiagram~‘Ré\r’\0L\0cutInfot\05Lro/barbos/gater/cutprocessor/diagram/CutDiagramInfo;L\0debugSquaret\0Ljava/lang/Double;L\0gaterCutFlowt\0Ljava/util/List;L\0multiBladeSlidesq\0~\0L\0stepst\00Lro/barbos/gater/cutprocessor/LumberLogCutSteps;xpsr\03ro.barbos.gater.cutprocessor.diagram.CutDiagramInfolÍ\0%Ù¿Œ\0D\0cutLayoutEfficencyD\0	cutVolumeD\0lumberLogVolumeD\0smallEndAreaD\0usedCutAreaL\0colorst\0Ljava/util/Map;L\0	cutPiecesq\0~\0xp@PŒ(¶k_A™ÆJ∞\0\0\0A∏æé◊\n=@˝vˆ∆*m]@Úˆ`\0\0\0\0sr\0java.util.HashMap⁄¡√`—\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0130X19X2,9 msr\0java.awt.Color•Éè3u\0F\0falphaI\0valueL\0cst\0Ljava/awt/color/ColorSpace;[\0	frgbvaluet\0[F[\0fvalueq\0~\0xp\0\0\0\0ˇ\0\0\0pppt\0105X17X2,5 msq\0~\0\0\0\0\0ˇ\0ˇ\0pppt\0105x17x3.1msq\0~\0\0\0\0\0ˇˇ\0\0pppxsq\0~\0	?@\0\0\0\0\0w\0\0\0\0\0\0q\0~\0sr\0java.lang.Integer‚†§˜Åá8\0I\0valuexr\0java.lang.NumberÜ¨ïî‡ã\0\0xp\0\0\0q\0~\0sq\0~\0\0\0\0xsr\0java.lang.DoubleÄ≥¬J)k˚\0D\0valuexq\0~\0@qR˘Fyi…sr\0java.util.ArrayListxÅ“ô«aù\0I\0sizexp\0\0\0	w\0\0\0	sr\00ro.barbos.gater.cutprocessor.diagram.GaterRotate†â\\\'öR8\0\0xpsq\0~\0sr\0/ro.barbos.gater.cutprocessor.diagram.GaterSlide\'E‚	î”…\0D\0heightD\0\npieceWidthI\0piecesD\0\nrightLimitD\0xD\0yL\0colort\0Ljava/awt/Color;L\0phaset\0\'Lro/barbos/gater/cutprocessor/CutPhase;xp@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0Ac–\0\0\0\0¿J@\0\0\0\0\0¿f–\0\0\0\0\0q\0~\0~r\0%ro.barbos.gater.cutprocessor.CutPhase\0\0\0\0\0\0\0\0\0\0xr\0java.lang.Enum\0\0\0\0\0\0\0\0\0\0xpt\0SECONDsq\0~\0sq\0~\0 @3\0\0\0\0\0\0@`@\0\0\0\0\0\0\0\0Ac–\0\0\0\0¿`x\0\0\0\0\0@XmπÜñ8q\0~\0~q\0~\0$t\0THIRDsq\0~\0 @3\0\0\0\0\0\0@`@\0\0\0\0\0\0\0\0Ac–\0\0\0\0¿`x\0\0\0\0\0@]ÕπÜñ8q\0~\0q\0~\0*sq\0~\0 @3\0\0\0\0\0\0@`@\0\0\0\0\0\0\0\0Ac–\0\0\0\0¿P@\0\0\0\0\0@añÉ\\√Kq\0~\0q\0~\0*sq\0~\0 @3\0\0\0\0\0\0@`@\0\0\0\0\0\0\0\0Ac–\0\0\0\0¿P@\0\0\0\0\0@dFÉ\\√Kq\0~\0q\0~\0*sq\0~\0 @1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0Ac–\0\0\0\0¿J@\0\0\0\0\0¿gY|£<¥‰q\0~\0~q\0~\0$t\0FOURTHxsq\0~\0\0\0\0w\0\0\0sr\07ro.barbos.gater.cutprocessor.diagram.MultibladeCutSlide »LxúUì\0D\0heightD\0\npieceWidthI\0piecesD\0widthD\0xD\0yL\0colorq\0~\0!L\0phaseq\0~\0\"xp@`@\0\0\0\0\0@3\0\0\0\0\0\0\0\0\0	\0\0\0\0\0\0\0\0¿YÄ\0\0\0\0\0¿dÈ|£<¥‰q\0~\0q\0~\00sq\0~\03@`@\0\0\0\0\0@3\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0¿e`\0\0\0\0\0¿AeÚåÚ”êq\0~\0q\0~\00xsr\0.ro.barbos.gater.cutprocessor.LumberLogCutSteps\0\0\0\0\0\0\0\0L\0bottomt\04Lro/barbos/gater/cutprocessor/LumberLogSegmentSteps;L\0leftq\0~\07L\0\nmultiBladeq\0~\07L\0rightq\0~\07L\0stepSequenceq\0~\0L\0topq\0~\07xpsr\02ro.barbos.gater.cutprocessor.LumberLogSegmentStepsπ±{÷\"0˘\0L\0cutsq\0~\0xpsq\0~\0\0\0\0w\0\0\0sq\0~\0@ê6–kòidsq\0~\0@3\0\0\0\0\0\0sq\0~\0@3\0\0\0\0\0\0sq\0~\0@3\0\0\0\0\0\0sq\0~\0@3\0\0\0\0\0\0xsq\0~\09sq\0~\0\0\0\0w\0\0\0sq\0~\0@ê\0\0\0\0\0xsq\0~\09sq\0~\0\0\0\0w\0\0\0sq\0~\0@`@\0\0\0\0\0sq\0~\0@`@\0\0\0\0\0xsq\0~\09sq\0~\0\0\0\0w\0\0\0sq\0~\0@èê\0\0\0\0\0sq\0~\0@1\0\0\0\0\0\0xsq\0~\0\0\0\0w\0\0\0sr\01ro.barbos.gater.cutprocessor.diagram.GaterCutStep˛…hÑßÎ\0Z\0isRotateZ\0isStartL\0valueq\0~\0xp\0q\0~\0Csq\0~\0M\0sq\0~\0@fÄ\0\0\0\0\0sq\0~\0M\0q\0~\0Jsq\0~\0M\0\0q\0~\0Ksq\0~\0M\0sq\0~\0@VÄ\0\0\0\0\0sq\0~\0M\0q\0~\0<sq\0~\0M\0\0q\0~\0=sq\0~\0M\0\0q\0~\0>sq\0~\0M\0\0q\0~\0?sq\0~\0M\0\0q\0~\0@sq\0~\0M\0sq\0~\0@fÄ\0\0\0\0\0sq\0~\0M\0sq\0~\0@çSˇˇˇˇˇsq\0~\0M\0\0sq\0~\0@1\0\0\0\0\0\0sq\0~\0M\0\0q\0~\0Fxsq\0~\09sq\0~\0\0\0\0w\0\0\0q\0~\0]q\0~\0_x',0.022367047127506288),(12,5,'2-1','','¨Ì\0sr\0/ro.barbos.gater.cutprocessor.diagram.CutDiagram~‘Ré\r’\0L\0cutInfot\05Lro/barbos/gater/cutprocessor/diagram/CutDiagramInfo;L\0debugSquaret\0Ljava/lang/Double;L\0gaterCutFlowt\0Ljava/util/List;L\0multiBladeSlidesq\0~\0L\0stepst\00Lro/barbos/gater/cutprocessor/LumberLogCutSteps;xpsr\03ro.barbos.gater.cutprocessor.diagram.CutDiagramInfolÍ\0%Ù¿Œ\0D\0cutLayoutEfficencyD\0	cutVolumeD\0lumberLogVolumeD\0smallEndAreaD\0usedCutAreaL\0colorst\0Ljava/util/Map;L\0	cutPiecesq\0~\0xp@P=Ã∏ôÆqAûÅj\0\0\0A∂∞1Õ·GÆ@Yc(˘u@Â> \0\0\0\0sr\0java.util.HashMap⁄¡√`—\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0130X19X2,9 msr\0java.awt.Color•Éè3u\0F\0falphaI\0valueL\0cst\0Ljava/awt/color/ColorSpace;[\0	frgbvaluet\0[F[\0fvalueq\0~\0xp\0\0\0\0ˇ\0\0\0pppt\0105X17X2,5 msq\0~\0\0\0\0\0ˇ\0ˇ\0pppt\0105x17x3.1msq\0~\0\0\0\0\0ˇˇ\0\0pppxsq\0~\0	?@\0\0\0\0\0w\0\0\0\0\0\0q\0~\0sr\0java.lang.Integer‚†§˜Åá8\0I\0valuexr\0java.lang.NumberÜ¨ïî‡ã\0\0xp\0\0\0q\0~\0sq\0~\0\0\0\0xsr\0java.lang.DoubleÄ≥¬J)k˚\0D\0valuexq\0~\0@iœ4¨È 6sr\0java.util.ArrayListxÅ“ô«aù\0I\0sizexp\0\0\0w\0\0\0sr\0/ro.barbos.gater.cutprocessor.diagram.GaterSlide\'E‚	î”…\0D\0heightD\0\npieceWidthI\0piecesD\0\nrightLimitD\0xD\0yL\0colort\0Ljava/awt/Color;L\0phaset\0\'Lro/barbos/gater/cutprocessor/CutPhase;xp@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0Ac–\0\0\0\0¿J@\0\0\0\0\0¿`∞\0\0\0\0\0q\0~\0~r\0%ro.barbos.gater.cutprocessor.CutPhase\0\0\0\0\0\0\0\0\0\0xr\0java.lang.Enum\0\0\0\0\0\0\0\0\0\0xpt\0SECONDsq\0~\0@3\0\0\0\0\0\0@`@\0\0\0\0\0\0\0\0Ac–\0\0\0\0¿P@\0\0\0\0\0¿\\Ä\0\0\0\0\0q\0~\0q\0~\0#sq\0~\0@`@\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0Ac–\0\0\0\0\0\0\0\0\0\0\0\0¿W \0\0\0\0\0ppsq\0~\0@3\0\0\0\0\0\0@`@\0\0\0\0\0\0\0\0Ac–\0\0\0\0¿`x\0\0\0\0\0@D\0\0\0\0\0\0q\0~\0~q\0~\0!t\0FIRSTsq\0~\0@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0Ac–\0\0\0\0¿Z∞\0\0\0\0\0@N¿\0\0\0\0\0q\0~\0q\0~\0(sq\0~\0@1\0\0\0\0\0\0@Z@\0\0\0\0\0\0\0\0Ac–\0\0\0\0¿Z∞\0\0\0\0\0@T@\0\0\0\0\0q\0~\0q\0~\0(sq\0~\0@3\0\0\0\0\0\0@`@\0\0\0\0\0\0\0\0Ac–\0\0\0\0¿P@\0\0\0\0\0@Y \0\0\0\0\0q\0~\0q\0~\0(xsq\0~\0\0\0\0w\0\0\0sr\07ro.barbos.gater.cutprocessor.diagram.MultibladeCutSlide »LxúUì\0D\0heightD\0\npieceWidthI\0piecesD\0widthD\0xD\0yL\0colorq\0~\0L\0phaseq\0~\0xp@`@\0\0\0\0\0@3\0\0\0\0\0\0\0\0\0\n\0\0\0\0\0\0\0\0¿\\ \0\0\0\0\0¿W \0\0\0\0\0q\0~\0q\0~\0#xsr\0.ro.barbos.gater.cutprocessor.LumberLogCutSteps\0\0\0\0\0\0\0\0L\0bottomt\04Lro/barbos/gater/cutprocessor/LumberLogSegmentSteps;L\0leftq\0~\01L\0\nmultiBladeq\0~\01L\0rightq\0~\01L\0stepSequenceq\0~\0L\0topq\0~\01xpsr\02ro.barbos.gater.cutprocessor.LumberLogSegmentStepsπ±{÷\"0˘\0L\0cutsq\0~\0xpsq\0~\0\0\0\0w\0\0\0sq\0~\0@å\0\0\0\0\0sq\0~\0@3\0\0\0\0\0\0sq\0~\0@1\0\0\0\0\0\0sq\0~\0@1\0\0\0\0\0\0sq\0~\0@3\0\0\0\0\0\0xpsq\0~\03sq\0~\0\0\0\0w\0\0\0sq\0~\0@`@\0\0\0\0\0xpsq\0~\0\0\0\0	w\0\0\0	sr\01ro.barbos.gater.cutprocessor.diagram.GaterCutStep˛…hÑßÎ\0Z\0isRotateZ\0isStartL\0valueq\0~\0xp\0q\0~\06sq\0~\0?\0\0q\0~\07sq\0~\0?\0\0q\0~\08sq\0~\0?\0\0q\0~\09sq\0~\0?\0\0q\0~\0:sq\0~\0?\0sq\0~\0@fÄ\0\0\0\0\0sq\0~\0?\0sq\0~\0@â‹\0\0\0\0\0sq\0~\0?\0\0sq\0~\0@1\0\0\0\0\0\0sq\0~\0?\0\0sq\0~\0@3\0\0\0\0\0\0xsq\0~\03sq\0~\0\0\0\0w\0\0\0q\0~\0Hq\0~\0Jq\0~\0Lx',0.012786961314067438);
/*!40000 ALTER TABLE `cutplanlumberlogdiagram` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cutplanproduct`
--

DROP TABLE IF EXISTS `cutplanproduct`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cutplanproduct` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PlanId` int(11) NOT NULL,
  `ProductName` varchar(50) DEFAULT NULL,
  `TargetVolume` double NOT NULL,
  `TargetPieces` int(11) NOT NULL,
  `CutPieces` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_CutPlanTargetProduct_planid` (`PlanId`),
  CONSTRAINT `cutplanproduct_ibfk_1` FOREIGN KEY (`PlanId`) REFERENCES `cutplan` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cutplanproduct`
--

LOCK TABLES `cutplanproduct` WRITE;
/*!40000 ALTER TABLE `cutplanproduct` DISABLE KEYS */;
INSERT INTO `cutplanproduct` VALUES (1,1,'105x17x3.1m',110,19879,121),(4,3,'105x17x3.1m',1,181,77),(5,4,'105x17x3.1m',110,19879,23),(6,5,'105x17x3.1m',10,1808,27),(7,5,'105X17X2,5 m',10,2241,41),(8,5,'130X19X2,9 m',10,1397,61);
/*!40000 ALTER TABLE `cutplanproduct` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gatersetting`
--

DROP TABLE IF EXISTS `gatersetting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gatersetting` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(30) DEFAULT NULL,
  `Val` decimal(10,2) DEFAULT NULL,
  `Metric` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_gatersetting_metric` (`Metric`),
  CONSTRAINT `gatersetting_ibfk_1` FOREIGN KEY (`Metric`) REFERENCES `metric` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gatersetting`
--

LOCK TABLES `gatersetting` WRITE;
/*!40000 ALTER TABLE `gatersetting` DISABLE KEYS */;
INSERT INTO `gatersetting` VALUES (1,'Sus',1400.00,1),(2,'Jos',710.00,1),(3,'Limita jos',56.00,1),(4,'Multilama error',0.00,1),(6,'Toleranta margine',4.00,1);
/*!40000 ALTER TABLE `gatersetting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `idplate`
--

DROP TABLE IF EXISTS `idplate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `idplate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `label` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=212 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `idplate`
--

LOCK TABLES `idplate` WRITE;
/*!40000 ALTER TABLE `idplate` DISABLE KEYS */;
INSERT INTO `idplate` VALUES (1,'1-1'),(3,'1-3'),(4,'1-4'),(5,'1-5'),(6,'1-6'),(7,'1-7'),(8,'1-8'),(9,'1-9'),(10,'1-10'),(11,'1-11'),(12,'1-12'),(13,'1-13'),(14,'1-14'),(15,'1-15'),(16,'1-16'),(17,'1-17'),(18,'1-18'),(19,'1-19'),(20,'1-20'),(21,'1-21'),(22,'1-22'),(23,'1-23'),(24,'1-24'),(25,'1-25'),(26,'1-26'),(27,'1-27'),(28,'1-28'),(29,'1-29'),(30,'1-30'),(31,'2-1'),(32,'2-2'),(33,'2-3'),(34,'2-4'),(35,'2-5'),(36,'2-6'),(37,'2-7'),(38,'2-8'),(39,'2-9'),(40,'2-10'),(41,'2-11'),(42,'2-12'),(43,'2-13'),(44,'2-14'),(45,'2-15'),(46,'2-16'),(47,'2-17'),(48,'2-18'),(49,'2-19'),(50,'2-20'),(51,'2-21'),(52,'2-22'),(53,'2-23'),(54,'2-24'),(55,'2-25'),(56,'2-26'),(57,'2-27'),(58,'2-28'),(59,'2-29'),(60,'2-30'),(61,'3-1'),(62,'3-2'),(63,'3-3'),(64,'3-4'),(65,'3-5'),(66,'3-6'),(67,'3-7'),(68,'3-8'),(69,'3-9'),(70,'3-10'),(71,'3-11'),(72,'3-12'),(73,'3-13'),(74,'3-14'),(75,'3-15'),(76,'3-16'),(77,'3-17'),(78,'3-18'),(79,'3-19'),(80,'3-20'),(81,'3-21'),(82,'3-22'),(83,'3-23'),(84,'3-24'),(85,'3-25'),(86,'3-26'),(87,'3-27'),(88,'3-28'),(89,'3-29'),(90,'3-30'),(91,'4-1'),(92,'4-2'),(93,'4-3'),(94,'4-4'),(95,'4-5'),(96,'4-6'),(97,'4-7'),(98,'4-8'),(99,'4-9'),(100,'4-10'),(101,'4-11'),(102,'4-12'),(103,'4-13'),(104,'4-14'),(105,'4-15'),(106,'4-16'),(107,'4-17'),(108,'4-18'),(109,'4-19'),(110,'4-20'),(111,'4-21'),(112,'4-22'),(113,'4-23'),(114,'4-24'),(115,'4-25'),(116,'4-26'),(117,'4-27'),(118,'4-28'),(119,'4-29'),(120,'4-30'),(121,'5-1'),(122,'5-2'),(123,'5-3'),(124,'5-4'),(125,'5-5'),(126,'5-6'),(127,'5-7'),(128,'5-8'),(129,'5-9'),(130,'5-10'),(131,'5-11'),(132,'5-12'),(133,'5-13'),(134,'5-14'),(135,'5-15'),(136,'5-16'),(137,'5-17'),(138,'5-18'),(139,'5-19'),(140,'5-20'),(141,'5-21'),(142,'5-22'),(143,'5-23'),(144,'5-24'),(145,'5-25'),(146,'5-26'),(147,'5-27'),(148,'5-28'),(149,'5-29'),(150,'5-30'),(151,'6-1'),(152,'6-2'),(153,'6-3'),(154,'6-4'),(155,'6-5'),(156,'6-6'),(157,'6-7'),(158,'6-8'),(159,'6-9'),(160,'6-10'),(161,'6-11'),(162,'6-12'),(163,'6-13'),(164,'6-14'),(165,'6-15'),(166,'6-16'),(167,'6-17'),(168,'6-18'),(169,'6-19'),(170,'6-20'),(171,'6-21'),(172,'6-22'),(173,'6-23'),(174,'6-24'),(175,'6-25'),(176,'6-26'),(177,'6-27'),(178,'6-28'),(179,'6-29'),(180,'6-30'),(181,'7-1'),(182,'7-2'),(183,'7-3'),(184,'7-4'),(185,'7-5'),(186,'7-6'),(187,'7-7'),(188,'7-8'),(190,'7-10'),(191,'7-11'),(192,'7-12'),(193,'7-13'),(194,'7-14'),(195,'7-15'),(196,'7-16'),(197,'7-17'),(198,'7-18'),(199,'7-19'),(200,'7-20'),(201,'7-21'),(202,'7-22'),(203,'7-23'),(204,'7-24'),(205,'7-25'),(206,'7-26'),(207,'7-27'),(208,'7-28'),(209,'7-29'),(210,'7-30'),(211,'1-31');
/*!40000 ALTER TABLE `idplate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lumberclass`
--

DROP TABLE IF EXISTS `lumberclass`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lumberclass` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lumberclass`
--

LOCK TABLES `lumberclass` WRITE;
/*!40000 ALTER TABLE `lumberclass` DISABLE KEYS */;
INSERT INTO `lumberclass` VALUES (1,'A'),(2,'A-B'),(3,'B'),(4,'B-C');
/*!40000 ALTER TABLE `lumberclass` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lumberentry`
--

DROP TABLE IF EXISTS `lumberentry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lumberentry` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` int(11) NOT NULL,
  `entrydate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_lumberentry_user` (`user`),
  CONSTRAINT `lumberentry_ibfk_1` FOREIGN KEY (`user`) REFERENCES `user` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lumberentry`
--

LOCK TABLES `lumberentry` WRITE;
/*!40000 ALTER TABLE `lumberentry` DISABLE KEYS */;
INSERT INTO `lumberentry` VALUES (1,1,'2013-11-28 20:50:19'),(2,1,'2013-11-29 17:28:15'),(3,1,'2013-11-29 21:21:50'),(4,2,'2013-12-02 22:03:26'),(5,2,'2013-12-09 20:46:47'),(6,2,'2013-12-09 20:48:38'),(7,2,'2013-12-11 22:03:01'),(8,2,'2014-01-17 14:19:41'),(9,2,'2014-01-19 17:18:52'),(10,2,'2014-01-20 13:49:19'),(11,2,'2014-01-22 16:35:25'),(12,2,'2014-03-09 06:02:28'),(13,2,'2014-03-09 06:03:13'),(14,2,'2014-04-06 13:00:58'),(15,2,'2014-04-06 13:13:35'),(16,2,'2014-04-06 16:58:48'),(17,2,'2014-04-06 17:05:22'),(18,2,'2014-04-06 17:18:50');
/*!40000 ALTER TABLE `lumberentry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lumberentry_to_lumberlog`
--

DROP TABLE IF EXISTS `lumberentry_to_lumberlog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lumberentry_to_lumberlog` (
  `entryid` int(11) NOT NULL,
  `lumberlogid` int(11) NOT NULL,
  KEY `fk_lumberentry_mapentry` (`entryid`),
  KEY `fk_lumberentry_maplumber` (`lumberlogid`),
  CONSTRAINT `lumberentry_to_lumberlog_ibfk_1` FOREIGN KEY (`entryid`) REFERENCES `lumberentry` (`id`),
  CONSTRAINT `lumberentry_to_lumberlog_ibfk_2` FOREIGN KEY (`lumberlogid`) REFERENCES `lumberlog` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lumberentry_to_lumberlog`
--

LOCK TABLES `lumberentry_to_lumberlog` WRITE;
/*!40000 ALTER TABLE `lumberentry_to_lumberlog` DISABLE KEYS */;
INSERT INTO `lumberentry_to_lumberlog` VALUES (7,8),(8,9),(10,11),(11,12),(11,13),(11,14),(11,15),(12,16),(13,17),(13,18),(13,19),(16,22),(17,23),(18,24);
/*!40000 ALTER TABLE `lumberentry_to_lumberlog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lumberlog`
--

DROP TABLE IF EXISTS `lumberlog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lumberlog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(100) DEFAULT NULL,
  `small_diameter` decimal(10,2) NOT NULL,
  `medium_diameter` decimal(10,2) NOT NULL,
  `big_diameter` decimal(10,2) NOT NULL,
  `length` decimal(10,2) NOT NULL,
  `volume` decimal(20,2) NOT NULL,
  `lumbertype` int(11) DEFAULT NULL,
  `lumberclass` int(11) DEFAULT NULL,
  `stack` int(11) NOT NULL,
  `idplate` int(11) DEFAULT NULL,
  `planId` int(11) DEFAULT NULL,
  `reallength` decimal(10,2) NOT NULL,
  `realvolume` decimal(20,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_log_lumbertype` (`lumbertype`),
  KEY `fk_log_lumberclass` (`lumberclass`),
  KEY `fk_log_idplate` (`idplate`),
  KEY `fk_log_lumberstack` (`stack`),
  CONSTRAINT `lumberlog_ibfk_1` FOREIGN KEY (`lumbertype`) REFERENCES `lumbertype` (`id`),
  CONSTRAINT `lumberlog_ibfk_2` FOREIGN KEY (`lumberclass`) REFERENCES `lumberclass` (`id`),
  CONSTRAINT `lumberlog_ibfk_3` FOREIGN KEY (`idplate`) REFERENCES `idplate` (`id`),
  CONSTRAINT `lumberlog_ibfk_4` FOREIGN KEY (`stack`) REFERENCES `lumberstack` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lumberlog`
--

LOCK TABLES `lumberlog` WRITE;
/*!40000 ALTER TABLE `lumberlog` DISABLE KEYS */;
INSERT INTO `lumberlog` VALUES (8,NULL,500.00,0.00,500.00,4000.00,785398163.40,1,1,4,5,3,4100.00,805033117.48),(9,NULL,300.00,0.00,300.00,3000.00,212057504.12,1,1,1,24,4,3100.00,219126087.59),(11,NULL,300.00,0.00,400.00,4000.00,369870175.08,1,1,1,31,5,4100.00,380645837.88),(12,NULL,400.00,0.00,500.00,2400.00,349240383.32,2,1,2,32,NULL,2400.00,349240383.32),(13,NULL,380.00,0.00,410.00,2600.00,325615606.57,1,1,2,33,5,2700.00,338498754.44),(14,NULL,400.00,0.00,500.00,3000.00,411025038.84,2,1,2,34,5,3000.00,411025038.84),(15,NULL,400.00,0.00,400.00,3000.00,376991118.43,1,1,2,35,5,3400.00,427256600.89),(16,NULL,300.00,0.00,320.00,3000.00,226482650.39,1,1,1,1,NULL,3100.00,234276418.16),(17,NULL,320.00,0.00,330.00,3000.00,243813769.86,1,1,1,10,NULL,3100.00,252110192.46),(18,NULL,330.00,0.00,340.00,3000.00,264469741.55,1,1,1,11,NULL,3400.00,300786552.63),(19,NULL,400.00,0.00,430.00,3000.00,393065600.84,1,1,2,12,NULL,3300.00,434636725.63),(22,NULL,400.00,0.00,400.00,4000.00,502654824.57,1,1,2,14,NULL,4000.00,502654824.57),(23,NULL,400.00,0.00,400.00,4000.00,502654824.57,2,3,2,15,NULL,4000.00,502654824.57),(24,NULL,400.00,0.00,400.00,4000.00,502654824.57,4,3,2,16,NULL,4000.00,502654824.57);
/*!40000 ALTER TABLE `lumberlog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lumberlog_diameter`
--

DROP TABLE IF EXISTS `lumberlog_diameter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lumberlog_diameter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lumberlog_id` int(11) NOT NULL,
  `diameter` decimal(10,2) NOT NULL,
  `metric` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_log_diameter` (`lumberlog_id`),
  CONSTRAINT `lumberlog_diameter_ibfk_1` FOREIGN KEY (`lumberlog_id`) REFERENCES `lumberlog` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lumberlog_diameter`
--

LOCK TABLES `lumberlog_diameter` WRITE;
/*!40000 ALTER TABLE `lumberlog_diameter` DISABLE KEYS */;
INSERT INTO `lumberlog_diameter` VALUES (23,8,500.00,1),(24,8,500.00,1),(25,8,500.00,1),(26,9,300.00,1),(27,9,300.00,1),(28,11,340.00,1),(29,11,340.00,1),(30,11,340.00,1),(31,12,400.00,1),(32,13,400.00,1),(33,14,400.00,1),(34,14,400.00,1),(35,15,400.00,1),(36,15,400.00,1),(37,16,310.00,1),(38,16,310.00,1),(39,17,320.00,1),(40,17,320.00,1),(41,18,330.00,1),(42,18,340.00,1),(43,19,400.00,1),(44,19,410.00,1),(51,22,400.00,1),(52,22,400.00,1),(53,22,400.00,1),(63,23,400.00,1),(64,23,400.00,1),(65,23,400.00,1),(75,24,400.00,1),(76,24,400.00,1),(77,24,400.00,1);
/*!40000 ALTER TABLE `lumberlog_diameter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lumberlog_processed`
--

DROP TABLE IF EXISTS `lumberlog_processed`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lumberlog_processed` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `entrydate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `description` varchar(100) DEFAULT NULL,
  `small_diameter` decimal(10,2) NOT NULL,
  `medium_diameter` decimal(10,2) NOT NULL,
  `big_diameter` decimal(10,2) NOT NULL,
  `length` decimal(10,2) NOT NULL,
  `volume` decimal(20,2) NOT NULL,
  `idlumbertype` int(11) DEFAULT NULL,
  `idlumberclass` int(11) DEFAULT NULL,
  `idstack` int(11) NOT NULL,
  `idplate` int(11) DEFAULT NULL,
  `planId` int(11) DEFAULT NULL,
  `message` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lumberlog_processed`
--

LOCK TABLES `lumberlog_processed` WRITE;
/*!40000 ALTER TABLE `lumberlog_processed` DISABLE KEYS */;
INSERT INTO `lumberlog_processed` VALUES (1,3,'2013-11-28 20:54:05',NULL,400.00,0.00,500.00,4100.00,552658507.64,1,1,2,1,1,''),(2,2,'2014-01-19 18:05:11',NULL,300.00,0.00,300.00,4100.00,289811922.29,1,1,1,1,2,''),(3,2,'2014-01-19 18:05:27',NULL,500.00,0.00,800.00,4100.00,960541953.84,1,1,4,2,1,''),(4,2,'2014-01-19 18:05:34',NULL,350.00,0.00,350.00,4100.00,394466227.57,1,1,1,3,2,''),(5,2,'2014-01-19 18:05:42',NULL,500.00,0.00,500.00,4100.00,805033117.48,1,1,4,4,2,''),(6,2,'2014-01-20 13:31:13',NULL,400.00,0.00,500.00,1000.00,159697626.56,1,1,2,6,0,''),(7,2,'2014-01-20 13:32:05',NULL,600.00,0.00,600.00,5000.00,1413716694.12,1,3,6,44,0,''),(8,2,'2014-01-20 13:33:24',NULL,300.00,0.00,400.00,3000.00,238237442.90,1,2,1,70,0,''),(9,2,'2014-04-06 18:05:07',NULL,400.00,0.00,500.00,4000.00,570722665.40,1,1,2,13,0,'');
/*!40000 ALTER TABLE `lumberlog_processed` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lumberstack`
--

DROP TABLE IF EXISTS `lumberstack`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lumberstack` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `minimum` decimal(10,2) NOT NULL,
  `maximum` decimal(10,2) NOT NULL,
  `metric` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_lumberstack_metric` (`metric`),
  CONSTRAINT `lumberstack_ibfk_1` FOREIGN KEY (`metric`) REFERENCES `metric` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lumberstack`
--

LOCK TABLES `lumberstack` WRITE;
/*!40000 ALTER TABLE `lumberstack` DISABLE KEYS */;
INSERT INTO `lumberstack` VALUES (1,'Stiva 1',300.00,350.00,1),(2,'Stiva 2',351.00,400.00,1),(3,'Stiva 3',401.00,450.00,1),(4,'Stiva 4',451.00,500.00,1),(5,'Stiva 5',501.00,550.00,1),(6,'Stiva 6',551.00,600.00,1);
/*!40000 ALTER TABLE `lumberstack` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lumbertype`
--

DROP TABLE IF EXISTS `lumbertype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lumbertype` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lumbertype`
--

LOCK TABLES `lumbertype` WRITE;
/*!40000 ALTER TABLE `lumbertype` DISABLE KEYS */;
INSERT INTO `lumbertype` VALUES (1,'brad'),(2,'molid'),(3,'fag'),(4,'stejar');
/*!40000 ALTER TABLE `lumbertype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `metric`
--

DROP TABLE IF EXISTS `metric`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `metric` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `metric`
--

LOCK TABLES `metric` WRITE;
/*!40000 ALTER TABLE `metric` DISABLE KEYS */;
INSERT INTO `metric` VALUES (1,'Milimetri'),(2,'Centimetri'),(3,'Decimetri'),(4,'Metri');
/*!40000 ALTER TABLE `metric` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `label` varchar(50) NOT NULL,
  `length` decimal(10,2) NOT NULL,
  `width` decimal(10,2) NOT NULL,
  `thick` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'105x17x3.1m',3100.00,105.00,17.00),(2,'105x17x4.1m',4100.00,105.00,17.00),(3,'110x20x3.1m',3100.00,110.00,20.00),(4,'110x20x4.1m',4100.00,110.00,20.00),(5,'130x19x3.1m',3100.00,130.00,19.00),(6,'130x19x4.1m',4100.00,130.00,19.00),(7,'125x23x3.1m',3100.00,125.00,23.00),(8,'125x23x4.1m',4100.00,125.00,23.00),(9,'105X17X2,5 m',2500.00,105.00,17.00),(10,'130X19X2,9 m',2900.00,130.00,19.00),(11,'105X15X3,1 m',3100.00,105.00,15.00),(12,'105X15X4,1 m',4100.00,105.00,15.00),(13,'105X8X2,6 m',2600.00,105.00,8.00),(14,'105X15X2,6 m',2600.00,105.00,15.00),(15,'105X45X2,6 m',2600.00,105.00,45.00),(16,'125X23X2,6 m',2600.00,125.00,23.00),(17,'105X35X2,6 m',2600.00,105.00,35.00),(18,'105X23X2,6 m',2600.00,105.00,23.00),(19,'110X20X2,6 m',2600.00,110.00,20.00);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `UserName` varchar(30) DEFAULT NULL,
  `Password` varchar(30) DEFAULT NULL,
  `FullName` varchar(200) DEFAULT NULL,
  `RightsLevel` int(11) DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'test','test','Operator stock',1),(2,'admin','admin','Administrator',0),(3,'operator','operator','Operator Gater',2);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-11-21  7:12:55
