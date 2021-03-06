-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`book`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`book` (
  `bookid` INT NOT NULL,
  `bookname` VARCHAR(45) NULL,
  `publisher` VARCHAR(45) NULL,
  `price` INT NULL,
  PRIMARY KEY (`bookid`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`customer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`customer` (
  `custid` INT NOT NULL,
  `name` VARCHAR(25) NULL,
  `address` VARCHAR(50) NULL,
  `phone` VARCHAR(13) NULL,
  PRIMARY KEY (`custid`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`orders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`orders` (
  `orderid` INT NOT NULL,
  `custid` INT NULL,
  `bookid` INT NULL,
  `saleprice` INT NULL,
  `orderdate` DATE NULL,
  `book_bookid` INT NOT NULL,
  `customer_custid` INT NOT NULL,
  PRIMARY KEY (`orderid`),
  INDEX `fk_orders_book_idx` (`book_bookid` ASC) VISIBLE,
  INDEX `fk_orders_customer1_idx` (`customer_custid` ASC) VISIBLE,
  CONSTRAINT `fk_orders_book`
    FOREIGN KEY (`book_bookid`)
    REFERENCES `mydb`.`book` (`bookid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_orders_customer1`
    FOREIGN KEY (`customer_custid`)
    REFERENCES `mydb`.`customer` (`custid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
