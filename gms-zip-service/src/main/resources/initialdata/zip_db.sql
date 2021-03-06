CREATE TABLE IF NOT EXISTS `zip_db`.`zip` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `zip` VARCHAR(10) NOT NULL,
  `address` VARCHAR(150) NOT NULL,
  `comp` VARCHAR(100) NULL,
  `neighborhood` VARCHAR(70) NULL,
  `city` VARCHAR(70) NOT NULL,
  `state` VARCHAR(45) NOT NULL,
  `created_at` DATETIME NOT NULL,
  `updated_at` DATETIME NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `zip_UNIQUE` (`zip` ASC) VISIBLE)
ENGINE = InnoDB;