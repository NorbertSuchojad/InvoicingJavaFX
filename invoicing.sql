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
-- Table `invoicing`.`my_company`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `invoicing`.`my_company` (
  `my_nip` BIGINT(8) NOT NULL,
  `my_regon` INT(11) NOT NULL,
  `my_company_name` VARCHAR(200) NOT NULL,
  `my_address1` VARCHAR(100) NOT NULL,
  `my_address2` VARCHAR(100) NULL DEFAULT NULL,
  `my_postcode1` VARCHAR(2) NOT NULL,
  `my_postcode2` VARCHAR(3) NOT NULL,
  `my_post` VARCHAR(50) NOT NULL,
  `my_company_account` VARCHAR(26) NULL DEFAULT NULL,
  PRIMARY KEY (`my_nip`))
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
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
