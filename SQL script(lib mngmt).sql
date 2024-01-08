-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema digital_library
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema digital_library
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `digital_library` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `digital_library` ;

-- -----------------------------------------------------
-- Table `digital_library`.`books`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `digital_library`.`books` (
  `Book_id` INT NOT NULL AUTO_INCREMENT,
  `Book_name` VARCHAR(45) NOT NULL,
  `Category` VARCHAR(45) NOT NULL,
  `Book_rent` INT NULL DEFAULT NULL,
  PRIMARY KEY (`Book_id`),
  UNIQUE INDEX `Book_id_UNIQUE` (`Book_id` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `digital_library`.`customers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `digital_library`.`customers` (
  `customer_id` INT NOT NULL AUTO_INCREMENT,
  `Cus_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`customer_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `digital_library`.`rent`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `digital_library`.`rent` (
  `rent_id` INT NOT NULL AUTO_INCREMENT,
  `book_id` INT NULL DEFAULT NULL,
  `customer_id` INT NULL DEFAULT NULL,
  `issue_date` DATE NULL DEFAULT NULL,
  `due_date` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`rent_id`),
  INDEX `book_id_idx` (`book_id` ASC) VISIBLE,
  INDEX `customer_fk_idx` (`customer_id` ASC) VISIBLE,
  CONSTRAINT `book_fk`
    FOREIGN KEY (`book_id`)
    REFERENCES `digital_library`.`books` (`Book_id`),
  CONSTRAINT `customer_fk`
    FOREIGN KEY (`customer_id`)
    REFERENCES `digital_library`.`customers` (`customer_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
