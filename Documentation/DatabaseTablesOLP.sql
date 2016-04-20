/*	Remember to create a database like:
* 	CREATE DATABASE `onlinelearningplatform` ;
*	and select it before running this queries
*/

CREATE TABLE `course_branch` (
  `course_branch_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `course_branch_name` varchar(45) NOT NULL,
  PRIMARY KEY (`course_branch_id`),
  UNIQUE KEY `course_branch_name_UNIQUE` (`course_branch_name`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

CREATE TABLE `course_type` (
  `course_type_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `course_type_name` varchar(45) NOT NULL,
  `ct_course_branch_fk` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`course_type_id`),
  KEY `ct_course_branch_fk_idx` (`ct_course_branch_fk`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

CREATE TABLE `content_provider` (
  `content_provider_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `content_provider_name` varchar(45) NOT NULL,
  `content_provider_description` varchar(45) DEFAULT NULL,
  `content_provider_email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`content_provider_id`),
  UNIQUE KEY `content_provider_name_UNIQUE` (`content_provider_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `course` (
  `course_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `course_name` varchar(45) DEFAULT NULL,
  `course_description` varchar(500) DEFAULT NULL,
  `released` date DEFAULT NULL,
  `language` varchar(45) DEFAULT NULL,
  `skill_level` varchar(45) DEFAULT NULL,
  `c_course_branch_fk` int(10) unsigned NOT NULL,
  `c_course_type_fk` int(10) unsigned NOT NULL,
  `c_content_provider_fk` int(10) unsigned NOT NULL,
  PRIMARY KEY (`course_id`),
  KEY `c_course_branch_fk_idx` (`c_course_branch_fk`),
  KEY `c_course_type_fk_idx` (`c_course_type_fk`),
  KEY `c_content_provider_fk_idx` (`c_content_provider_fk`),
  CONSTRAINT `c_content_provider_fk` FOREIGN KEY (`c_content_provider_fk`) REFERENCES `content_provider` (`content_provider_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `c_course_branch_fk` FOREIGN KEY (`c_course_branch_fk`) REFERENCES `course_branch` (`course_branch_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `c_course_type_fk` FOREIGN KEY (`c_course_type_fk`) REFERENCES `course_type` (`course_type_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `lecture` (
  `lecture_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `lecture_name` varchar(45) DEFAULT NULL,
  `video` varchar(45) DEFAULT NULL,
  `duration` time DEFAULT NULL,
  `l_course_fk` int(10) unsigned NOT NULL,
  PRIMARY KEY (`lecture_id`),
  KEY `l_course_fk_idx` (`l_course_fk`),
  CONSTRAINT `l_course_fk` FOREIGN KEY (`l_course_fk`) REFERENCES `course` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `multiple_choice_question` (
  `multiple_choice_question_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `mcq_name` varchar(45) DEFAULT NULL,
  `mcq_question` varchar(45) DEFAULT NULL,
  `mcq_course_fk` int(10) unsigned NOT NULL,
  `mcq_lecture_fk` int(10) unsigned NOT NULL,
  PRIMARY KEY (`multiple_choice_question_id`),
  KEY `mcq_course_fk_idx` (`mcq_course_fk`),
  KEY `mcq_lecture_fk_idx` (`mcq_lecture_fk`),
  CONSTRAINT `mcq_course_fk` FOREIGN KEY (`mcq_course_fk`) REFERENCES `course` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `mcq_lecture_fk` FOREIGN KEY (`mcq_lecture_fk`) REFERENCES `lecture` (`lecture_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `multiple_choice_options` (
  `multiple_choice_options_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `mco_sequence` varchar(45) DEFAULT NULL,
  `mco_the_option` varchar(45) DEFAULT NULL,
  `mco_question_fk` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`multiple_choice_options_id`),
  KEY `mco_question_fk_idx` (`mco_question_fk`),
  CONSTRAINT `mco_question_fk` FOREIGN KEY (`mco_question_fk`) REFERENCES `multiple_choice_question` (`multiple_choice_question_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `multiple_choice_answer` (
  `multiple_choice_answer_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `mca_explanation` varchar(45) DEFAULT NULL,
  `mca_question_fk` int(10) unsigned NOT NULL,
  `mca_option_fk` int(10) unsigned NOT NULL,
  PRIMARY KEY (`multiple_choice_answer_id`),
  KEY `mca_option_fk_idx` (`mca_option_fk`),
  KEY `mca_question_fk_idx` (`mca_question_fk`),
  CONSTRAINT `mca_option_fk` FOREIGN KEY (`mca_option_fk`) REFERENCES `multiple_choice_options` (`multiple_choice_options_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `mca_question_fk` FOREIGN KEY (`mca_question_fk`) REFERENCES `multiple_choice_question` (`multiple_choice_question_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `user` (
  `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_name` varchar(45) CHARACTER SET utf8 DEFAULT NULL,
  `password` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

