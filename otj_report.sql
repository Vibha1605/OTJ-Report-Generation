-- MySQL Script generated by MySQL Workbench
-- Wed Jan 31 19:27:38 2024
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema otj_report
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema otj_report
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `otj_report` DEFAULT CHARACTER SET utf8 ;
USE `otj_report` ;

-- -----------------------------------------------------
-- Table `otj_report`.`assessment_report`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `otj_report`.`assessment_report` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `associate_id` INT NULL,
  `associate_name` VARCHAR(100) NULL,
  `associate_email` VARCHAR(255) NULL,
  `otj_name` VARCHAR(500) NULL,
  `otj_code` VARCHAR(20) NULL,
  `otj_received_date` DATE NULL,
  `planned_delivery_date` DATE NULL,
  `actual_delivery_date` DATE NULL,
  `score` INT NULL,
  `status` VARCHAR(30) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `otj_report`.`skill_cluster`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `otj_report`.`skill_cluster` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `assessment_report_id` INT NULL,
  `features` VARCHAR(100) NULL,
  `topicwise_score` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `assessment_report_fk_idx` (`assessment_report_id` ASC) VISIBLE,
  CONSTRAINT `assessment_report_fk`
    FOREIGN KEY (`assessment_report_id`)
    REFERENCES `otj_report`.`assessment_report` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `otj_report`.`assessment_report`
-- -----------------------------------------------------
START TRANSACTION;
USE `otj_report`;
INSERT INTO `otj_report`.`assessment_report` (`id`, `associate_id`, `associate_name`, `associate_email`, `otj_name`, `otj_code`, `otj_received_date`, `planned_delivery_date`, `actual_delivery_date`, `score`, `status`) VALUES (DEFAULT, 123456, 'Shashikant Tagore', 'shashikant.tagore@cognizant.com', 'Fundamentals of mobile App development using Android core & tools  -On-The-Job Evaluation [101-Basics]', 'HMZ310317', NULL, NULL, NULL, 60, 'Completed');

COMMIT;


-- -----------------------------------------------------
-- Data for table `otj_report`.`skill_cluster`
-- -----------------------------------------------------
START TRANSACTION;
USE `otj_report`;
INSERT INTO `otj_report`.`skill_cluster` (`id`, `assessment_report_id`, `features`, `topicwise_score`) VALUES (DEFAULT, 1, 'Develop User interfaces for all the pages', 94);
INSERT INTO `otj_report`.`skill_cluster` (`id`, `assessment_report_id`, `features`, `topicwise_score`) VALUES (DEFAULT, 1, 'Functionalities', 79);
INSERT INTO `otj_report`.`skill_cluster` (`id`, `assessment_report_id`, `features`, `topicwise_score`) VALUES (DEFAULT, 1, 'Architecture Pattern', 80);
INSERT INTO `otj_report`.`skill_cluster` (`id`, `assessment_report_id`, `features`, `topicwise_score`) VALUES (DEFAULT, 1, 'Performance Best practices / Others', 80);

COMMIT;

