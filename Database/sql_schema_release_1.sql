-- MySQL Script generated by MySQL Workbench
-- Mon Oct  5 00:30:09 2015
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema RouterDB
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `RouterDB` ;

-- -----------------------------------------------------
-- Schema RouterDB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `RouterDB` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `RouterDB` ;

-- -----------------------------------------------------
-- Table `RouterDB`.`Route`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `RouterDB`.`Route` ;

CREATE TABLE IF NOT EXISTS `RouterDB`.`Route` (
  `RouteID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `RouteNo` INT NOT NULL,
  `RouteType` VARCHAR(45) NOT NULL,
  `RouteName` VARCHAR(250) NOT NULL,
  PRIMARY KEY (`RouteID`),
  UNIQUE INDEX `RouteID_UNIQUE` (`RouteID` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RouterDB`.`Station`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `RouterDB`.`Station` ;

CREATE TABLE IF NOT EXISTS `RouterDB`.`Station` (
  `StationID` INT NOT NULL,
  `CodeID` VARCHAR(45) NOT NULL,
  `Name` VARCHAR(250) NOT NULL,
  `Street` VARCHAR(250) NOT NULL,
  `Latitude` DECIMAL(22,12) NOT NULL,
  `Longitude` DECIMAL(22,12) NOT NULL,
  PRIMARY KEY (`StationID`),
  UNIQUE INDEX `StationID_UNIQUE` (`StationID` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RouterDB`.`PathInfo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `RouterDB`.`PathInfo` ;

CREATE TABLE IF NOT EXISTS `RouterDB`.`PathInfo` (
  `PathInfoID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `RouteID` INT UNSIGNED NOT NULL,
  `FromStationID` INT NULL,
  `ToStationID` INT NULL,
  `PathInfoNo` INT NULL,
  `MiddlePoint` VARCHAR(3000) NULL,
  PRIMARY KEY (`PathInfoID`),
  INDEX `fk_PathInfo_Route1_idx` (`RouteID` ASC),
  INDEX `fk_PathInfo_Station1_idx` (`FromStationID` ASC),
  INDEX `fk_PathInfo_Station2_idx` (`ToStationID` ASC),
  CONSTRAINT `fk_PathInfo_Route1`
    FOREIGN KEY (`RouteID`)
    REFERENCES `RouterDB`.`Route` (`RouteID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_PathInfo_Station1`
    FOREIGN KEY (`FromStationID`)
    REFERENCES `RouterDB`.`Station` (`StationID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_PathInfo_Station2`
    FOREIGN KEY (`ToStationID`)
    REFERENCES `RouterDB`.`Station` (`StationID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RouterDB`.`Trip`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `RouterDB`.`Trip` ;

CREATE TABLE IF NOT EXISTS `RouterDB`.`Trip` (
  `TridID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `TripNo` INT NULL,
  `StartTime` TIME NULL,
  `EndTime` TIME NULL,
  `RouteID` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`TridID`),
  UNIQUE INDEX `TridID_UNIQUE` (`TridID` ASC),
  INDEX `fk_Trip_Route_idx` (`RouteID` ASC),
  CONSTRAINT `fk_Trip_Route`
    FOREIGN KEY (`RouteID`)
    REFERENCES `RouterDB`.`Route` (`RouteID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RouterDB`.`Connection`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `RouterDB`.`Connection` ;

CREATE TABLE IF NOT EXISTS `RouterDB`.`Connection` (
  `ConnectionID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `PathInfoID` INT UNSIGNED NOT NULL,
  `TripID` INT UNSIGNED NOT NULL,
  `ArrivalTime` TIME NOT NULL,
  INDEX `fk_Connection_PathInfo1_idx` (`PathInfoID` ASC),
  INDEX `fk_Connection_Trip1_idx` (`TripID` ASC),
  PRIMARY KEY (`ConnectionID`),
  CONSTRAINT `fk_Connection_PathInfo1`
    FOREIGN KEY (`PathInfoID`)
    REFERENCES `RouterDB`.`PathInfo` (`PathInfoID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Connection_Trip1`
    FOREIGN KEY (`TripID`)
    REFERENCES `RouterDB`.`Trip` (`TridID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RouterDB`.`Temp`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `RouterDB`.`Temp` ;

CREATE TABLE IF NOT EXISTS `RouterDB`.`Temp` (
  `TempID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `TempNo` INT NULL,
  `Date` TIME NULL,
  PRIMARY KEY (`TempID`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
