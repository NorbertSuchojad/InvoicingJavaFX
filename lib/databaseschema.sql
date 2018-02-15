-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema invoicing
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema invoicing
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `invoicing` DEFAULT CHARACTER SET utf8 ;
USE `invoicing` ;

-- -----------------------------------------------------
-- Table `invoicing`.`invoice`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `invoicing`.`invoice` (
  `id_invoice` INT(11) NOT NULL AUTO_INCREMENT,
  `service_name` VARCHAR(100) NOT NULL,
  `sales_type` VARCHAR(45) NOT NULL DEFAULT 'szt.',
  `sales_value` DOUBLE NULL DEFAULT '1',
  `sales_price` DOUBLE NOT NULL DEFAULT '1',
  `date_of_issue` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `payment_time` INT(11) NOT NULL DEFAULT '7',
  PRIMARY KEY (`id_invoice`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `invoicing`.`my_company`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `invoicing`.`my_company` (
  `my_nip` BIGINT(8) NOT NULL,
  `my_regon` INT(11) NOT NULL,
  `my_company_name` VARCHAR(200) NOT NULL,
  `my_address` VARCHAR(100) NOT NULL,
  `my_postcode` VARCHAR(6) NOT NULL,
  `my_post` VARCHAR(50) NOT NULL,
  `my_company_account` VARCHAR(26) NULL DEFAULT NULL,
  PRIMARY KEY (`my_nip`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `invoicing`.`other_company`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `invoicing`.`other_company` (
  `ot_nip` INT(11) NOT NULL,
  `ot_regon` INT(11) NOT NULL,
  `ot_company_name` VARCHAR(200) NOT NULL,
  `ot_address1` VARCHAR(100) NOT NULL,
  `ot_address2` VARCHAR(100) NULL DEFAULT NULL,
  `ot_postcode` VARCHAR(6) NOT NULL,
  `ot_post` VARCHAR(50) NOT NULL,
  `ot_company_account` VARCHAR(26) NULL DEFAULT NULL,
  PRIMARY KEY (`ot_nip`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `invoicing`.`services`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `invoicing`.`services` (
  `idservices` INT(11) NOT NULL,
  `service_name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`idservices`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `invoicing`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `invoicing`.`user` (
  `iduser` INT(11) NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(45) NOT NULL,
  `pass` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`iduser`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
