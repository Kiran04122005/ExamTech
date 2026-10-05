-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: localhost    Database: examtech_db
-- ------------------------------------------------------
-- Server version	8.0.46

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `exam_answers`
--

DROP TABLE IF EXISTS `exam_answers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exam_answers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `feedback` text,
  `marks_awarded` int DEFAULT NULL,
  `student_answer` text,
  `student_name` varchar(255) DEFAULT NULL,
  `exam_result_id` bigint DEFAULT NULL,
  `question_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKral4toyh8ehui74wn0h1b310n` (`exam_result_id`),
  KEY `FK58jpsqvhuexpjnuf5awfaqk9g` (`question_id`),
  CONSTRAINT `FK58jpsqvhuexpjnuf5awfaqk9g` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`),
  CONSTRAINT `FKral4toyh8ehui74wn0h1b310n` FOREIGN KEY (`exam_result_id`) REFERENCES `exam_results` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exam_answers`
--

LOCK TABLES `exam_answers` WRITE;
/*!40000 ALTER TABLE `exam_answers` DISABLE KEYS */;
INSERT INTO `exam_answers` VALUES (1,'Automatically evaluated.',2,'B','APPIKONDA BHAGYAKIRAN',2,1),(2,'Pending examiner evaluation.',0,'The main() method is the starting point of execution in a Java program.','APPIKONDA BHAGYAKIRAN',2,2),(3,'Pending examiner evaluation.',0,'The main features of OOP in Java are encapsulation, inheritance, polymorphism and abstraction.','APPIKONDA BHAGYAKIRAN',2,3),(4,'Automatically evaluated.',2,'B','APPIKONDA BHAGYAKIRAN',3,1),(5,'Good answer. Clearly explains the purpose of the main() method.',3,'The main() method is the starting point of execution in a Java program.','APPIKONDA BHAGYAKIRAN',3,2),(6,'Good explanation of the main OOP features in Java.',4,'The main features of OOP in Java are encapsulation, inheritance, polymorphism and abstraction.','APPIKONDA BHAGYAKIRAN',3,3),(7,'Automatically evaluated.',2,'B','APPIKONDA BHAGYAKIRAN',4,1),(8,'Feedback: Good answer. Clearly explains the purpose of the main() method.',2,'The main() method is the starting point of execution in a Java program.','APPIKONDA BHAGYAKIRAN',4,2),(9,'Feedback: Good explanation of the main OOP features in Java.',5,'The main features of OOP in Java are encapsulation, inheritance, polymorphism and abstraction.','APPIKONDA BHAGYAKIRAN',4,3),(10,'Automatically evaluated.',2,'B','APPIKONDA BHAGYAKIRAN',5,1),(11,'good',3,'The main() method is the starting point of execution in a Java program.','APPIKONDA BHAGYAKIRAN',5,2),(12,'good',5,'The main features of OOP in Java are encapsulation, inheritance, polymorphism and abstraction.','APPIKONDA BHAGYAKIRAN',5,3),(13,'Automatically evaluated.',2,'B','APPIKONDA BHAGYAKIRAN',6,1),(14,'Correct answer. The main() method is the starting point of a Java program.',3,'The main() method is the starting point of execution in a Java program.','APPIKONDA BHAGYAKIRAN',6,2),(15,'Good answer. The major OOP features have been correctly identified.',5,'The main features of OOP in Java are encapsulation, inheritance, polymorphism and abstraction.','APPIKONDA BHAGYAKIRAN',6,3),(16,'Automatically evaluated.',2,'B','Appikonda Bhagyakiran 259',7,1),(17,'good answer',3,'The main() method is the starting point of execution in a Java program.','Appikonda Bhagyakiran 259',7,2),(18,'good answer',5,'The main features of OOP in Java are encapsulation, inheritance, polymorphism and abstraction.','Appikonda Bhagyakiran 259',7,3),(19,'Automatically evaluated.',2,'C','Appikonda Bhagyakiran 259',8,7),(20,'Automatically evaluated.',2,'B','Appikonda Bhagyakiran 259',8,8),(21,'Automatically evaluated.',2,'C','Appikonda Bhagyakiran 259',8,9),(22,'Automatically evaluated.',2,'B','Appikonda Bhagyakiran 259',8,10),(23,'Automatically evaluated.',2,'A','Appikonda Bhagyakiran 259',8,11),(24,'Automatically evaluated.',2,'C','Appikonda Bhagyakiran 259',8,12),(25,'Pending examiner evaluation.',0,'A function is a reusable block of code designed to perform a specific task. It helps reduce code repetition and makes programs easier to understand and maintain.','Appikonda Bhagyakiran 259',8,13),(26,'Automatically evaluated.',2,'C','Appikonda Bhagyakiran 259',9,7),(27,'Automatically evaluated.',2,'B','Appikonda Bhagyakiran 259',9,8),(28,'Automatically evaluated.',2,'C','Appikonda Bhagyakiran 259',9,9),(29,'Automatically evaluated.',2,'B','Appikonda Bhagyakiran 259',9,10),(30,'Automatically evaluated.',2,'A','Appikonda Bhagyakiran 259',9,11),(31,'Automatically evaluated.',2,'C','Appikonda Bhagyakiran 259',9,12),(32,'Pending examiner evaluation.',0,'A function is a reusable block of code designed to perform a specific task. It helps reduce code repetition and makes programs easier to understand and maintain.','Appikonda Bhagyakiran 259',9,13),(33,'Automatically evaluated.',2,'C','Appikonda Bhagyakiran 259',10,7),(34,'Automatically evaluated.',2,'B','Appikonda Bhagyakiran 259',10,8),(35,'Automatically evaluated.',2,'C','Appikonda Bhagyakiran 259',10,9),(36,'Automatically evaluated.',2,'B','Appikonda Bhagyakiran 259',10,10),(37,'Automatically evaluated.',2,'A','Appikonda Bhagyakiran 259',10,11),(38,'Automatically evaluated.',2,'C','Appikonda Bhagyakiran 259',10,12),(39,'good',3,'A function is a reusable block of code designed to perform a specific task. It helps reduce code repetition and makes programs easier to understand and maintain.','Appikonda Bhagyakiran 259',10,13),(40,'good',5,'Simple and easy-to-learn syntax\r\nHigh-level programming language\r\nInterpreted language\r\nSupports object-oriented programming\r\nDynamically typed\r\nLarge standard library\r\nSupports code reusability through functions and modules\r\nCan be used for web development, data analysis, automation, AI, and other applications','Appikonda Bhagyakiran 259',10,14),(41,'Automatically evaluated.',2,'B','Appikonda Bhagyakiran 259',11,1),(42,'correct',3,'The main() method is the starting point of execution in a Java program.','Appikonda Bhagyakiran 259',11,2),(43,'correct',5,'The main features of OOP in Java are encapsulation, inheritance, polymorphism and abstraction.','Appikonda Bhagyakiran 259',11,3);
/*!40000 ALTER TABLE `exam_answers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exam_results`
--

DROP TABLE IF EXISTS `exam_results`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exam_results` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `maximum_marks` int DEFAULT NULL,
  `objective_marks` int DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `student_name` varchar(255) DEFAULT NULL,
  `total_marks_obtained` int DEFAULT NULL,
  `exam_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtf85ht7yquiorwjx2xbdx3fxw` (`exam_id`),
  CONSTRAINT `FKtf85ht7yquiorwjx2xbdx3fxw` FOREIGN KEY (`exam_id`) REFERENCES `exams` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exam_results`
--

LOCK TABLES `exam_results` WRITE;
/*!40000 ALTER TABLE `exam_results` DISABLE KEYS */;
INSERT INTO `exam_results` VALUES (1,20,2,'Pending Evaluation','APPIKONDA BHAGYAKIRAN',2,1),(2,20,2,'Pending Evaluation','APPIKONDA BHAGYAKIRAN',2,1),(3,20,2,'Completed','APPIKONDA BHAGYAKIRAN',9,1),(4,20,2,'Completed','APPIKONDA BHAGYAKIRAN',9,1),(5,20,2,'Completed','APPIKONDA BHAGYAKIRAN',10,1),(6,20,2,'Completed','APPIKONDA BHAGYAKIRAN',10,1),(7,20,2,'Completed','Appikonda Bhagyakiran 259',10,1),(8,20,12,'Pending Evaluation','Appikonda Bhagyakiran 259',12,3),(9,20,12,'Pending Evaluation','Appikonda Bhagyakiran 259',12,3),(10,20,12,'Completed','Appikonda Bhagyakiran 259',20,3),(11,20,2,'Completed','Appikonda Bhagyakiran 259',10,1);
/*!40000 ALTER TABLE `exam_results` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exams`
--

DROP TABLE IF EXISTS `exams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exams` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `description` text,
  `duration_minutes` int DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `total_marks` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exams`
--

LOCK TABLES `exams` WRITE;
/*!40000 ALTER TABLE `exams` DISABLE KEYS */;
INSERT INTO `exams` VALUES (1,_binary '','Java fundamentals and object-oriented programming',1,'Java Programming Updated',20),(2,_binary '','Advanced Java Programming',30,'Java Advanced',20),(3,_binary '','This examination evaluates the basic concepts of Python programming, including variables, data types, operators, conditional statements, loops, functions, and object-oriented programming.\r\n',10,'Python Programming',20);
/*!40000 ALTER TABLE `exams` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `questions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `correct_answer` varchar(255) DEFAULT NULL,
  `marks` int NOT NULL,
  `optiona` varchar(255) DEFAULT NULL,
  `optionb` varchar(255) DEFAULT NULL,
  `optionc` varchar(255) DEFAULT NULL,
  `optiond` varchar(255) DEFAULT NULL,
  `question_text` varchar(1000) NOT NULL,
  `type` varchar(255) NOT NULL,
  `exam_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrk78bmt53fns7np8casqa3q44` (`exam_id`),
  CONSTRAINT `FKrk78bmt53fns7np8casqa3q44` FOREIGN KEY (`exam_id`) REFERENCES `exams` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
INSERT INTO `questions` VALUES (1,'B',2,'this','extends','implements','super','Which keyword is used to inheritance a class in Java?','MCQ',1),(2,'',3,'','','','','What is the purpose of the main() method in Java?','SHORT_ANSWER',1),(3,'',5,'','','','','Explain the main features of Object-Oriented Programming in Java.','ESSAY',1),(5,'B',2,'this','extends','implements','super','Which keyword is used to inheritance a class in Java?','MCQ',2),(6,'',3,'','','','','What is the purpose of the main() method in Java?','SHORT_ANSWER',2),(7,'C',2,'function','define','def','fun','Which keyword is used to define a function in Python?','MCQ',3),(8,'B',2,'Tuple','List','Set','Dictionary','Which of the following is used to store multiple values in an ordered and changeable collection?','MCQ',3),(9,'C',2,'//','/* */','#','--','Which symbol is used for a single-line comment in Python?','MCQ',3),(10,'B',2,'integer','float','decimalNumber','character','Which of the following is a valid Python data type?','MCQ',3),(11,'A',2,'if','check','switch','select','Which statement is used to make a decision in Python?','MCQ',3),(12,'C',2,'add()','insertEnd()','append()','push()','Which method is used to add an element to the end of a Python list?','MCQ',3),(13,'',3,'','','','','What is the purpose of a function in Python? Explain briefly with an example.','SHORT_ANSWER',3),(14,'',5,'','','','','Explain the main features of Python programming language.','ESSAY',3);
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'appikondakiran12@gmail.com','Appikonda Bhagyakiran 259','$2a$10$1kJUZjUPnoLbGmHI73OG2OupAdFWyeUmLslQlUw0wUylakuyIt1Ou','STUDENT'),(2,'adithya28@gmail.com','ADITHYA.M','$2a$10$ZVScwwB87Xbmpv2qx2ic4u.HHWyYkP9yGU4su04EhHrAG2.jAGM7G','EXAMINER'),(3,'newstudent01@gmail.com','NEW STUDENT','$2a$10$AQ/4/o9fB8fsdmEV7uvLPewwTlXMCUz25KwYbzviSnS3ogDk4nwja','STUDENT'),(4,'chandrasekhar@gmail.com','CHANDRA SEKHAR','$2a$10$uRcg.tyRkxZNMLC1CozsPu575ELSGiy7I/yhxIhWEJwaPGwmlY/f.','STUDENT');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-10-05 20:19:15
