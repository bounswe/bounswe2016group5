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
) ENGINE=InnoDB AUTO_INCREMENT=120 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `choice`
--

LOCK TABLES `choice` WRITE;
/*!40000 ALTER TABLE `choice` DISABLE KEYS */;
INSERT INTO `choice` VALUES (54,'cevap1',1),(55,'cvp1',1),(56,'huloo1',0),(57,'asd2',0),(58,'hulllloooo2',1),(59,'cevap2.1',1),(60,'cvp2.1',1),(61,'huloo2.1',0),(62,'asd2',0),(63,'hulllloooo2.2',1),(64,'cevap3.1',1),(65,'cvp3.1',1),(66,'hulo12o2.1',0),(67,'asd2',0),(68,'hulllloooo2.2',1),(69,'o1',1),(70,'o2',0),(71,'o1',1),(72,'o2',0),(73,'o1',1),(74,'o2',0),(75,'o8',0),(76,'o9',0),(77,'o8',0),(78,'o9',0),(79,'o8',0),(80,'o9',0),(81,'o8',0),(82,'o9',0),(83,'o8',0),(84,'o9',0),(85,'o8',0),(86,'o9',0),(87,'o8',0),(88,'o9',0),(89,'o8',1),(90,'o9',0),(91,'o1',0),(92,'o2',0),(93,'o6',1),(94,'o6',1),(95,'o1',1),(96,'o2',1),(97,'o1',1),(98,'o2',0),(99,'o2',1),(100,'o2',1),(101,'das',1),(102,'csds',1),(103,'o1',1),(104,'o1',1),(105,'o2',0),(106,'o2',1),(107,'o4',1),(108,'o5',0),(109,'sad',1),(110,'dsc',1),(111,'csds',1),(112,'cs',0),(113,'o1',1),(114,'o2',0),(115,'o1',1),(116,'o2',0),(117,'o3',0),(118,'Wolverine',0),(119,'Not Wolverine - obviously',1);
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
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (1,'MAASALLAH OGLUM',9,8,-1,NULL,'2016-11-22 17:13:24'),(2,'asdsad',4,10,-1,0,'2016-11-23 16:07:40'),(3,'asd',4,38,-1,0,'2016-11-23 16:16:48'),(4,'asdsad',4,38,-1,0,'2016-11-23 16:17:36'),(5,'asdsad',4,38,-1,0,'2016-11-23 16:19:15'),(6,'heyyo comment',4,10,-1,0,'2016-11-23 17:01:14'),(7,'heyyo2',4,10,-1,0,'2016-11-23 17:02:25'),(8,'comment',4,10,-1,0,'2016-11-23 17:05:38'),(9,'remcds',4,10,-1,0,'2016-11-23 17:06:05'),(10,'remcdssdf',4,10,-1,0,'2016-11-23 17:08:14'),(11,'sdfsdfsd',4,10,-1,0,'2016-11-23 17:08:29'),(12,'sdfdsf',4,10,-1,0,'2016-11-23 17:10:34'),(13,'sdfdsf',4,10,-1,0,'2016-11-23 19:13:52'),(14,'fghgh',4,10,-1,0,'2016-11-23 19:18:24'),(15,'fghgh',4,10,-1,0,'2016-11-23 19:25:35'),(16,'hekdscsd',4,10,6,0,'2016-11-23 19:57:36'),(17,'cdssdcsdc',4,10,6,0,'2016-11-23 19:58:03'),(18,'comment1',36,18,-1,0,'2016-11-23 20:11:12'),(19,'comemasc',36,18,18,0,'2016-11-23 20:11:21'),(20,'cdscds',4,10,-1,0,'2016-11-23 20:21:35'),(21,'dscdcs',4,10,-1,0,'2016-11-23 20:22:57'),(22,'comment',4,37,-1,0,'2016-11-23 20:23:30'),(23,'ne',36,37,22,0,'2016-11-23 20:31:20'),(24,'comment',4,57,-1,0,'2016-11-23 20:34:46'),(25,'ne',36,57,24,0,'2016-11-23 20:35:36'),(26,'\"newwq\"',32,57,-1,0,'2016-11-23 20:59:49'),(27,'a b c',4,10,-1,0,'2016-11-23 21:03:23'),(28,'\"fuguy\"',32,57,-1,0,'2016-11-23 21:03:49'),(29,'\"tyui\"',32,57,-1,0,'2016-11-23 21:04:22'),(30,'gjft',32,58,-1,0,'2016-11-23 21:08:11'),(31,'fhhffvg',32,58,-1,0,'2016-11-23 21:08:15'),(32,'fjvyu fucy',32,58,-1,0,'2016-11-23 21:11:28'),(33,'etcu gfuu dycg',32,58,-1,0,'2016-11-23 21:11:48'),(34,'dify guf syd',32,58,-1,0,'2016-11-23 21:12:15'),(35,'a b c f',4,10,-1,0,'2016-11-23 21:19:22'),(36,'a b c f',4,10,-1,0,'2016-11-23 21:20:55'),(37,'bu yotun ricy',32,59,-1,0,'2016-11-23 21:27:35'),(38,'fjf fjd',32,59,-1,0,'2016-11-23 21:40:47'),(39,'dhg gch',32,59,-1,0,'2016-11-23 23:12:54'),(40,'sucyy hh',32,59,-1,0,'2016-11-23 23:14:47'),(41,'ciovu iyo',32,59,-1,0,'2016-11-23 23:17:45'),(42,'newww',32,59,-1,0,'2016-11-23 23:18:03'),(43,'dvuvf',32,57,-1,0,'2016-11-23 23:19:59'),(44,'gooood god',32,59,-1,0,'2016-11-23 23:24:45'),(45,'jkjnkj',4,63,-1,0,'2016-11-23 23:25:02'),(46,'hjbh  hhh',4,63,-1,0,'2016-11-23 23:25:08'),(47,'hjbh  hhh',4,63,-1,0,'2016-11-23 23:34:01'),(48,'Cute cat!!',32,65,-1,0,'2016-11-23 23:35:48'),(49,'merhaba',36,67,-1,0,'2016-11-23 23:37:14'),(50,'hello',4,67,49,0,'2016-11-23 23:38:23'),(51,'her grupta fener topic\'i var sanirim :D',32,67,-1,0,'2016-11-23 23:38:43'),(52,'who is she?',32,64,-1,0,'2016-11-23 23:39:10'),(53,'yeah, I have a lot of time and I\'m making comments to every topic',32,64,-1,0,'2016-11-23 23:39:45'),(54,'Yarin sunum var sunum!',32,59,-1,0,'2016-11-23 23:40:35'),(55,'iste bu ilk goz agrimiz, ilk resmimizi burda kullandik',32,57,-1,0,'2016-11-23 23:41:38'),(56,'Comment dememe',4,11,-1,0,'2016-11-24 08:50:23'),(57,'Superman!',4,72,-1,0,'2016-11-24 08:54:05'),(58,'spiderman benim en sevdigim super kahraman, trol degil cidden en sevdigim',32,69,-1,0,'2016-11-24 08:57:42'),(59,'Oha artik, ne salak salak sorular bunlar',32,68,-1,0,'2016-11-24 08:58:32'),(60,':)',63,69,58,0,'2016-11-24 09:03:05'),(61,'Hello little puppy',4,75,-1,0,'2016-11-24 09:19:37'),(62,'Seninle cok gurur duyuyorum halam. Allak muvaffak etsin. Basarilarinin devamini diliyorum. Annengillere selam',36,75,-1,0,'2016-11-24 09:21:35'),(63,'I LOVE YOU JEAN!!!',63,75,61,0,'2016-11-24 09:22:22'),(64,'I LOVE YOU JEAN!!',63,75,-1,0,'2016-11-24 09:22:38'),(65,'It is so little cute cat. I can\'t stop looking to the picture.',32,77,-1,0,'2016-11-24 09:28:56'),(66,'CANIM YEGENIM BENIM MAASALLAH!!',64,75,62,0,'2016-11-24 09:30:40'),(67,'We love you Bugra',64,75,64,0,'2016-11-24 09:31:21'),(68,'Benim commentim neden siliniyor, yorumlarim degersiz mi !!',32,80,-1,0,'2016-11-24 09:33:05'),(69,'Son filminde olucek mi ya wolverine :((',32,75,-1,0,'2016-11-24 09:35:37'),(70,'Of course spiderman .. ',32,72,-1,0,'2016-11-24 09:36:15'),(71,'Ben kimim bunu bilmezken neyi secebilirim ki ..',32,72,-1,0,'2016-11-24 09:41:30'),(72,'I WANT!!',60,81,-1,0,'2016-11-24 09:44:28'),(73,'Yesss, I have always wanted to learn this, thanks a lot Digest.',32,81,-1,0,'2016-11-24 09:45:02'),(74,'I\'m looking forward for it <3',32,66,-1,0,'2016-11-24 09:46:36');
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `media`
--

DROP TABLE IF EXISTS `media`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `media` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tid` int(11) DEFAULT NULL,
  `url` varchar(2048) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_media_1_idx` (`tid`),
  CONSTRAINT `fk_media_1` FOREIGN KEY (`tid`) REFERENCES `topic` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `media`
--

LOCK TABLES `media` WRITE;
/*!40000 ALTER TABLE `media` DISABLE KEYS */;
INSERT INTO `media` VALUES (14,75,'https://www.youtube.com/watch?v=jofNR_WkoCE');
/*!40000 ALTER TABLE `media` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (23,'sorucuk1.1'),(24,'sorucuk1.2'),(25,'sorucuk2.1'),(26,'sorucuk2.2'),(27,'sorucucuk2.1'),(28,'s2orucuk2.2'),(29,'q1'),(30,'q1'),(31,'q1'),(32,'q5'),(33,'q5'),(34,'q5'),(35,'q5'),(36,'q5'),(37,'q5'),(38,'q5'),(39,'q5'),(40,'q5'),(41,'q1'),(42,'q1'),(43,'q1'),(44,'q1'),(45,'q1'),(46,'q1'),(47,'qq1'),(48,'q1'),(49,'q1'),(50,'q1'),(51,'asd'),(52,'qf'),(53,'question1'),(54,'ques3'),(55,'Who howls at the fulmoon?');
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
INSERT INTO `question_choice` VALUES (23,54),(23,55),(23,56),(24,57),(24,58),(25,59),(25,60),(25,61),(26,62),(26,63),(27,64),(27,65),(27,66),(28,67),(28,68),(29,69),(29,70),(30,71),(30,72),(31,73),(31,74),(32,75),(32,76),(33,77),(33,78),(34,79),(34,80),(35,81),(35,82),(36,83),(36,84),(37,85),(37,86),(38,87),(38,88),(39,89),(39,90),(40,91),(40,92),(41,93),(42,94),(43,95),(43,96),(44,97),(44,98),(45,99),(46,100),(47,101),(47,102),(48,103),(49,104),(49,105),(50,106),(50,107),(50,108),(51,109),(52,110),(52,111),(52,112),(53,113),(53,114),(54,115),(54,116),(54,117),(55,118),(55,119);
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
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quiz`
--

LOCK TABLES `quiz` WRITE;
/*!40000 ALTER TABLE `quiz` DISABLE KEYS */;
INSERT INTO `quiz` VALUES (14,'ilk'),(15,'iki'),(16,'baskabaska'),(17,'quiz'),(18,'quiz'),(19,'quiz'),(20,'quiz'),(21,'quiz'),(22,'quiz'),(23,'quiz7'),(24,'quiz7'),(25,'quiz 6'),(26,'1'),(27,'11'),(28,'quiz'),(29,'csdc'),(30,'qqq'),(31,'qqqw'),(32,'uqzxc'),(33,'wqwq'),(34,'sdcsdc'),(35,'quiz6'),(36,'Wolverine\'s abilities');
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
INSERT INTO `quiz_question` VALUES (14,23),(14,24),(15,25),(15,26),(16,27),(16,28),(17,29),(18,30),(19,31),(20,32),(20,33),(21,34),(21,35),(21,36),(22,37),(22,38),(22,39),(22,40),(23,41),(24,42),(25,43),(26,44),(27,45),(28,46),(29,47),(30,48),(31,49),(32,50),(33,51),(34,52),(35,53),(35,54),(36,55);
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
  `body` text,
  `owner` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `timestamp` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_owner_idx` (`owner`),
  CONSTRAINT `fk_owner` FOREIGN KEY (`owner`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `topic`
--

LOCK TABLES `topic` WRITE;
/*!40000 ALTER TABLE `topic` DISABLE KEYS */;
INSERT INTO `topic` VALUES (64,'Why girls like Instagram?','https://pbs.twimg.com/profile_images/574984492110200833/gi-ATBU_.jpeg','Because Instagram is beatiful.',36,0,'2016-11-23 23:25:36'),(66,'Will the new movie of Wolverine succeed?','http://images.zaazu.com/img/Wolverine-Wolverine-X-men-Logan-smiley-emoticon-001162-facebook.gif','It will rock!',32,0,'2016-11-23 23:29:05'),(67,'Why Fenerbahce is one of the biggest sports club in Turkey?','https://pbs.twimg.com/profile_images/776427712600739840/GppYTpZP.jpg','bodybody',4,0,'2016-11-23 23:35:43'),(68,'Does drinking blood break the fast?','http://www.unfinishedman.com/wp-content/uploads/2010/07/Tru-Blood-Vampire-Drink-e1278477513282.jpg','yes',36,0,'2016-11-24 08:46:51'),(69,'Being a superhero without the tights','http://i.annihil.us/u/prod/marvel/i/mg/2/00/53710b14a320b.png','cool',36,0,'2016-11-24 08:48:15'),(70,'Drunk Animals','https://www.askideas.com/wp-content/uploads/2016/11/Funny-Animal-Face.jpg','lol',36,0,'2016-11-24 08:49:16'),(71,'Why kittens are so cute?','http://static.boredpanda.com/blog/wp-content/uploads/2016/08/cute-kittens-4-57b30a939dff5__605.jpg','cat',4,0,'2016-11-24 08:51:03'),(72,'Which superhero is the best?','https://s-media-cache-ak0.pinimg.com/originals/5b/18/3e/5b183eab5263e0ddadf46ce750932239.jpg','spiderman',4,0,'2016-11-24 08:51:51'),(75,'How Wolverine differs from wolves and you?','https://i.imgur.com/QG8tjaO.jpg','What common traits do wolves, dogs, werewolfves share? What WOlverine shares with them and Spiderman?\r\n\r\nStuck between being a superhero and being a member of animal kingdom, let\'s solve the mystery of Wolverine.',60,0,'2016-11-24 09:00:16'),(76,'Which superhero is the best?','https://s-media-cache-ak0.pinimg.com/originals/5b/18/3e/5b183eab5263e0ddadf46ce750932239.jpg','b',4,0,'2016-11-24 09:15:20'),(77,'Why kittens are sooooooo cute?','http://static.boredpanda.com/blog/wp-content/uploads/2016/08/cute-kittens-4-57b30a939dff5__605.jpg','cat',4,0,'2016-11-24 09:15:47'),(78,'Funny Drunk Crazy Animals!!!!','https://www.askideas.com/wp-content/uploads/2016/11/Funny-Animal-Face.jpg','d',4,0,'2016-11-24 09:16:31'),(79,'Being a superhero without the tights','http://i.annihil.us/u/prod/marvel/i/mg/2/00/53710b14a320b.png','s',4,0,'2016-11-24 09:16:57'),(80,'Does drinking blood break the fast?','http://www.unfinishedman.com/wp-content/uploads/2010/07/Tru-Blood-Vampire-Drink-e1278477513282.jpg','s',4,0,'2016-11-24 09:17:46'),(81,'Do you want to learn the art of pruning?','http://www.installitdirect.com/wp-content/uploads/2012/07/pruning-plants.gif','Pruning is a horticultural and silvicultural practice involving the selective removal of parts of a plant, such as branches, buds, or roots. Reasons to prune plants include deadwood removal, shaping (by controlling or directing growth), improving or maintaining health, reducing risk from falling branches, preparing nursery specimens for transplanting, and both harvesting and increasing the yield or quality of flowers and fruits. The practice entails targeted removal of diseased, damaged, dead, non-productive, structurally unsound, or otherwise unwanted tissue from crop and landscape plants. Specialized pruning practices may be applied to certain plants, such as roses, fruit trees, and grapevines. It is important when pruning that the treeâ??s limbs are kept intact, as this is what helps the tree stay upright.[1] Different pruning techniques may be deployed on herbaceous plants than those used on perennial woody plants. Hedges, by design, are usually (but not exclusively) maintained by hedge trimming, rather than by pruning.',36,0,'2016-11-24 09:30:53');
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
INSERT INTO `topic_quiz` VALUES (67,35),(75,36);
/*!40000 ALTER TABLE `topic_quiz` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `topic_subscribe`
--

DROP TABLE IF EXISTS `topic_subscribe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `topic_subscribe` (
  `tid` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  PRIMARY KEY (`tid`,`uid`),
  KEY `fk_user_topic_subscribe_idx` (`uid`),
  CONSTRAINT `fk_topic_topic_subscribe` FOREIGN KEY (`tid`) REFERENCES `topic` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_topic_subscribe` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `topic_subscribe`
--

LOCK TABLES `topic_subscribe` WRITE;
/*!40000 ALTER TABLE `topic_subscribe` DISABLE KEYS */;
INSERT INTO `topic_subscribe` VALUES (67,36),(78,60),(75,62);
/*!40000 ALTER TABLE `topic_subscribe` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `topic_tag`
--

LOCK TABLES `topic_tag` WRITE;
/*!40000 ALTER TABLE `topic_tag` DISABLE KEYS */;
INSERT INTO `topic_tag` VALUES (52,64,'tag'),(57,66,'film'),(58,66,'wolverine'),(59,66,'xmen'),(62,68,''),(63,69,''),(64,70,''),(65,71,''),(66,72,''),(69,75,'aintdog'),(70,75,'aintwolf'),(71,75,'whatami'),(72,76,''),(75,79,''),(76,80,''),(77,81,'');
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
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'kerimgokarslan1','7c4a8d09ca3762af61e59520943dc26494f8941b','kerimgokarslan1@gmail.com','kerim','gokarslan',0,1),(2,'kerimgokarslan2','7c4a8d09ca3762af61e59520943dc26494f8941b','kerimgokarslan@hotmail.com','kerim','gokarslan',0,2),(3,'kerimgokarslan4','7c4a8d09ca3762af61e59520943dc26494f8941b','kerimgokarslan4@asd.com','kerim','gokarslan',0,2),(4,'atakanguney','1234','deneme','atakan','guney',0,2),(5,'seymaertem','12345','seyma@mail.com','seyma','ertem',0,2),(6,'seyma','123456','deneme2','seyma2','ertem',0,2),(7,'','','','','',0,2),(25,'atakang','12345678','a@a.com','atakan','guney',0,2),(26,'user1','12345678','user@user.com','user1','last1',0,2),(27,'user2','12345678','user2@user2.com','user2','last2',0,2),(28,'aaa','12345678','aaa@aaa.com','atakan','guney',0,2),(31,'cihatbaktir','12345678','cihat@aa.com','cihat','baktir',0,2),(32,'android','1234','asdf@asdf.com','android','android',1,2),(36,'seyma7','77777777','seyma.ertem@hotmail.com','Seyma','Ertem',0,2),(39,'user','12345678','user@hotmail.com','user','user',0,2),(40,'Oyku','12345','oyku@oyku.com','Oyku','Oyku',0,2),(41,'Sdvm','12345','sdvm@sdf.com','Sdvm','Sdvm',0,2),(42,'wert','12345','wert@wert.com','wert','wert',0,2),(43,'Wert2','12345','wer@wer.com','Wert','Wert',0,2),(44,'Wert3','12345','wert3@wert.com','Wert3','Wert3',0,2),(45,'Wert4','12345','wert4@asd.com','Wert','Wert',0,2),(46,'\"a\"','\"1\"','a@a','\"a\"','\"b\"',0,2),(49,'Wert5','12345','wert5@wert.com','Wert5','Wert',0,2),(50,'Sahin','1234','sahin@batmaz.com','Sahin','Batmaz',0,2),(51,'Wer1','1234','wer1@wer.com','Wer','Wer',0,2),(52,'Wer4','1234','uuu@hh.com','Hdh','Hdjdj',0,2),(53,'Wer5','1234','h@j.com','Sdf','Djdjd',0,2),(54,'Wer7','1234','h@c.com','Ghh','Ghh',0,2),(55,'Wer8','1234','gg@c.com','Fgh','Ghj',0,2),(56,'Er1','1234','t@h.com','Eee','Rrr',0,2),(57,'Er2','1234','fd@h.com','Jdkd','Djd',0,2),(58,'Fff','1234','y@j.com','Dff','Ndkd',0,2),(59,'Ert1','1234','f@h.com','Fff','Fff',0,2),(60,'CRaZY_WoLVi','12341234','crazywolvi@digest.com','Wolverine','Xmen',0,2),(62,'kerimgokarslan','12345678','kerimgokarslan@gmail.com','Kerim','Gokarslan',0,2),(63,'mbugc','mustafa123','mustafa.bugra.cil@gmail.com','Bugra','Cil',0,2),(64,'WolverinesAunt','12341234','WolverinesAunt@gmail.coom','WolverinesAunt','WolverinesAunt',0,2);
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
) ENGINE=InnoDB AUTO_INCREMENT=428 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_session`
--

LOCK TABLES `user_session` WRITE;
/*!40000 ALTER TABLE `user_session` DISABLE KEYS */;
INSERT INTO `user_session` VALUES (1,1,'71d15aebf36a2c7c',0),(2,1,'32cc93bbf77966b2',0),(3,1,'2660760fae7a9bf0',0),(4,1,'ec4a67a3183994fe',0),(5,1,'6315aefeb5538685',0),(6,1,'f32d43a5a46f5e64',0),(7,1,'375cb108bd30f367',0),(8,4,'96e9639f42b38d95',0),(9,4,'dfe8147c67e7c574',0),(10,4,'cb0854d0a62b2d22',0),(11,4,'c65d3bb8317272d0',0),(12,4,'95612ca658a3923a',0),(13,4,'23311ab05fd11ae4',0),(14,4,'adfcefe8bec4b9a7',0),(15,6,'6de8e5c0af55f365',0),(16,5,'88d4ae6096cbd229',0),(17,4,'1ff115cf39e23120',0),(18,4,'56281d0d751f528d',0),(19,4,'121b8a68a839b1ca',0),(20,4,'83166ca6a734df43',0),(21,4,'e8f87306e5be7335',0),(22,4,'8fcb8ec7f6373b65',0),(23,4,'469befa7cdf810fd',0),(24,4,'61a55bcbfd62b22a',0),(25,4,'95ce272d6940e012',0),(26,4,'a80cab2acf7f13aa',0),(27,4,'9b01b3e3c96f57d4',0),(28,4,'db3a886720b8cc13',0),(29,4,'d3ed1050d06ea947',0),(30,4,'76207514a4d11166',0),(31,4,'cd2c68b696e2f9c2',0),(32,4,'c6e7eafe48e84828',0),(33,4,'2ab9ad8188b32d08',0),(34,4,'eaccdea9c4e490a7',0),(35,4,'780e38b1e16a4663',0),(36,4,'1c368a1662e63ba7',0),(37,4,'4d7b89ccef94fe80',0),(38,4,'3debae031e231472',0),(39,4,'7e72ad6b5a6d4421',0),(40,4,'590710f453af2a01',0),(41,4,'eee28f08428d1a5f',0),(42,4,'a53a7b3fbe96b16b',0),(43,4,'fb878741ea40e099',0),(44,25,'260573264c0352f9',0),(45,26,'4f15df005228cdfc',0),(46,7,'11e55b04ed5aa982',0),(47,7,'941769cc670eefc8',0),(48,7,'24f478fd7feb5e3e',0),(49,7,'10f7fef01526ec09',0),(50,7,'a295a45442cd7bbe',0),(51,7,'9c967a32912d3c97',0),(52,27,'2045df384279246a',0),(53,4,'c57b669c6356db87',0),(54,4,'21536a2e1c476f61',0),(55,4,'5d0c65cee0ad68bf',0),(56,4,'791b831b233dc01a',0),(57,4,'1e7946041fbe95c6',0),(58,28,'f479fdb617479639',0),(59,4,'80f5ebadf58163bf',0),(60,4,'100a63cf5cc480d9',0),(61,4,'79bf55986d65b536',0),(62,4,'ba729350d9942e48',0),(63,4,'f6e2d0045737e01e',0),(64,26,'c576cffe3d59d0c5',0),(65,4,'4cb69115d04bc43b',0),(66,4,'2900400e85507a86',0),(67,4,'371a16aab30fd18a',0),(68,4,'4bd18ad87f35868e',0),(69,4,'1c0ba94271a083b1',0),(70,4,'3d14266edae0fc1b',0),(71,4,'58ac8895c18f384f',0),(72,4,'d70e6b8c1e001b26',0),(73,4,'e56219dded453860',0),(74,4,'1ecfa1dc336adca3',0),(75,4,'73e42d69d69be587',0),(76,4,'bec22cc619cae314',0),(77,4,'67da1b8e375ba36f',0),(78,4,'8a71f54cb8abf3bc',0),(79,4,'4e144bdb7b9b27bc',0),(80,4,'594bff8f5429c3df',0),(81,4,'aab16d4bd35b3cee',0),(82,4,'55f78eb1e8781b35',0),(83,4,'2e12702e51a2effa',0),(84,4,'14807e874e82969e',0),(85,4,'55e36849ee00e7ea',0),(86,4,'2ddd3418cae31e43',0),(87,4,'64859a37e2f59b43',0),(88,4,'4e5c5bcc1c9c8621',0),(89,4,'b42557a0b0706427',0),(90,4,'1c28c0b3d906d450',0),(91,4,'c1de252785528bcc',0),(92,4,'d4e3b0fae741cd2f',0),(93,4,'79955e5170ac353b',0),(94,4,'98037a33fbfdaa09',0),(95,4,'6f27edaeb38bfae6',0),(96,4,'8a5baac65455fee0',0),(97,4,'a6c298493f6e1957',0),(98,4,'a2796c8229e02466',0),(99,4,'20564987e5329b84',0),(100,31,'509a1a88d9bd9d09',0),(101,32,'1c01360fd1a2aa97',0),(102,32,'94c9527537c02c76',0),(103,32,'2b9d1fa4d8fc2af7',0),(104,32,'f9f56f3beba06f7d',0),(105,32,'375e192a76387063',0),(106,32,'6bfea112adb0fbf9',0),(107,32,'77f6f5c13074892c',0),(108,32,'b5c431f5cca0e50e',0),(109,32,'7f66cf8b8176536d',0),(110,32,'4ba245fd5ac2229a',0),(111,32,'2ec3774411f5bc7a',0),(112,32,'4170d2c8c44ebc4a',0),(113,32,'76c92ef31530c45c',0),(114,32,'fbec773cfc25df70',0),(115,4,'25fb8638940ac06a',0),(116,4,'e2149e1efc9445a8',0),(117,4,'33a83d18aa4b19b1',0),(118,32,'a8745e4f12499cbf',0),(119,32,'919e94c8128ef45f',0),(120,36,'58efa0f6720fd985',0),(121,36,'cfec8f53804d718b',0),(122,36,'71b93d97fb95a414',0),(123,36,'575f6bd318d1c2c6',0),(124,36,'b74f9fe3fde0b235',0),(125,36,'184a16f39cf414fd',0),(126,36,'350917d45f1ddfd7',0),(127,36,'5333c5dad9f3e2d1',0),(128,36,'ec45af61cf987470',0),(129,36,'90219641f560f8a7',0),(130,36,'2bf4511ad0f48eca',0),(131,4,'bb56626eb45d1830',0),(132,4,'284ac90a2b61224d',0),(133,4,'19772a9fe289174a',0),(134,4,'a43a4d7ef570f9f8',0),(135,4,'54e1203f8d76c5d0',0),(136,4,'7ce9b2fcae71a3ae',0),(137,4,'5fa162318db6822f',0),(138,4,'d6aff8927e5bc741',0),(139,4,'b30a78dff1a0fe61',0),(140,36,'baca1fc3204f81e5',0),(141,36,'23b1bd48cdd0ba07',0),(142,36,'436e3cee5eff1d96',0),(143,36,'b3ec14333fe13a3e',0),(144,36,'31b9d9cdf95a7e28',0),(145,4,'23c4c4b1f285432f',0),(146,36,'968af3d0fb2617d2',0),(147,32,'fcbebe44dc4fbcb4',0),(148,32,'8413fb399fc5f23a',0),(149,32,'725c547fcda2f79b',0),(150,32,'3007146b4242f27d',0),(151,40,'7ac79900346f23bc',0),(152,36,'ac71949a5b5f7939',0),(153,32,'654ae02f75ca22f1',0),(154,32,'fe9c9a1da8ec29bd',0),(155,36,'f104f62344d7c91d',0),(156,32,'cbf5b2725485527a',0),(157,32,'2b6b9e8dfd22232e',0),(158,42,'283f2f7f70404548',0),(159,32,'5370476a19ece11d',0),(160,32,'337840951a8f5434',0),(161,32,'c9c0f297aa6e98ff',0),(162,32,'a358378486e51047',0),(163,32,'b76561373842e919',0),(164,44,'754d44ebbaae4b22',0),(165,32,'a5e37f5b440e228a',0),(166,32,'59f09ff74e70b0d8',0),(167,32,'1a02b46b763a23f8',0),(168,32,'813635ea4ea0ac72',0),(169,4,'e9212e95b88e7cac',0),(170,4,'ea4e9c09622fd631',0),(171,32,'7b302b59d52ddaed',0),(172,4,'8d4a5cafe88f6a5d',0),(173,32,'ee50d96e7c7dcc0e',0),(174,36,'fe59d64f824bee59',0),(175,36,'ead7bfa83022fc87',0),(176,4,'127a3e0e72a9bf73',0),(177,36,'863c12212c7aa895',0),(178,36,'61e571213c124c10',0),(179,36,'11fbb7c964b6d80e',0),(180,36,'abc5b857cfb844e8',0),(181,36,'d4372a418d6ffa50',0),(182,36,'30de78d2997b345a',0),(183,36,'eb1574ca59502c36',0),(184,36,'aff14ea89c4becec',0),(185,36,'8bf8d58586545b4e',0),(186,36,'5821712af50077cf',0),(187,36,'17884543262cf87d',0),(188,4,'484e75dad6fc6dae',0),(189,4,'4262abad973d7c80',0),(190,4,'afcf889cf736dba5',0),(191,4,'6d7488797cd93fa1',0),(192,32,'81e7b8d57afc845c',0),(193,32,'1cd3867069ad8428',0),(194,32,'dc12fccb84f1e211',0),(195,32,'43d18f5b953036c2',0),(196,32,'193d5c39ee09a61e',0),(197,4,'558795709bc386c3',0),(198,4,'bacfa7a8f0ffa132',0),(199,4,'51c4d0fb21a685ba',0),(200,4,'1ed92a8a48b7a9da',0),(201,4,'dba5ff0cdbee114a',0),(202,4,'c96c60d18c2d720c',0),(203,4,'721bf827cf70168d',0),(204,4,'b65c5f1047eb923a',0),(205,32,'7ad26c8658b482ff',0),(206,32,'e5ebf287bb33d4c3',0),(207,4,'dc1737d667db41f5',0),(208,4,'99b2a12882b5f0be',0),(209,36,'48bbf09931d7a39b',0),(210,36,'428ad2d76ade9f15',0),(211,4,'ea74b7aca044465c',0),(212,4,'47078563b03fbe8b',0),(213,4,'f9d7ace571a1dc78',0),(214,4,'b78176c281d28d25',0),(215,36,'e33559ea5380ca23',0),(216,36,'50b58b6b66ce11de',0),(217,4,'5920c79a886174df',0),(218,4,'37de8e9c774c9cd0',0),(219,4,'a7d2e71775707dc4',0),(220,4,'40f212962bd7ab2f',0),(221,4,'a5c64d92be98ac5c',0),(222,36,'c3855b15a3eeadbb',0),(223,36,'1f83f6bb9420f923',0),(224,36,'8c634207d222f035',0),(225,36,'bd29440c75c14fac',0),(226,4,'b0a9eff41ed8ec17',0),(227,36,'d9d9c6fc739aeea8',0),(228,36,'7f1951b91d0a4f24',0),(229,32,'f2e1a027609fadd4',0),(230,36,'f94c5632d3bc417a',0),(231,32,'bd96fb03b094e552',0),(232,4,'57f695affa2bbc3b',0),(233,36,'af8d9d5ef6a581f5',0),(234,36,'e2f92fb7284cdbb3',0),(235,4,'1d6160325201b53c',0),(236,36,'2721b7fd1b86e292',0),(237,36,'e3eb59f99ea7c626',0),(238,36,'860fb6438ffcdf5f',0),(239,32,'c1ce782ab4ed7774',0),(240,32,'ef86c97b7c34b815',0),(241,32,'421c9401cdcb13e8',0),(242,32,'e45dfd96195e72fc',0),(243,32,'fd097ad845a5ed17',0),(244,32,'848220faa653956e',0),(245,32,'3daf3358c4493e62',0),(246,36,'1b4ef6368bbee056',0),(247,36,'dd4da339eb46d3bc',0),(248,32,'c2998e31335c5099',0),(249,36,'725211ab2a3776ab',0),(250,36,'121ab7fe2270b9d3',0),(251,36,'ee719edc18e88391',0),(252,36,'6f63dd5755cca742',0),(253,36,'f35846aee73fa5e5',0),(254,4,'4707121db3284167',0),(255,32,'947f6792aa54b7f1',0),(256,32,'ee313c0650d3d16b',0),(257,36,'b65c82e8ddf43c67',0),(258,4,'7897d0d210cc6d61',0),(259,4,'4c5dfacb263a574e',0),(260,4,'ad565ab464a3e9f4',0),(261,4,'2b017fe48798af84',0),(262,4,'acaaf938306053e8',0),(263,36,'82127ebd3103dccd',0),(264,4,'581e4fe1d8319ec6',0),(265,36,'67336a12bfded2ca',0),(266,51,'cb4167e327d71861',0),(267,36,'42104fb1eb7178bd',0),(268,32,'d180a1ee9c83e6e6',0),(269,4,'3571f979ae72d460',0),(270,32,'bad73a51eb372a7d',0),(271,32,'f64e34438e13ab23',0),(272,32,'405d81c2e8d93131',0),(273,4,'39262e9bdbb84ead',0),(274,32,'4ecf4b63be8190aa',0),(275,32,'8e549e3579e792d1',0),(276,32,'9415b02bd74da5c6',0),(277,32,'df3d21f3f5a68ea5',0),(278,32,'bfeafdf4ce8c1a01',0),(279,32,'eacc7ab6c1707118',0),(280,36,'b9d582fed67ce6aa',0),(281,32,'871b5708ffbe6063',0),(282,36,'26d23a2adea7e036',0),(283,36,'f6fd21daee74dd01',0),(284,4,'cafcbcc9d8d46a50',0),(285,32,'bb5ce8f8bc06c1b2',0),(286,32,'97422fda6944c2ae',0),(287,32,'22656948a4088b40',0),(288,32,'5a91f166454a8a20',0),(289,32,'997558f32a154a63',0),(290,32,'55a1fdf9c65c33d6',0),(291,32,'cfb0a45dba1de910',0),(292,32,'3a6b2b0e537e3752',0),(293,36,'db3ab43abf516084',0),(294,32,'f7a6ac95191c5137',0),(295,32,'650982aab73ebb6a',0),(296,32,'82fbd01d74f949cb',0),(297,32,'b3e38578de18eeea',0),(298,52,'f25552d24e2171ec',0),(299,4,'781ba02b8bf28e47',0),(300,32,'1875b832b5e0ec3c',0),(301,32,'9d88b2799689108c',0),(302,32,'65287e819868e22a',0),(303,32,'15acab7e6a0ceadf',0),(304,32,'a0c2d2ee8c61985f',0),(305,32,'a45f876e7151159d',0),(306,32,'2eecfd0e116660a9',0),(307,32,'7ca06ff9775d1e13',0),(308,59,'32a1bc96d31a1a28',0),(309,32,'12e9c8466939e9a2',0),(310,36,'b2df564af5006a16',0),(311,32,'f31d9cb5f09a33bb',0),(312,4,'dbee7d92affde345',0),(313,32,'b18f885274afc720',0),(314,32,'266394417fe71ce8',0),(315,36,'aac2ead837a74328',0),(316,36,'6c9dd4a326c540f5',0),(317,32,'e9857f4b11e242a2',0),(318,36,'2fbb4863875dafc2',0),(319,36,'129a4ee41cb158e3',0),(320,32,'53345c5379b8cd97',0),(321,32,'9443b56d9a79c081',0),(322,4,'f12a42eebc8ca2d8',0),(323,4,'896f12c352b58db9',0),(324,32,'1c26c7ae27d51a13',0),(325,32,'71e4a225475c7635',0),(326,32,'aff2b1433cdd2efe',0),(327,32,'52fd84b82b1a9637',0),(328,32,'4ea99be47946f94c',0),(329,32,'79167d14752ec888',0),(330,32,'6c5c81d9f201e099',0),(331,32,'a36d9b7c735e234c',0),(332,32,'72aa34ca23f8ca9f',0),(333,32,'dfa95a635131c9c0',0),(334,32,'f4171e1f93029746',0),(335,32,'482d6c2ae81ef529',0),(336,32,'4c204e60e39e2598',0),(337,32,'c2fcd751d28cf3a8',0),(338,32,'e1602445e5d8e37e',0),(339,32,'1b4a66f84049f0fb',0),(340,32,'e249a220544e1601',0),(341,32,'2d70462f185df302',0),(342,32,'e80f5421e23e65ee',0),(343,32,'1d4737475eaf6d06',0),(344,32,'93241397890eec81',0),(345,32,'bc5fa98590332044',0),(346,32,'2bd04512f6c9cb49',0),(347,32,'479cba924f97d384',0),(348,32,'92034b1fc474a777',0),(349,32,'146721b6824b35bc',0),(350,32,'73e86a9c3ccf60e5',0),(351,32,'755769234850f124',0),(352,32,'627e3c66612061f4',0),(353,32,'dd7e98f2d08e73ae',0),(354,32,'3f7a90d04711136b',0),(355,32,'6c9cca2a4e061d6e',0),(356,32,'cc9957dd8b7875ef',0),(357,32,'d7aaa1e27cbb3cb4',0),(358,32,'6db534c969e345fe',0),(359,32,'4bf7bc5b9b097a4a',0),(360,32,'e05a403c4eed9243',0),(361,32,'730dc1a1f4c66eea',0),(362,32,'a04da903987532e8',0),(363,32,'7714cbcba82b129c',0),(364,32,'16d6729ddc834ada',0),(365,32,'12ae9f2b7be1e741',0),(366,32,'4c45d54f2fe49d5c',0),(367,32,'b32350ed8c67c378',0),(368,32,'b2caecdff6aeded1',0),(369,36,'35cd354ac77d4442',0),(370,4,'a11b478afa8b9a4f',0),(371,32,'9a459c51f1c8cf16',0),(372,32,'bb78d3595ff5572b',0),(373,32,'2a62399c929dde93',0),(374,32,'31db3ea0367110f5',0),(375,32,'ff3db308a6e6fb72',0),(376,32,'1e297864bae9d2dc',0),(377,32,'54fcb7e189bcecb7',0),(378,32,'dd9d8ab49144ec37',0),(379,32,'2f30b87bb0cfb8e6',0),(380,32,'338d49b91b2c2b1f',0),(381,32,'c277aa1cb00fe8f3',0),(382,32,'15a68f1f68f45c3d',0),(383,32,'768f4e404b145d6c',0),(384,32,'7c5718146d3029a9',0),(385,32,'c14472fe35137d75',0),(386,4,'1a91e0ec71ce347f',0),(387,32,'f90bdd63a64cc48e',0),(388,32,'ed75572d54cb5c1c',0),(389,32,'7d6cd2d48c6bf0a9',0),(390,32,'cb59d9bdf4407edb',0),(391,32,'44df98e1aafb9cb4',0),(392,4,'7f74e1e98a4dc7ad',0),(393,32,'958a9a05555727c5',0),(394,36,'c5e08fee211e6f76',0),(395,36,'f650e7ad96a68c6b',0),(396,32,'ec9e999f886c4f0c',0),(397,32,'bc1d27df167c755d',0),(398,4,'6fa781d64511777e',0),(399,4,'ddd1789675249a2f',0),(400,32,'59235263413fc6b7',0),(401,60,'810588ddde181841',0),(402,4,'78aaaffa81efa01d',0),(403,36,'645c9643ad43e908',0),(404,63,'d1a398655bfb9d67',0),(405,62,'2c5c64f6c116ac77',0),(406,4,'ad6b355e6a8cf24d',0),(407,32,'8077a5ba6717f825',0),(408,62,'f84a92a25ddfe642',0),(409,36,'719560ce662c7c5e',0),(410,32,'99d4dd0563e1b739',0),(411,32,'ededf12479957b77',0),(412,64,'2299834571d8f677',0),(413,62,'fbd9b7b33855a9b7',0),(414,36,'30fb662c18af4d17',0),(415,32,'4fdc8f41a8681c83',0),(416,60,'fa7c6403568cdf89',0),(417,32,'6b2185095ba05742',0),(418,32,'561b18142267c259',0),(419,32,'92189760373c2d2c',0),(420,60,'8b9fc583204adda6',0),(421,32,'59fff503d07b2d9f',0),(422,32,'4b7af10fe04d1784',0),(423,32,'23f1fbcf934f8f63',0),(424,32,'3b31c456e49023f8',0),(425,32,'daac1dbe9104ab21',0),(426,4,'5ed09bfaa9b7e6e0',0),(427,4,'957199dd64b78ef5',0);
/*!40000 ALTER TABLE `user_session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'digest'
--

--
-- Dumping routines for database 'digest'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-11-24 12:48:33
