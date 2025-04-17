
-- CREATE DATABASE `application` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
-- GRANT SELECT, INSERT, UPDATE ON application.* TO 'my_user'@'%';

-- CREATE DATABASE `source` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
-- GRANT SELECT, INSERT, UPDATE ON source.* TO 'my_user'@'%';

SET FOREIGN_KEY_CHECKS = 0;
	DROP TABLE `spring_batch`.`BATCH_STEP_EXECUTION_SEQ`;
	DROP TABLE `spring_batch`.`BATCH_STEP_EXECUTION_CONTEXT`;
	DROP TABLE `spring_batch`.`BATCH_STEP_EXECUTION`;
	DROP TABLE `spring_batch`.`BATCH_JOB_SEQ`;
	DROP TABLE `spring_batch`.`BATCH_JOB_INSTANCE`;
	DROP TABLE `spring_batch`.`BATCH_JOB_EXECUTION_SEQ`;
	DROP TABLE `spring_batch`.`BATCH_JOB_EXECUTION_PARAMS`;
	DROP TABLE `spring_batch`.`BATCH_JOB_EXECUTION_CONTEXT`;
	DROP TABLE `spring_batch`.`BATCH_JOB_EXECUTION`;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE IF NOT EXISTS application.people (
  `person_id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`person_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS `source`.people_source (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `source`.people_source (id, name, last_name) VALUES(1,"Jill","Doe")  ON DUPLICATE KEY UPDATE name="Jill", last_name="Doe";
INSERT INTO `source`.people_source (id, name, last_name) VALUES(2,"Joe","Doe")  ON DUPLICATE KEY UPDATE name="Joe", last_name="Doe";
INSERT INTO `source`.people_source (id, name, last_name) VALUES(3,"Justin","Doe")  ON DUPLICATE KEY UPDATE name="Justin", last_name="Doe";
INSERT INTO `source`.people_source (id, name, last_name) VALUES(4,"Jane","Doe")  ON DUPLICATE KEY UPDATE name="Jane", last_name="Doe";
INSERT INTO `source`.people_source (id, name, last_name) VALUES(5,"John","Doe")  ON DUPLICATE KEY UPDATE name="John", last_name="Doe";

delete from application.people where person_id>0;

SELECT * FROM application.people;
