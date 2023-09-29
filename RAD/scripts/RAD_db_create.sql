-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema RAD_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema RAD_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `RAD_db` DEFAULT CHARACTER SET utf8 ;
USE `RAD_db` ;

-- -----------------------------------------------------
-- Table `RAD_db`.`roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RAD_db`.`roles` (
  `role_id` INT NOT NULL AUTO_INCREMENT,
  `role_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`role_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RAD_db`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RAD_db`.`users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(30) NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `password` VARCHAR(64) NOT NULL,
  `role_id` INT NOT NULL,
  PRIMARY KEY (`user_id`),
  INDEX `role_id_idx` (`role_id` ASC) VISIBLE,
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE,
  CONSTRAINT `role_id`
    FOREIGN KEY (`role_id`)
    REFERENCES `RAD_db`.`roles` (`role_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RAD_db`.`device_status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RAD_db`.`device_status` (
  `device_status_id` INT NOT NULL AUTO_INCREMENT,
  `device_status_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`device_status_id`),
  UNIQUE INDEX `status_name_UNIQUE` (`device_status_name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RAD_db`.`categories`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RAD_db`.`categories` (
  `category_id` INT NOT NULL AUTO_INCREMENT,
  `category_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`category_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RAD_db`.`devices`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RAD_db`.`devices` (
  `device_id` INT NOT NULL AUTO_INCREMENT,
  `inventory_id` VARCHAR(8) NOT NULL,
  `inventory_code` VARCHAR(9) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `brand` VARCHAR(45) NOT NULL,
  `model` VARCHAR(45) NOT NULL,
  `serial_nr` VARCHAR(45) NOT NULL,
  `room_nr` VARCHAR(10) NOT NULL,
  `buy_date` DATE NOT NULL,
  `log_date` DATE NOT NULL,
  `disposal_date` DATE NULL,
  `price` DECIMAL(5,2) NOT NULL,
  `device_status_id` INT NOT NULL,
  `comments` TEXT(300) NULL,
  `category_id` INT NOT NULL,
  PRIMARY KEY (`device_id`),
  INDEX `category_id_idx` (`category_id` ASC) VISIBLE,
  INDEX `status_id_idx` (`device_status_id` ASC) VISIBLE,
  UNIQUE INDEX `inventory_id_UNIQUE` (`inventory_id` ASC) VISIBLE,
  UNIQUE INDEX `inventory_code_UNIQUE` (`inventory_code` ASC) VISIBLE,
  CONSTRAINT `status_id`
    FOREIGN KEY (`device_status_id`)
    REFERENCES `RAD_db`.`device_status` (`device_status_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `category_id`
    FOREIGN KEY (`category_id`)
    REFERENCES `RAD_db`.`categories` (`category_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RAD_db`.`reservation_status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RAD_db`.`reservation_status` (
  `reservation_status_id` INT NOT NULL AUTO_INCREMENT,
  `reservation_status_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`reservation_status_id`),
  UNIQUE INDEX `reservation_status_name_UNIQUE` (`reservation_status_name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RAD_db`.`reservations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RAD_db`.`reservations` (
  `reservation_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `device_id` INT NOT NULL,
  `start_date` DATE NOT NULL,
  `end_date` DATE NOT NULL,
  `reservation_status_id` INT NOT NULL,
  PRIMARY KEY (`reservation_id`),
  INDEX `device_id_idx` (`device_id` ASC) VISIBLE,
  INDEX `reservation_status_id_idx` (`reservation_status_id` ASC) VISIBLE,
  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `RAD_db`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `device_id`
    FOREIGN KEY (`device_id`)
    REFERENCES `RAD_db`.`devices` (`device_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `reservation_status_id`
    FOREIGN KEY (`reservation_status_id`)
    REFERENCES `RAD_db`.`reservation_status` (`reservation_status_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

DELIMITER //

CREATE EVENT DailyStatusUpdate
ON SCHEDULE EVERY 1 DAY
STARTS CURRENT_TIMESTAMP
DO
BEGIN
    -- Update reservations with reservation_status_id = 2
    UPDATE reservations
    SET reservation_status_id = 3
    WHERE reservation_status_id = 2 AND end_date < CURDATE();

    -- Update associated device statuses to 5
    UPDATE devices
    SET status_id = 5
    WHERE device_id IN (
        SELECT device_id
        FROM reservations
        WHERE reservation_status_id = 3
    );
END;
//

DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
