-- -----------------------------------------------------
-- Table `db_azship`.`srm_department`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_srm`.`srm_department` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(400) NULL,
  `description` VARCHAR(4000) NULL,
  `change_date` TIMESTAMP NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `db_azship`.`srm_product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_srm`.`srm_product` (
  `isbn` VARCHAR(100) NOT NULL,
  `name` VARCHAR(400) NOT NULL,
  `department_id` INT NOT NULL,
  `amount` INT NOT NULL,
  `cost` DOUBLE NOT NULL,
  `change_date` TIMESTAMP NOT NULL,
  PRIMARY KEY (`isbn`),
  INDEX `fk_product_department_idx` (`department_id` ASC),
  CONSTRAINT `fk_shipping_destination_place1`
    FOREIGN KEY (`department_id`)
    REFERENCES `db_srm`.`srm_department` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
