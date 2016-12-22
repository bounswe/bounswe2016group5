CREATE TABLE IF NOT EXISTS `group5db`.`kerim_save` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `cid` TEXT NULL DEFAULT NULL,
  `item` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `item_idx` (`item` ASC),
  CONSTRAINT `item`
    FOREIGN KEY (`item`)
    REFERENCES `group5db`.`kerim_automobile` (`item`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 44
DEFAULT CHARACTER SET = latin1

CREATE TABLE IF NOT EXISTS `group5db`.`kerim_automobile` (
  `item` INT(11) NOT NULL,
  `itemlabel` TEXT NULL DEFAULT NULL,
  `commons` TEXT NULL DEFAULT NULL,
  `manufacturer` TEXT NULL DEFAULT NULL,
  `manufacturerlabel` TEXT NULL DEFAULT NULL,
  `type_` TEXT NULL DEFAULT NULL,
  `typelabel` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`item`),
  UNIQUE INDEX `item_UNIQUE` (`item` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8