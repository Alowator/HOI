CREATE TABLE `people`.`person` (
  `id` INT NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `gender` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);

CREATE TABLE `people`.`spouse` (
  `wife` INT NOT NULL,
  `husband` INT NOT NULL,
  UNIQUE INDEX `wife_UNIQUE` (`wife` ASC) VISIBLE,
  UNIQUE INDEX `husband_UNIQUE` (`husband` ASC) VISIBLE);

CREATE TABLE `people`.`mother` (
  `child` INT NOT NULL,
  `mother` INT NOT NULL,
  UNIQUE INDEX `child_UNIQUE` (`child` ASC));

CREATE TABLE `people`.`father` (
  `child` INT NOT NULL,
  `father` INT NOT NULL,
  UNIQUE INDEX `child_UNIQUE` (`child` ASC));

CREATE TABLE `people`.`son` (
  `son` INT NOT NULL,
  `parent` INT NOT NULL);

CREATE TABLE `people`.`daughter` (
  `daughter` INT NOT NULL,
  `parent` INT NOT NULL);

CREATE TABLE `people`.`sister` (
  `sibling` INT NOT NULL,
  `sister` INT NOT NULL);

CREATE TABLE `people`.`brother` (
  `sibling` INT NOT NULL,
  `brother` INT NOT NULL);


