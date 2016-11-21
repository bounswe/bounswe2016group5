-- MySQL dump 10.13  Distrib 5.7.12, for osx10.9 (x86_64)
--
-- Host: digest-db.c7pdwrhsbu6p.us-east-1.rds.amazonaws.com    Database: digest
-- ------------------------------------------------------
-- Server version	5.6.27-log

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
-- Table structure for table `choice`
--

DROP TABLE IF EXISTS `choice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `choice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` varchar(45) DEFAULT NULL,
  `isAnswer` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `choice`
--

LOCK TABLES `choice` WRITE;
/*!40000 ALTER TABLE `choice` DISABLE KEYS */;
INSERT INTO `choice` VALUES (36,'cevap',1),(37,'cvp',1),(38,'huloo',0),(39,'asd',0),(40,'hulllloooo',1),(41,'cevap',1),(42,'cvp',1),(43,'huloo',0),(44,'asd',0),(45,'hulllloooo',1),(46,'o1',1),(47,'o2',0),(48,'o1',1),(49,'o2',0),(50,'o1',1),(51,'o2',0),(52,'o1',1),(53,'o2',0);
/*!40000 ALTER TABLE `choice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `body` varchar(300) DEFAULT NULL,
  `uid` int(11) DEFAULT NULL,
  `tid` int(11) DEFAULT NULL,
  `ucid` int(11) DEFAULT NULL,
  `rate` int(11) DEFAULT NULL,
  `timestamp` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` varchar(2048) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (15,'sorucuk?'),(16,'sorucuk2'),(17,'sorucuk?'),(18,'sorucuk2'),(19,'q1'),(20,'q1'),(21,'q1'),(22,'q1');
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question_choice`
--

DROP TABLE IF EXISTS `question_choice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `question_choice` (
  `qid` int(11) NOT NULL,
  `cid` int(11) NOT NULL,
  PRIMARY KEY (`qid`,`cid`),
  KEY `fk_question_choice_2_idx` (`cid`),
  CONSTRAINT `fk_question_choice_1` FOREIGN KEY (`qid`) REFERENCES `question` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_question_choice_2` FOREIGN KEY (`cid`) REFERENCES `choice` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question_choice`
--

LOCK TABLES `question_choice` WRITE;
/*!40000 ALTER TABLE `question_choice` DISABLE KEYS */;
INSERT INTO `question_choice` VALUES (15,36),(15,37),(15,38),(16,39),(16,40),(17,41),(17,42),(17,43),(18,44),(18,45),(19,46),(19,47),(20,48),(20,49),(21,50),(21,51),(22,52),(22,53);
/*!40000 ALTER TABLE `question_choice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quiz`
--

DROP TABLE IF EXISTS `quiz`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quiz` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quiz`
--

LOCK TABLES `quiz` WRITE;
/*!40000 ALTER TABLE `quiz` DISABLE KEYS */;
INSERT INTO `quiz` VALUES (2,'ilk'),(3,'ilk'),(4,'ilk'),(5,'ilk'),(6,'ilk'),(7,'ilk'),(8,'ilk'),(9,'ilk'),(10,'ilk'),(11,'quiz'),(12,'quiz'),(13,'quiz');
/*!40000 ALTER TABLE `quiz` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quiz_question`
--

DROP TABLE IF EXISTS `quiz_question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quiz_question` (
  `quiz_id` int(11) NOT NULL,
  `question_id` int(11) NOT NULL,
  PRIMARY KEY (`quiz_id`,`question_id`),
  KEY `fk_quiz_question_2_idx` (`question_id`),
  CONSTRAINT `fk_quiz_question_1` FOREIGN KEY (`quiz_id`) REFERENCES `quiz` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_quiz_question_2` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quiz_question`
--

LOCK TABLES `quiz_question` WRITE;
/*!40000 ALTER TABLE `quiz_question` DISABLE KEYS */;
INSERT INTO `quiz_question` VALUES (9,15),(9,16),(10,17),(10,18),(11,19),(13,20),(13,21),(12,22);
/*!40000 ALTER TABLE `quiz_question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'admin'),(2,'user');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `topic`
--

DROP TABLE IF EXISTS `topic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `topic` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `header` varchar(90) DEFAULT NULL,
  `image` varchar(120) DEFAULT NULL,
  `url` varchar(120) DEFAULT NULL,
  `body` text,
  `owner` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_owner_idx` (`owner`),
  CONSTRAINT `fk_owner` FOREIGN KEY (`owner`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `topic`
--

LOCK TABLES `topic` WRITE;
/*!40000 ALTER TABLE `topic` DISABLE KEYS */;
INSERT INTO `topic` VALUES (2,NULL,'url1','url2','sometext',25,0),(3,'topic1','url1','url2','sometext',25,0),(4,'topic4','url1231','url2322','sometext',25,0),(5,'topic4','url1231','url2322','sometext',25,0),(6,'topic4','url1231','url2322','sometext',25,0),(7,'topic4','url1231','url232asdasaasda2','sometext',25,0),(8,'topic4','url1231','url232asdasaasda2','sometext',25,0),(9,'topic444234','urlsfsfd1231','url2322','sometext',25,0),(10,'de','asd',NULL,'asd',4,0),(11,'header','url',NULL,'bodybody',4,0),(12,'denemeheader','urldeneme',NULL,'bodydeneme',4,0),(13,'topicheader','url2','','bodybody',4,0),(14,'topicheader2','url','','body',4,0),(15,'header3','url4','','body4',4,0),(16,'header5','url','','body5',4,0);
/*!40000 ALTER TABLE `topic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `topic_manager`
--

DROP TABLE IF EXISTS `topic_manager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `topic_manager` (
  `id` int(11) NOT NULL,
  `uid` int(11) DEFAULT NULL,
  `tid` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_topic_manager_idx` (`uid`),
  KEY `fk_topic_manager_1_idx` (`tid`),
  CONSTRAINT `fk_topic_manager` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_topic_manager_1` FOREIGN KEY (`tid`) REFERENCES `topic` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `topic_manager`
--

LOCK TABLES `topic_manager` WRITE;
/*!40000 ALTER TABLE `topic_manager` DISABLE KEYS */;
/*!40000 ALTER TABLE `topic_manager` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `topic_quiz`
--

DROP TABLE IF EXISTS `topic_quiz`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `topic_quiz` (
  `tid` int(11) NOT NULL,
  `qid` int(11) NOT NULL,
  PRIMARY KEY (`tid`,`qid`),
  KEY `fk_topic_quiz_1_idx` (`qid`),
  KEY `fk_topic_quiz_2_idx` (`tid`),
  CONSTRAINT `fk_topic_quiz_1` FOREIGN KEY (`qid`) REFERENCES `quiz` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_topic_quiz_2` FOREIGN KEY (`tid`) REFERENCES `topic` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `topic_quiz`
--

LOCK TABLES `topic_quiz` WRITE;
/*!40000 ALTER TABLE `topic_quiz` DISABLE KEYS */;
INSERT INTO `topic_quiz` VALUES (8,10),(2,11),(2,12),(2,13);
/*!40000 ALTER TABLE `topic_quiz` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `topic_tag`
--

DROP TABLE IF EXISTS `topic_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `topic_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tid` int(11) DEFAULT NULL,
  `tag` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_topic_id_idx` (`tid`),
  CONSTRAINT `fk_topic_id` FOREIGN KEY (`tid`) REFERENCES `topic` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `topic_tag`
--

LOCK TABLES `topic_tag` WRITE;
/*!40000 ALTER TABLE `topic_tag` DISABLE KEYS */;
INSERT INTO `topic_tag` VALUES (1,16,'a'),(2,16,'b'),(3,16,'c');
/*!40000 ALTER TABLE `topic_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `rid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `fk_rid_idx` (`rid`),
  CONSTRAINT `fk_rid` FOREIGN KEY (`rid`) REFERENCES `role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'kerimgokarslan','7c4a8d09ca3762af61e59520943dc26494f8941b','kerimgokarslan@gmail.com','kerim','gokarslan',0,1),(2,'kerimgokarslan2','7c4a8d09ca3762af61e59520943dc26494f8941b','kerimgokarslan@hotmail.com','kerim','gokarslan',0,2),(3,'kerimgokarslan4','7c4a8d09ca3762af61e59520943dc26494f8941b','kerimgokarslan4@asd.com','kerim','gokarslan',0,2),(4,'atakanguney','1234','deneme','atakan','guney',0,2),(5,'seymaertem','12345','seyma@mail.com','seyma','ertem',0,2),(6,'seyma','123456','deneme2','seyma2','ertem',0,2),(7,'','','','','',0,2),(25,'atakang','12345678','a@a.com','atakan','guney',0,2),(26,'user1','12345678','user@user.com','user1','last1',0,2),(27,'user2','12345678','user2@user2.com','user2','last2',0,2),(28,'aaa','12345678','aaa@aaa.com','atakan','guney',0,2),(31,'cihatbaktir','12345678','cihat@aa.com','cihat','baktir',0,2),(32,'android','1234','asdf@asdf.com','android','android',1,2),(36,'seyma7','77777777','seyma.ertem@hotmail.com','Seyma','Ertem',0,2),(39,'user','12345678','user@hotmail.com','user','user',0,2),(40,'Oyku','12345','oyku@oyku.com','Oyku','Oyku',0,2),(41,'Sdvm','12345','sdvm@sdf.com','Sdvm','Sdvm',0,2),(42,'wert','12345','wert@wert.com','wert','wert',0,2),(43,'Wert2','12345','wer@wer.com','Wert','Wert',0,2),(44,'Wert3','12345','wert3@wert.com','Wert3','Wert3',0,2),(45,'Wert4','12345','wert4@asd.com','Wert','Wert',0,2),(46,'\"a\"','\"1\"','a@a','\"a\"','\"b\"',0,2),(49,'Wert5','12345','wert5@wert.com','Wert5','Wert',0,2),(50,'Sahin','1234','sahin@batmaz.com','Sahin','Batmaz',0,2);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_session`
--

DROP TABLE IF EXISTS `user_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_session` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL,
  `sid` varchar(45) DEFAULT NULL,
  `duration` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=218 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_session`
--

LOCK TABLES `user_session` WRITE;
/*!40000 ALTER TABLE `user_session` DISABLE KEYS */;
INSERT INTO `user_session` VALUES (1,1,'71d15aebf36a2c7c',0),(2,1,'32cc93bbf77966b2',0),(3,1,'2660760fae7a9bf0',0),(4,1,'ec4a67a3183994fe',0),(5,1,'6315aefeb5538685',0),(6,1,'f32d43a5a46f5e64',0),(7,1,'375cb108bd30f367',0),(8,4,'96e9639f42b38d95',0),(9,4,'dfe8147c67e7c574',0),(10,4,'cb0854d0a62b2d22',0),(11,4,'c65d3bb8317272d0',0),(12,4,'95612ca658a3923a',0),(13,4,'23311ab05fd11ae4',0),(14,4,'adfcefe8bec4b9a7',0),(15,6,'6de8e5c0af55f365',0),(16,5,'88d4ae6096cbd229',0),(17,4,'1ff115cf39e23120',0),(18,4,'56281d0d751f528d',0),(19,4,'121b8a68a839b1ca',0),(20,4,'83166ca6a734df43',0),(21,4,'e8f87306e5be7335',0),(22,4,'8fcb8ec7f6373b65',0),(23,4,'469befa7cdf810fd',0),(24,4,'61a55bcbfd62b22a',0),(25,4,'95ce272d6940e012',0),(26,4,'a80cab2acf7f13aa',0),(27,4,'9b01b3e3c96f57d4',0),(28,4,'db3a886720b8cc13',0),(29,4,'d3ed1050d06ea947',0),(30,4,'76207514a4d11166',0),(31,4,'cd2c68b696e2f9c2',0),(32,4,'c6e7eafe48e84828',0),(33,4,'2ab9ad8188b32d08',0),(34,4,'eaccdea9c4e490a7',0),(35,4,'780e38b1e16a4663',0),(36,4,'1c368a1662e63ba7',0),(37,4,'4d7b89ccef94fe80',0),(38,4,'3debae031e231472',0),(39,4,'7e72ad6b5a6d4421',0),(40,4,'590710f453af2a01',0),(41,4,'eee28f08428d1a5f',0),(42,4,'a53a7b3fbe96b16b',0),(43,4,'fb878741ea40e099',0),(44,25,'260573264c0352f9',0),(45,26,'4f15df005228cdfc',0),(46,7,'11e55b04ed5aa982',0),(47,7,'941769cc670eefc8',0),(48,7,'24f478fd7feb5e3e',0),(49,7,'10f7fef01526ec09',0),(50,7,'a295a45442cd7bbe',0),(51,7,'9c967a32912d3c97',0),(52,27,'2045df384279246a',0),(53,4,'c57b669c6356db87',0),(54,4,'21536a2e1c476f61',0),(55,4,'5d0c65cee0ad68bf',0),(56,4,'791b831b233dc01a',0),(57,4,'1e7946041fbe95c6',0),(58,28,'f479fdb617479639',0),(59,4,'80f5ebadf58163bf',0),(60,4,'100a63cf5cc480d9',0),(61,4,'79bf55986d65b536',0),(62,4,'ba729350d9942e48',0),(63,4,'f6e2d0045737e01e',0),(64,26,'c576cffe3d59d0c5',0),(65,4,'4cb69115d04bc43b',0),(66,4,'2900400e85507a86',0),(67,4,'371a16aab30fd18a',0),(68,4,'4bd18ad87f35868e',0),(69,4,'1c0ba94271a083b1',0),(70,4,'3d14266edae0fc1b',0),(71,4,'58ac8895c18f384f',0),(72,4,'d70e6b8c1e001b26',0),(73,4,'e56219dded453860',0),(74,4,'1ecfa1dc336adca3',0),(75,4,'73e42d69d69be587',0),(76,4,'bec22cc619cae314',0),(77,4,'67da1b8e375ba36f',0),(78,4,'8a71f54cb8abf3bc',0),(79,4,'4e144bdb7b9b27bc',0),(80,4,'594bff8f5429c3df',0),(81,4,'aab16d4bd35b3cee',0),(82,4,'55f78eb1e8781b35',0),(83,4,'2e12702e51a2effa',0),(84,4,'14807e874e82969e',0),(85,4,'55e36849ee00e7ea',0),(86,4,'2ddd3418cae31e43',0),(87,4,'64859a37e2f59b43',0),(88,4,'4e5c5bcc1c9c8621',0),(89,4,'b42557a0b0706427',0),(90,4,'1c28c0b3d906d450',0),(91,4,'c1de252785528bcc',0),(92,4,'d4e3b0fae741cd2f',0),(93,4,'79955e5170ac353b',0),(94,4,'98037a33fbfdaa09',0),(95,4,'6f27edaeb38bfae6',0),(96,4,'8a5baac65455fee0',0),(97,4,'a6c298493f6e1957',0),(98,4,'a2796c8229e02466',0),(99,4,'20564987e5329b84',0),(100,31,'509a1a88d9bd9d09',0),(101,32,'1c01360fd1a2aa97',0),(102,32,'94c9527537c02c76',0),(103,32,'2b9d1fa4d8fc2af7',0),(104,32,'f9f56f3beba06f7d',0),(105,32,'375e192a76387063',0),(106,32,'6bfea112adb0fbf9',0),(107,32,'77f6f5c13074892c',0),(108,32,'b5c431f5cca0e50e',0),(109,32,'7f66cf8b8176536d',0),(110,32,'4ba245fd5ac2229a',0),(111,32,'2ec3774411f5bc7a',0),(112,32,'4170d2c8c44ebc4a',0),(113,32,'76c92ef31530c45c',0),(114,32,'fbec773cfc25df70',0),(115,4,'25fb8638940ac06a',0),(116,4,'e2149e1efc9445a8',0),(117,4,'33a83d18aa4b19b1',0),(118,32,'a8745e4f12499cbf',0),(119,32,'919e94c8128ef45f',0),(120,36,'58efa0f6720fd985',0),(121,36,'cfec8f53804d718b',0),(122,36,'71b93d97fb95a414',0),(123,36,'575f6bd318d1c2c6',0),(124,36,'b74f9fe3fde0b235',0),(125,36,'184a16f39cf414fd',0),(126,36,'350917d45f1ddfd7',0),(127,36,'5333c5dad9f3e2d1',0),(128,36,'ec45af61cf987470',0),(129,36,'90219641f560f8a7',0),(130,36,'2bf4511ad0f48eca',0),(131,4,'bb56626eb45d1830',0),(132,4,'284ac90a2b61224d',0),(133,4,'19772a9fe289174a',0),(134,4,'a43a4d7ef570f9f8',0),(135,4,'54e1203f8d76c5d0',0),(136,4,'7ce9b2fcae71a3ae',0),(137,4,'5fa162318db6822f',0),(138,4,'d6aff8927e5bc741',0),(139,4,'b30a78dff1a0fe61',0),(140,36,'baca1fc3204f81e5',0),(141,36,'23b1bd48cdd0ba07',0),(142,36,'436e3cee5eff1d96',0),(143,36,'b3ec14333fe13a3e',0),(144,36,'31b9d9cdf95a7e28',0),(145,4,'23c4c4b1f285432f',0),(146,36,'968af3d0fb2617d2',0),(147,32,'fcbebe44dc4fbcb4',0),(148,32,'8413fb399fc5f23a',0),(149,32,'725c547fcda2f79b',0),(150,32,'3007146b4242f27d',0),(151,40,'7ac79900346f23bc',0),(152,36,'ac71949a5b5f7939',0),(153,32,'654ae02f75ca22f1',0),(154,32,'fe9c9a1da8ec29bd',0),(155,36,'f104f62344d7c91d',0),(156,32,'cbf5b2725485527a',0),(157,32,'2b6b9e8dfd22232e',0),(158,42,'283f2f7f70404548',0),(159,32,'5370476a19ece11d',0),(160,32,'337840951a8f5434',0),(161,32,'c9c0f297aa6e98ff',0),(162,32,'a358378486e51047',0),(163,32,'b76561373842e919',0),(164,44,'754d44ebbaae4b22',0),(165,32,'a5e37f5b440e228a',0),(166,32,'59f09ff74e70b0d8',0),(167,32,'1a02b46b763a23f8',0),(168,32,'813635ea4ea0ac72',0),(169,4,'e9212e95b88e7cac',0),(170,4,'ea4e9c09622fd631',0),(171,32,'7b302b59d52ddaed',0),(172,4,'8d4a5cafe88f6a5d',0),(173,32,'ee50d96e7c7dcc0e',0),(174,36,'fe59d64f824bee59',0),(175,36,'ead7bfa83022fc87',0),(176,4,'127a3e0e72a9bf73',0),(177,36,'863c12212c7aa895',0),(178,36,'61e571213c124c10',0),(179,36,'11fbb7c964b6d80e',0),(180,36,'abc5b857cfb844e8',0),(181,36,'d4372a418d6ffa50',0),(182,36,'30de78d2997b345a',0),(183,36,'eb1574ca59502c36',0),(184,36,'aff14ea89c4becec',0),(185,36,'8bf8d58586545b4e',0),(186,36,'5821712af50077cf',0),(187,36,'17884543262cf87d',0),(188,4,'484e75dad6fc6dae',0),(189,4,'4262abad973d7c80',0),(190,4,'afcf889cf736dba5',0),(191,4,'6d7488797cd93fa1',0),(192,32,'81e7b8d57afc845c',0),(193,32,'1cd3867069ad8428',0),(194,32,'dc12fccb84f1e211',0),(195,32,'43d18f5b953036c2',0),(196,32,'193d5c39ee09a61e',0),(197,4,'558795709bc386c3',0),(198,4,'bacfa7a8f0ffa132',0),(199,4,'51c4d0fb21a685ba',0),(200,4,'1ed92a8a48b7a9da',0),(201,4,'dba5ff0cdbee114a',0),(202,4,'c96c60d18c2d720c',0),(203,4,'721bf827cf70168d',0),(204,4,'b65c5f1047eb923a',0),(205,32,'7ad26c8658b482ff',0),(206,32,'e5ebf287bb33d4c3',0),(207,4,'dc1737d667db41f5',0),(208,4,'99b2a12882b5f0be',0),(209,36,'48bbf09931d7a39b',0),(210,36,'428ad2d76ade9f15',0),(211,4,'ea74b7aca044465c',0),(212,4,'47078563b03fbe8b',0),(213,4,'f9d7ace571a1dc78',0),(214,4,'b78176c281d28d25',0),(215,36,'e33559ea5380ca23',0),(216,36,'50b58b6b66ce11de',0),(217,4,'5920c79a886174df',0);
/*!40000 ALTER TABLE `user_session` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-11-21 23:24:06
