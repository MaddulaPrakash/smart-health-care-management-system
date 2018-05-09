-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.0.45-community-nt - MySQL Community Edition (GPL)
-- Server OS:                    Win32
-- HeidiSQL Version:             8.3.0.4694
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping database structure for diafoot
DROP DATABASE IF EXISTS `personalhealt`;
CREATE DATABASE IF NOT EXISTS `personalhealt` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `personalhealt`;


-- Dumping structure for table diafoot.answer
DROP TABLE IF EXISTS `answer`;
CREATE TABLE IF NOT EXISTS `answer` (
  `QUESID` int(11) NOT NULL,
  `QUESDESC` varchar(250) NOT NULL,
  `PATEINTNAME` varchar(250) NOT NULL,
  `TYPE` varchar(250) NOT NULL,
  `ANSWER1` varchar(250) NOT NULL,
  `ANSWER2` varchar(250) NOT NULL,
  `ANSWER3` varchar(250) NOT NULL,
  `ANSWER4` varchar(250) NOT NULL,
  `SUG1` varchar(250) NOT NULL,
  `SUG2` varchar(250) NOT NULL,
  `SUG3` varchar(250) NOT NULL,
  `SUG4` varchar(250) NOT NULL,
  `TESTNAME` varchar(250) NOT NULL,
  `RATING1` int(11) NOT NULL,
  `RATING2` int(11) NOT NULL,
  `RATING3` int(11) NOT NULL,
  `RATING4` int(11) NOT NULL,
  `SELECTEDANSWER` int(11) NOT NULL,
  `TOTALRATING` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table diafoot.answer: ~36 rows (approximately)
/*!40000 ALTER TABLE `answer` DISABLE KEYS */;
INSERT INTO `answer` (`QUESID`, `QUESDESC`, `PATEINTNAME`, `TYPE`, `ANSWER1`, `ANSWER2`, `ANSWER3`, `ANSWER4`, `SUG1`, `SUG2`, `SUG3`, `SUG4`, `TESTNAME`, `RATING1`, `RATING2`, `RATING3`, `RATING4`, `SELECTEDANSWER`, `TOTALRATING`) VALUES
	(63, 'What is the sugar level?', 'rvcepateint1', 'LOWDIABETICS', '10-20 percent', '20-30 percent', '30-45 percent', 'greater than 45 percent', 'Its ok but have  a walk early moring', 'This is also cool have sugar less elements for all cooked food', 'Need to take only chapatis dont take high quantity of rice', 'Consult family doctor and take tablets regularly', 'DiabeticsTest1', 4, 2, 3, 1, 4, 4),
	(64, 'What is the type of food you have evening?', 'rvcepateint1', 'LOWDIABETICS', 'Sweet Choclates around 4-5', 'Biscuts around 3 packets', 'I will have usually Gobi ', 'I take Chikuu jucie dialy 3 times', 'This is Suggestion1', 'This is Suggestion2 ', 'This is suggestion3', 'This is suggestion4', 'DiabeticsTest1', 4, 3, 1, 2, 3, 4),
	(65, 'How many kilometers doo you walk daily?', 'rvcepateint1', 'LOWDIABETICS', '1-2 kilometers', '2-4 kilometers', '4-8 kilometers', 'greater than 8 kilometers', 'Walk at least 8 kilometers', 'Increse by another 4 kilometers', 'Good make a point you never miss this', 'Very Good you will get positive results at the end of the month', 'DiabeticsTest1', 4, 3, 2, 1, 4, 4),
	(66, 'What is the tablet you are taking 3 times in a day?', 'rvcepateint1', 'LOWDIABETICS', 'paracetomal12mg', 'omega678', 'Del789', 'Delluiy890', 'Take Omega 678 tablet', 'good continue with this tablet', 'Take Omega 678 tablet', 'Take Omega 678 tablet', 'DiabeticsTest1', 2, 1, 3, 4, 2, 4),
	(63, 'What is the sugar level?', 'rvcepateint2', 'MEDIUMDIABETICS', '10-20 percent', '20-30 percent', '30-45 percent', 'greater than 45 percent', 'Its ok but have  a walk early moring', 'This is also cool have sugar less elements for all cooked food', 'Need to take only chapatis dont take high quantity of rice', 'Consult family doctor and take tablets regularly', 'DiabeticsTest1', 4, 2, 3, 1, 4, 5),
	(64, 'What is the type of food you have evening?', 'rvcepateint2', 'MEDIUMDIABETICS', 'Sweet Choclates around 4-5', 'Biscuts around 3 packets', 'I will have usually Gobi ', 'I take Chikuu jucie dialy 3 times', 'This is Suggestion1', 'This is Suggestion2 ', 'This is suggestion3', 'This is suggestion4', 'DiabeticsTest1', 4, 3, 1, 2, 3, 5),
	(65, 'How many kilometers doo you walk daily?', 'rvcepateint2', 'MEDIUMDIABETICS', '1-2 kilometers', '2-4 kilometers', '4-8 kilometers', 'greater than 8 kilometers', 'Walk at least 8 kilometers', 'Increse by another 4 kilometers', 'Good make a point you never miss this', 'Very Good you will get positive results at the end of the month', 'DiabeticsTest1', 4, 3, 2, 1, 3, 5),
	(66, 'What is the tablet you are taking 3 times in a day?', 'rvcepateint2', 'MEDIUMDIABETICS', 'paracetomal12mg', 'omega678', 'Del789', 'Delluiy890', 'Take Omega 678 tablet', 'good continue with this tablet', 'Take Omega 678 tablet', 'Take Omega 678 tablet', 'DiabeticsTest1', 2, 1, 3, 4, 2, 5),
	(63, 'What is the sugar level?', 'rvcepateint3', 'MEDIUMDIABETICS', '10-20 percent', '20-30 percent', '30-45 percent', 'greater than 45 percent', 'Its ok but have  a walk early moring', 'This is also cool have sugar less elements for all cooked food', 'Need to take only chapatis dont take high quantity of rice', 'Consult family doctor and take tablets regularly', 'DiabeticsTest1', 4, 2, 3, 1, 4, 5),
	(64, 'What is the type of food you have evening?', 'rvcepateint3', 'MEDIUMDIABETICS', 'Sweet Choclates around 4-5', 'Biscuts around 3 packets', 'I will have usually Gobi ', 'I take Chikuu jucie dialy 3 times', 'This is Suggestion1', 'This is Suggestion2 ', 'This is suggestion3', 'This is suggestion4', 'DiabeticsTest1', 4, 3, 1, 2, 4, 5),
	(65, 'How many kilometers doo you walk daily?', 'rvcepateint3', 'MEDIUMDIABETICS', '1-2 kilometers', '2-4 kilometers', '4-8 kilometers', 'greater than 8 kilometers', 'Walk at least 8 kilometers', 'Increse by another 4 kilometers', 'Good make a point you never miss this', 'Very Good you will get positive results at the end of the month', 'DiabeticsTest1', 4, 3, 2, 1, 4, 5),
	(66, 'What is the tablet you are taking 3 times in a day?', 'rvcepateint3', 'MEDIUMDIABETICS', 'paracetomal12mg', 'omega678', 'Del789', 'Delluiy890', 'Take Omega 678 tablet', 'good continue with this tablet', 'Take Omega 678 tablet', 'Take Omega 678 tablet', 'DiabeticsTest1', 2, 1, 3, 4, 2, 5),
	(63, 'What is the sugar level?', 'rvcepateint3', 'HIGHDIABETICS', '10-20 percent', '20-30 percent', '30-45 percent', 'greater than 45 percent', 'Its ok but have  a walk early moring', 'This is also cool have sugar less elements for all cooked food', 'Need to take only chapatis dont take high quantity of rice', 'Consult family doctor and take tablets regularly', 'DiabeticsTest1', 4, 2, 3, 1, 1, 13),
	(64, 'What is the type of food you have evening?', 'rvcepateint3', 'HIGHDIABETICS', 'Sweet Choclates around 4-5', 'Biscuts around 3 packets', 'I will have usually Gobi ', 'I take Chikuu jucie dialy 3 times', 'This is Suggestion1', 'This is Suggestion2 ', 'This is suggestion3', 'This is suggestion4', 'DiabeticsTest1', 4, 3, 1, 2, 1, 13),
	(65, 'How many kilometers doo you walk daily?', 'rvcepateint3', 'HIGHDIABETICS', '1-2 kilometers', '2-4 kilometers', '4-8 kilometers', 'greater than 8 kilometers', 'Walk at least 8 kilometers', 'Increse by another 4 kilometers', 'Good make a point you never miss this', 'Very Good you will get positive results at the end of the month', 'DiabeticsTest1', 4, 3, 2, 1, 1, 13),
	(66, 'What is the tablet you are taking 3 times in a day?', 'rvcepateint3', 'HIGHDIABETICS', 'paracetomal12mg', 'omega678', 'Del789', 'Delluiy890', 'Take Omega 678 tablet', 'good continue with this tablet', 'Take Omega 678 tablet', 'Take Omega 678 tablet', 'DiabeticsTest1', 2, 1, 3, 4, 2, 13),
	(63, 'What is the sugar level?', 'newpatient123', 'HIGHDIABETICS', '10-20 percent', '20-30 percent', '30-45 percent', 'greater than 45 percent', 'Its ok but have  a walk early moring', 'This is also cool have sugar less elements for all cooked food', 'Need to take only chapatis dont take high quantity of rice', 'Consult family doctor and take tablets regularly', 'DiabeticsTest1', 4, 2, 3, 1, 1, 13),
	(64, 'What is the type of food you have evening?', 'newpatient123', 'HIGHDIABETICS', 'Sweet Choclates around 4-5', 'Biscuts around 3 packets', 'I will have usually Gobi ', 'I take Chikuu jucie dialy 3 times', 'This is Suggestion1', 'This is Suggestion2 ', 'This is suggestion3', 'This is suggestion4', 'DiabeticsTest1', 4, 3, 1, 2, 1, 13),
	(65, 'How many kilometers doo you walk daily?', 'newpatient123', 'HIGHDIABETICS', '1-2 kilometers', '2-4 kilometers', '4-8 kilometers', 'greater than 8 kilometers', 'Walk at least 8 kilometers', 'Increse by another 4 kilometers', 'Good make a point you never miss this', 'Very Good you will get positive results at the end of the month', 'DiabeticsTest1', 4, 3, 2, 1, 1, 13),
	(66, 'What is the tablet you are taking 3 times in a day?', 'newpatient123', 'HIGHDIABETICS', 'paracetomal12mg', 'omega678', 'Del789', 'Delluiy890', 'Take Omega 678 tablet', 'good continue with this tablet', 'Take Omega 678 tablet', 'Take Omega 678 tablet', 'DiabeticsTest1', 2, 1, 3, 4, 2, 13),
	(67, 'For How many days have you taken omega 456', 'rvcepateint3', 'HIGHDIABETICS', 'i have taken 10 days', 'i have taken 2 days', 'i have taken 1 week', 'i have taken 1 month', 'Take omega 456 for another 20 days', 'Take omega 456 for another 28 days', 'Take omega 456 for another 2 weeks', 'Good continue on that', 'DiabeticsTest2', 2, 4, 3, 1, 2, 14),
	(68, 'What kind of fruit juices do you have taken and How many', 'rvcepateint3', 'HIGHDIABETICS', 'Chikuu daily 3 times', 'Mango dialy 3 times', 'Apple dialy 3 times', 'Lemon Daily 3 times', 'Take Ppper Juice dialy once', 'Take Pepper Juice daily twice ', 'Take Lemon Juice instead', 'Good you can have it', 'DiabeticsTest2', 3, 4, 2, 1, 2, 14),
	(69, 'How Many times do you have food', 'rvcepateint3', 'HIGHDIABETICS', '5 times a day', '6 times a day', '3 times a day', '4 times a day', 'Have 3 times a day and take sugar less coffee if you need', 'Have 3 times a day and please avoid such habits', 'Good continue on this', 'its ok but try to skip once instead have some snacks with low sugar content', 'DiabeticsTest2', 3, 4, 1, 2, 2, 14),
	(70, 'How many kilometers do you run daily', 'rvcepateint3', 'HIGHDIABETICS', '1 kilometer i run daily', '2 kilometers i run daily', '3 kilometers i run daily', '4 kilometers i run daily', 'Run 4 kilometers more', 'Run 5 kilometers more', 'Run 2 kilometers more', 'Good try it and continue working on it', 'DiabeticsTest2', 4, 3, 2, 1, 3, 14),
	(67, 'For How many days have you taken omega 456', 'rvcepateint3', 'HIGHDIABETICS', 'i have taken 10 days', 'i have taken 2 days', 'i have taken 1 week', 'i have taken 1 month', 'Take omega 456 for another 20 days', 'Take omega 456 for another 28 days', 'Take omega 456 for another 2 weeks', 'Good continue on that', 'DiabeticsTest2', 2, 4, 3, 1, 2, 16),
	(68, 'What kind of fruit juices do you have taken and How many', 'rvcepateint3', 'HIGHDIABETICS', 'Chikuu daily 3 times', 'Mango dialy 3 times', 'Apple dialy 3 times', 'Lemon Daily 3 times', 'Take Ppper Juice dialy once', 'Take Pepper Juice daily twice ', 'Take Lemon Juice instead', 'Good you can have it', 'DiabeticsTest2', 3, 4, 2, 1, 2, 16),
	(69, 'How Many times do you have food', 'rvcepateint3', 'HIGHDIABETICS', '5 times a day', '6 times a day', '3 times a day', '4 times a day', 'Have 3 times a day and take sugar less coffee if you need', 'Have 3 times a day and please avoid such habits', 'Good continue on this', 'its ok but try to skip once instead have some snacks with low sugar content', 'DiabeticsTest2', 3, 4, 1, 2, 2, 16),
	(70, 'How many kilometers do you run daily', 'rvcepateint3', 'HIGHDIABETICS', '1 kilometer i run daily', '2 kilometers i run daily', '3 kilometers i run daily', '4 kilometers i run daily', 'Run 4 kilometers more', 'Run 5 kilometers more', 'Run 2 kilometers more', 'Good try it and continue working on it', 'DiabeticsTest2', 4, 3, 2, 1, 1, 16),
	(67, 'For How many days have you taken omega 456', 'SHAB145', 'HIGHDIABETICS', 'i have taken 10 days', 'i have taken 2 days', 'i have taken 1 week', 'i have taken 1 month', 'Take omega 456 for another 20 days', 'Take omega 456 for another 28 days', 'Take omega 456 for another 2 weeks', 'Good continue on that', 'DiabeticsTest2', 2, 4, 3, 1, 2, 16),
	(68, 'What kind of fruit juices do you have taken and How many', 'SHAB145', 'HIGHDIABETICS', 'Chikuu daily 3 times', 'Mango dialy 3 times', 'Apple dialy 3 times', 'Lemon Daily 3 times', 'Take Ppper Juice dialy once', 'Take Pepper Juice daily twice ', 'Take Lemon Juice instead', 'Good you can have it', 'DiabeticsTest2', 3, 4, 2, 1, 2, 16),
	(69, 'How Many times do you have food', 'SHAB145', 'HIGHDIABETICS', '5 times a day', '6 times a day', '3 times a day', '4 times a day', 'Have 3 times a day and take sugar less coffee if you need', 'Have 3 times a day and please avoid such habits', 'Good continue on this', 'its ok but try to skip once instead have some snacks with low sugar content', 'DiabeticsTest2', 3, 4, 1, 2, 2, 16),
	(70, 'How many kilometers do you run daily', 'SHAB145', 'HIGHDIABETICS', '1 kilometer i run daily', '2 kilometers i run daily', '3 kilometers i run daily', '4 kilometers i run daily', 'Run 4 kilometers more', 'Run 5 kilometers more', 'Run 2 kilometers more', 'Good try it and continue working on it', 'DiabeticsTest2', 4, 3, 2, 1, 1, 16),
	(63, 'What is the sugar level?', 'newpatient123', 'HIGHDIABETICS', '10-20 percent', '20-30 percent', '30-45 percent', 'greater than 45 percent', 'Its ok but have  a walk early moring', 'This is also cool have sugar less elements for all cooked food', 'Need to take only chapatis dont take high quantity of rice', 'Consult family doctor and take tablets regularly', 'DiabeticsTest1', 4, 2, 3, 1, 1, 13),
	(64, 'What is the type of food you have evening?', 'newpatient123', 'HIGHDIABETICS', 'Sweet Choclates around 4-5', 'Biscuts around 3 packets', 'I will have usually Gobi ', 'I take Chikuu jucie dialy 3 times', 'This is Suggestion1', 'This is Suggestion2 ', 'This is suggestion3', 'This is suggestion4', 'DiabeticsTest1', 4, 3, 1, 2, 1, 13),
	(65, 'How many kilometers doo you walk daily?', 'newpatient123', 'HIGHDIABETICS', '1-2 kilometers', '2-4 kilometers', '4-8 kilometers', 'greater than 8 kilometers', 'Walk at least 8 kilometers', 'Increse by another 4 kilometers', 'Good make a point you never miss this', 'Very Good you will get positive results at the end of the month', 'DiabeticsTest1', 4, 3, 2, 1, 1, 13),
	(66, 'What is the tablet you are taking 3 times in a day?', 'newpatient123', 'HIGHDIABETICS', 'paracetomal12mg', 'omega678', 'Del789', 'Delluiy890', 'Take Omega 678 tablet', 'good continue with this tablet', 'Take Omega 678 tablet', 'Take Omega 678 tablet', 'DiabeticsTest1', 2, 1, 3, 4, 2, 13),
	(63, 'What is the sugar level?', 'newpatient123', 'MEDIUMDIABETICS', '10-20 percent', '20-30 percent', '30-45 percent', 'greater than 45 percent', 'Its ok but have  a walk early moring', 'This is also cool have sugar less elements for all cooked food', 'Need to take only chapatis dont take high quantity of rice', 'Consult family doctor and take tablets regularly', 'DiabeticsTest1', 4, 2, 3, 1, 4, 5),
	(64, 'What is the type of food you have evening?', 'newpatient123', 'MEDIUMDIABETICS', 'Sweet Choclates around 4-5', 'Biscuts around 3 packets', 'I will have usually Gobi ', 'I take Chikuu jucie dialy 3 times', 'This is Suggestion1', 'This is Suggestion2 ', 'This is suggestion3', 'This is suggestion4', 'DiabeticsTest1', 4, 3, 1, 2, 4, 5),
	(65, 'How many kilometers doo you walk daily?', 'newpatient123', 'MEDIUMDIABETICS', '1-2 kilometers', '2-4 kilometers', '4-8 kilometers', 'greater than 8 kilometers', 'Walk at least 8 kilometers', 'Increse by another 4 kilometers', 'Good make a point you never miss this', 'Very Good you will get positive results at the end of the month', 'DiabeticsTest1', 4, 3, 2, 1, 4, 5),
	(66, 'What is the tablet you are taking 3 times in a day?', 'newpatient123', 'MEDIUMDIABETICS', 'paracetomal12mg', 'omega678', 'Del789', 'Delluiy890', 'Take Omega 678 tablet', 'good continue with this tablet', 'Take Omega 678 tablet', 'Take Omega 678 tablet', 'DiabeticsTest1', 2, 1, 3, 4, 2, 5),
	(63, 'What is the sugar level?', 'newpatient123', 'HIGHDIABETICS', '10-20 percent', '20-30 percent', '30-45 percent', 'greater than 45 percent', 'Its ok but have  a walk early moring', 'This is also cool have sugar less elements for all cooked food', 'Need to take only chapatis dont take high quantity of rice', 'Consult family doctor and take tablets regularly', 'DiabeticsTest1', 4, 2, 3, 1, 1, 13),
	(64, 'What is the type of food you have evening?', 'newpatient123', 'HIGHDIABETICS', 'Sweet Choclates around 4-5', 'Biscuts around 3 packets', 'I will have usually Gobi ', 'I take Chikuu jucie dialy 3 times', 'This is Suggestion1', 'This is Suggestion2 ', 'This is suggestion3', 'This is suggestion4', 'DiabeticsTest1', 4, 3, 1, 2, 1, 13),
	(65, 'How many kilometers doo you walk daily?', 'newpatient123', 'HIGHDIABETICS', '1-2 kilometers', '2-4 kilometers', '4-8 kilometers', 'greater than 8 kilometers', 'Walk at least 8 kilometers', 'Increse by another 4 kilometers', 'Good make a point you never miss this', 'Very Good you will get positive results at the end of the month', 'DiabeticsTest1', 4, 3, 2, 1, 1, 13),
	(66, 'What is the tablet you are taking 3 times in a day?', 'newpatient123', 'HIGHDIABETICS', 'paracetomal12mg', 'omega678', 'Del789', 'Delluiy890', 'Take Omega 678 tablet', 'good continue with this tablet', 'Take Omega 678 tablet', 'Take Omega 678 tablet', 'DiabeticsTest1', 2, 1, 3, 4, 2, 13),
	(63, 'What is the sugar level?', 'newpatient123', 'HIGHDIABETICS', '10-20 percent', '20-30 percent', '30-45 percent', 'greater than 45 percent', 'Its ok but have  a walk early moring', 'This is also cool have sugar less elements for all cooked food', 'Need to take only chapatis dont take high quantity of rice', 'Consult family doctor and take tablets regularly', 'DiabeticsTest1', 4, 2, 3, 1, 1, 13),
	(64, 'What is the type of food you have evening?', 'newpatient123', 'HIGHDIABETICS', 'Sweet Choclates around 4-5', 'Biscuts around 3 packets', 'I will have usually Gobi ', 'I take Chikuu jucie dialy 3 times', 'This is Suggestion1', 'This is Suggestion2 ', 'This is suggestion3', 'This is suggestion4', 'DiabeticsTest1', 4, 3, 1, 2, 1, 13),
	(65, 'How many kilometers doo you walk daily?', 'newpatient123', 'HIGHDIABETICS', '1-2 kilometers', '2-4 kilometers', '4-8 kilometers', 'greater than 8 kilometers', 'Walk at least 8 kilometers', 'Increse by another 4 kilometers', 'Good make a point you never miss this', 'Very Good you will get positive results at the end of the month', 'DiabeticsTest1', 4, 3, 2, 1, 1, 13),
	(66, 'What is the tablet you are taking 3 times in a day?', 'newpatient123', 'HIGHDIABETICS', 'paracetomal12mg', 'omega678', 'Del789', 'Delluiy890', 'Take Omega 678 tablet', 'good continue with this tablet', 'Take Omega 678 tablet', 'Take Omega 678 tablet', 'DiabeticsTest1', 2, 1, 3, 4, 2, 13);
/*!40000 ALTER TABLE `answer` ENABLE KEYS */;


-- Dumping structure for table diafoot.appointment
DROP TABLE IF EXISTS `appointment`;
CREATE TABLE IF NOT EXISTS `appointment` (
  `APPOINTID` int(11) NOT NULL auto_increment,
  `PATIENTNAME` varchar(250) NOT NULL,
  `STATUS` int(1) NOT NULL,
  `DATE` varchar(50) default NULL,
  `TIMEIN` varchar(50) default NULL,
  `TIMEOUT` varchar(50) default NULL,
  `DOCNAME` varchar(50) default NULL,
  PRIMARY KEY  (`APPOINTID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Dumping data for table diafoot.appointment: ~0 rows (approximately)
/*!40000 ALTER TABLE `appointment` DISABLE KEYS */;
/*!40000 ALTER TABLE `appointment` ENABLE KEYS */;


-- Dumping structure for table diafoot.bank
DROP TABLE IF EXISTS `bank`;
CREATE TABLE IF NOT EXISTS `bank` (
  `ACCOUNTNO` varchar(50) NOT NULL default '',
  `IPIN` varchar(50) default NULL,
  `AMOUNT` double default NULL,
  PRIMARY KEY  (`ACCOUNTNO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table diafoot.bank: ~2 rows (approximately)
/*!40000 ALTER TABLE `bank` DISABLE KEYS */;
INSERT INTO `bank` (`ACCOUNTNO`, `IPIN`, `AMOUNT`) VALUES
	('ACCOUNT1', 'ACCOUNT777', 46555),
	('ACCOUNT2', 'PASS123', 497000);
/*!40000 ALTER TABLE `bank` ENABLE KEYS */;


-- Dumping structure for table diafoot.eligibility
DROP TABLE IF EXISTS `eligibility`;
CREATE TABLE IF NOT EXISTS `eligibility` (
  `PATIENTNAME` varchar(500) NOT NULL,
  `TESTNAME` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table diafoot.eligibility: ~8 rows (approximately)
/*!40000 ALTER TABLE `eligibility` DISABLE KEYS */;
INSERT INTO `eligibility` (`PATIENTNAME`, `TESTNAME`) VALUES
	('patient134', 'DiabeticsTest1'),
	('rvcepateint1', 'DiabeticsTest1'),
	('rvcepateint2', 'DiabeticsTest1'),
	('rvcepateint3', 'DiabeticsTest1'),
	('SHAB145', 'DiabeticsTest1'),
	('sonia', 'DiabeticsTest1'),
	('test', 'DiabeticsTest1'),
	('newpatient123', 'DiabeticsTest1');
/*!40000 ALTER TABLE `eligibility` ENABLE KEYS */;


-- Dumping structure for table diafoot.login
DROP TABLE IF EXISTS `login`;
CREATE TABLE IF NOT EXISTS `login` (
  `FIRSTNAME` varchar(500) NOT NULL,
  `LASTNAME` varchar(500) NOT NULL,
  `PASSWORD` varchar(500) NOT NULL,
  `EMAIL` varchar(500) NOT NULL,
  `USERID` varchar(50) NOT NULL,
  `LOGINTYPE` int(11) NOT NULL default '1',
  PRIMARY KEY  (`USERID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table diafoot.login: ~21 rows (approximately)
/*!40000 ALTER TABLE `login` DISABLE KEYS */;
INSERT INTO `login` (`FIRSTNAME`, `LASTNAME`, `PASSWORD`, `EMAIL`, `USERID`, `LOGINTYPE`) VALUES
	('AAQUIB', 'Nawaz', 'PASS123', 'aaquib@gmail.com', 'AAQUIBUSER', 1),
	('asdas', 'sadsad', 'sadasd', 'asd@hjas.com', 'asd', 1),
	('Bushra', 'Tazeen', 'pass123', 'bush@gmail.com', 'bush123', 2),
	('Bushra', 'Tazeen', 'pass123', 'aaquibnawaz@gmail.com', 'bushratazeen123', 1),
	('Rahul', 'Gandhi', 'bestpolitician', 'yongindia@gmail.com', 'indianicon', 2),
	('Kiran', 'Kumar', 'PASS123', 'kumar@gmail.com', 'KIRAN1123', 2),
	('aaquib', 'nawaz', 'pass123', 'aaquibnawazrvce@gmail.com', 'newpatient123', 4),
	('Aaquib', 'Nawaz', 'pass123', 'acidattack@gmail.com', 'patient134', 4),
	('Rahul', 'Gandhi', 'pass123', 'rahulgandi@gmail.com', 'rahulji', 3),
	('rvcollege', 'engineering', 'pass123', 'aaqrvce@gmail.com', 'rvceadmin123', 1),
	('rvcedoctor', 'enginering', 'pass123', 'rvcedoctor@gmail.com', 'rvcedoctor123', 2),
	('rvce123', 'patient12', 'pass123', 'rvcepat123@gmail.com', 'rvcepateint1', 4),
	('rvcecomputer', 'pateint12345', 'pass123', 'rvcepat2874@gmail.com', 'rvcepateint2', 4),
	('rvcepat', 'computer science', 'pass123', 'rvcepatcs56@gmail.com', 'rvcepateint3', 4),
	('rvcepateintcor', 'enginering', 'pass123', 'rvcepatient@gmail.com', 'rvcepc123', 3),
	('Shabnaaz', 'Meena', 'pass1234', 'shab@gmail.com', 'shab123', 1),
	('Shabnaaz', 'Meena', 'pass1234', 'shab@gmail.com', 'shab12345', 1),
	('Shabnaz', 'Meen', 'shab123', 'shabnaz.aaquib@gmail.com', 'SHAB145', 4),
	('shashank', 'p', 'pass123', 'shashankp@gmail.com', 'shashankp', 1),
	('Sonia', 'Gandhi', 'pass123', 'sonia.gandhi@gmail.com', 'sonia', 4),
	('test', 'test', 'test', 'test@gmail.com', 'test', 4);
/*!40000 ALTER TABLE `login` ENABLE KEYS */;


-- Dumping structure for table diafoot.question
DROP TABLE IF EXISTS `question`;
CREATE TABLE IF NOT EXISTS `question` (
  `QUESID` int(11) NOT NULL auto_increment,
  `QUESDESC` varchar(250) NOT NULL,
  `ANSWER1` varchar(250) NOT NULL,
  `ANSWER2` varchar(250) NOT NULL,
  `ANSWER3` varchar(250) NOT NULL,
  `ANSWER4` varchar(250) NOT NULL,
  `SUG1` varchar(250) NOT NULL,
  `SUG2` varchar(250) NOT NULL,
  `SUG3` varchar(250) NOT NULL,
  `SUG4` varchar(250) NOT NULL,
  `TESTNAME` varchar(250) NOT NULL,
  `RATING1` int(11) NOT NULL,
  `RATING2` int(11) NOT NULL,
  `RATING3` int(11) NOT NULL,
  `RATING4` int(11) NOT NULL,
  PRIMARY KEY  (`QUESID`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=latin1;

-- Dumping data for table diafoot.question: ~8 rows (approximately)
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` (`QUESID`, `QUESDESC`, `ANSWER1`, `ANSWER2`, `ANSWER3`, `ANSWER4`, `SUG1`, `SUG2`, `SUG3`, `SUG4`, `TESTNAME`, `RATING1`, `RATING2`, `RATING3`, `RATING4`) VALUES
	(63, 'What is the sugar level?', '10-20 percent', '20-30 percent', '30-45 percent', 'greater than 45 percent', 'Its ok but have  a walk early moring', 'This is also cool have sugar less elements for all cooked food', 'Need to take only chapatis dont take high quantity of rice', 'Consult family doctor and take tablets regularly', 'DiabeticsTest1', 4, 2, 3, 1),
	(64, 'What is the type of food you have evening?', 'Sweet Choclates around 4-5', 'Biscuts around 3 packets', 'I will have usually Gobi ', 'I take Chikuu jucie dialy 3 times', 'This is Suggestion1', 'This is Suggestion2 ', 'This is suggestion3', 'This is suggestion4', 'DiabeticsTest1', 4, 3, 1, 2),
	(65, 'How many kilometers doo you walk daily?', '1-2 kilometers', '2-4 kilometers', '4-8 kilometers', 'greater than 8 kilometers', 'Walk at least 8 kilometers', 'Increse by another 4 kilometers', 'Good make a point you never miss this', 'Very Good you will get positive results at the end of the month', 'DiabeticsTest1', 4, 3, 2, 1),
	(66, 'What is the tablet you are taking 3 times in a day?', 'paracetomal12mg', 'omega678', 'Del789', 'Delluiy890', 'Take Omega 678 tablet', 'good continue with this tablet', 'Take Omega 678 tablet', 'Take Omega 678 tablet', 'DiabeticsTest1', 2, 1, 3, 4),
	(67, 'For How many days have you taken omega 456', 'i have taken 10 days', 'i have taken 2 days', 'i have taken 1 week', 'i have taken 1 month', 'Take omega 456 for another 20 days', 'Take omega 456 for another 28 days', 'Take omega 456 for another 2 weeks', 'Good continue on that', 'DiabeticsTest2', 2, 4, 3, 1),
	(68, 'What kind of fruit juices do you have taken and How many', 'Chikuu daily 3 times', 'Mango dialy 3 times', 'Apple dialy 3 times', 'Lemon Daily 3 times', 'Take Ppper Juice dialy once', 'Take Pepper Juice daily twice ', 'Take Lemon Juice instead', 'Good you can have it', 'DiabeticsTest2', 3, 4, 2, 1),
	(69, 'How Many times do you have food', '5 times a day', '6 times a day', '3 times a day', '4 times a day', 'Have 3 times a day and take sugar less coffee if you need', 'Have 3 times a day and please avoid such habits', 'Good continue on this', 'its ok but try to skip once instead have some snacks with low sugar content', 'DiabeticsTest2', 3, 4, 1, 2),
	(70, 'How many kilometers do you run daily', '1 kilometer i run daily', '2 kilometers i run daily', '3 kilometers i run daily', '4 kilometers i run daily', 'Run 4 kilometers more', 'Run 5 kilometers more', 'Run 2 kilometers more', 'Good try it and continue working on it', 'DiabeticsTest2', 4, 3, 2, 1);
/*!40000 ALTER TABLE `question` ENABLE KEYS */;


-- Dumping structure for table diafoot.role
DROP TABLE IF EXISTS `role`;
CREATE TABLE IF NOT EXISTS `role` (
  `ROLEID` int(11) NOT NULL,
  `ROLENAME` varchar(50) NOT NULL,
  `ROLEDESC` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table diafoot.role: ~4 rows (approximately)
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` (`ROLEID`, `ROLENAME`, `ROLEDESC`) VALUES
	(1, 'ADMIN', 'ADMIN'),
	(2, 'DOCTOR', 'DOCTOR'),
	(3, 'PATIENT', 'PATIENT'),
	(4, 'CUSTOMER', 'CUSTOMER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;


-- Dumping structure for table diafoot.test
DROP TABLE IF EXISTS `test`;
CREATE TABLE IF NOT EXISTS `test` (
  `TESTNAME` varchar(250) NOT NULL,
  `R1LL` double NOT NULL,
  `R1HL` double NOT NULL,
  `R2LL` double NOT NULL,
  `R2HL` double NOT NULL,
  `R3LL` double NOT NULL,
  `R3HL` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table diafoot.test: ~2 rows (approximately)
/*!40000 ALTER TABLE `test` DISABLE KEYS */;
INSERT INTO `test` (`TESTNAME`, `R1LL`, `R1HL`, `R2LL`, `R2HL`, `R3LL`, `R3HL`) VALUES
	('DiabeticsTest1', 0, 4, 5, 7, 8, 15),
	('DiabeticsTest2', 0, 4, 5, 7, 8, 15);
/*!40000 ALTER TABLE `test` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
