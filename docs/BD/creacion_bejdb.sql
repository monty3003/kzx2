SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `bejdb` DEFAULT CHARACTER SET utf8 ;
USE `bejdb` ;

-- -----------------------------------------------------
-- Table `bejdb`.`categoria`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bejdb`.`categoria` ;

CREATE  TABLE IF NOT EXISTS `bejdb`.`categoria` (
  `id` INT(11) NOT NULL ,
  `descripcion` VARCHAR(50) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = latin1
COMMENT = 'Categoria de objetos en general.' ;


-- -----------------------------------------------------
-- Table `bejdb`.`persona`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bejdb`.`persona` ;

CREATE  TABLE IF NOT EXISTS `bejdb`.`persona` (
  `id` INT(11) NOT NULL AUTO_INCREMENT ,
  `documento` VARCHAR(11) NOT NULL ,
  `fisica` CHAR(1) NOT NULL DEFAULT 'S' ,
  `nombre` VARCHAR(50) NOT NULL ,
  `direccion1` VARCHAR(50) NOT NULL ,
  `direccion2` VARCHAR(50) NULL DEFAULT NULL ,
  `telefono_fijo` VARCHAR(11) NULL DEFAULT NULL ,
  `telefono_movil` VARCHAR(13) NULL DEFAULT NULL ,
  `email` VARCHAR(25) NULL DEFAULT NULL ,
  `fecha_ingreso` DATE NOT NULL ,
  `ruc` VARCHAR(10) NULL DEFAULT NULL ,
  `contacto` VARCHAR(45) NULL DEFAULT NULL ,
  `fecha_nacimiento` DATE NULL DEFAULT NULL ,
  `estado_civil` CHAR(1) NOT NULL DEFAULT 'S' ,
  `profesion` VARCHAR(40) NULL DEFAULT NULL ,
  `tratamiento` VARCHAR(15) NOT NULL ,
  `sexo` CHAR(1) NULL DEFAULT 'H' ,
  `hijos` TINYINT(4) NULL DEFAULT NULL ,
  `habilitado` CHAR(1) NOT NULL DEFAULT 'S' ,
  `categoria` INT(11) NOT NULL ,
  PRIMARY KEY (`id`, `documento`) ,
  UNIQUE INDEX `documento_UNIQUE` (`documento` ASC) ,
  INDEX `categoria_fk` (`categoria` ASC) ,
  CONSTRAINT `categoria_fk`
    FOREIGN KEY (`categoria` )
    REFERENCES `bejdb`.`categoria` (`id` )
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = MyISAM
AUTO_INCREMENT = 23
DEFAULT CHARACTER SET = latin1, 
COMMENT = 'Tabla de Personas - Clasificacion de Fisicas y Juridicas' ;


-- -----------------------------------------------------
-- Table `bejdb`.`transaccion`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bejdb`.`transaccion` ;

CREATE  TABLE IF NOT EXISTS `bejdb`.`transaccion` (
  `id` INT(11) NOT NULL AUTO_INCREMENT ,
  `codigo` INT(11) NOT NULL ,
  `comprobante` VARCHAR(45) NOT NULL ,
  `fechaOperacion` DATETIME NOT NULL ,
  `fechaEntrega` DATETIME NOT NULL ,
  `vendedor` INT(11) NOT NULL ,
  `comprador` INT(11) NOT NULL ,
  `anulado` CHAR(1) NOT NULL ,
  `sub_total_exentas` DECIMAL(10,2) NOT NULL ,
  `sub_total_gravadas_10` DECIMAL(10,2) NOT NULL ,
  `sub_total_gravadas_5` DECIMAL(10,2) NOT NULL ,
  `sub_total` DECIMAL(10,2) NOT NULL ,
  `total_iva5` DECIMAL(10,2) NOT NULL ,
  `total_iva10` DECIMAL(10,2) NOT NULL ,
  `total_iva` DECIMAL(10,2) NOT NULL ,
  `descuento` FLOAT NOT NULL ,
  `total` DECIMAL(10,2) NOT NULL ,
  `total_descuento` DECIMAL(10,2) NOT NULL ,
  `total_pagado` DECIMAL(10,2) NOT NULL ,
  `entrega_inicial` DECIMAL(10,2) NOT NULL ,
  `cuotas` TINYINT(4) NOT NULL ,
  `monto_cuota_igual` DECIMAL(10,2) NOT NULL ,
  `saldado` CHAR(1) NOT NULL ,
  `cantidad_items` TINYINT(4) NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_transaccion` (`codigo` ASC) ,
  INDEX `fk_persona` (`vendedor` ASC, `comprador` ASC) ,
  CONSTRAINT `fk_transaccion`
    FOREIGN KEY (`codigo` )
    REFERENCES `bejdb`.`categoria` (`id` )
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_persona`
    FOREIGN KEY (`vendedor` , `comprador` )
    REFERENCES `bejdb`.`persona` (`id` , `id` )
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = MyISAM
AUTO_INCREMENT = 74
DEFAULT CHARACTER SET = latin1, 
COMMENT = 'Tabla de Registro de Transacciones en Gral.' ;


-- -----------------------------------------------------
-- Table `bejdb`.`credito`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bejdb`.`credito` ;

CREATE  TABLE IF NOT EXISTS `bejdb`.`credito` (
  `id` INT(11) NOT NULL AUTO_INCREMENT ,
  `categoria` INT(11) NOT NULL ,
  `transaccion` INT(11) NOT NULL COMMENT '	' ,
  `fecha_inicio` DATE NOT NULL ,
  `fecha_fin` DATE NOT NULL ,
  `sistema_credito` INT(11) NOT NULL ,
  `tan` FLOAT NOT NULL ,
  `tae` FLOAT NOT NULL ,
  `capital` DECIMAL(11,2) NOT NULL ,
  `amortizacion` TINYINT(4) NOT NULL ,
  `credito_total` DECIMAL(11,2) NOT NULL ,
  `total_amortizado_pagado` DECIMAL(11,2) NOT NULL ,
  `total_intereses_pagado` DECIMAL(11,2) NOT NULL ,
  `total_intereses_pagado_multa` DECIMAL(11,2) NULL DEFAULT NULL ,
  `fecha_ultimo_pago` DATE NULL DEFAULT NULL ,
  `cuotas_atrasadas` TINYINT(4) NULL DEFAULT NULL ,
  `estado` INT(11) NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_categoria` (`categoria` ASC) ,
  INDEX `fk_transaccion` (`transaccion` ASC) ,
  INDEX `fk_estado` (`estado` ASC) ,
  CONSTRAINT `fk_categoria`
    FOREIGN KEY (`categoria` )
    REFERENCES `bejdb`.`categoria` (`id` )
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_transaccion`
    FOREIGN KEY (`transaccion` )
    REFERENCES `bejdb`.`transaccion` (`id` )
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_estado`
    FOREIGN KEY (`estado` )
    REFERENCES `bejdb`.`categoria` (`id` )
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = MyISAM
AUTO_INCREMENT = 31
DEFAULT CHARACTER SET = latin1, 
COMMENT = 'Tabla de Creditos de compras y ventas' ;


-- -----------------------------------------------------
-- Table `bejdb`.`factura`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bejdb`.`factura` ;

CREATE  TABLE IF NOT EXISTS `bejdb`.`factura` (
  `id` INT(11) NOT NULL ,
  `numero` VARCHAR(45) NOT NULL ,
  `valido_hasta` DATE NOT NULL ,
  `categoria` INT(11) NOT NULL ,
  `sub_total_exentas` DECIMAL(10,0) NOT NULL ,
  `sub_total_gravadas_10` DECIMAL(10,0) NOT NULL ,
  `sub_total_gravadas_5` DECIMAL(10,0) NOT NULL ,
  `sub_total` DECIMAL(10,0) NOT NULL ,
  `total_iva` DECIMAL(10,0) NOT NULL ,
  `total_pagado` DECIMAL(10,0) NOT NULL ,
  `descuento` FLOAT NOT NULL ,
  `saldado` CHAR(1) NOT NULL ,
  PRIMARY KEY (`id`, `numero`) ,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) ,
  INDEX `fk_categoria` (`categoria` ASC) ,
  CONSTRAINT `fk_categoria`
    FOREIGN KEY (`categoria` )
    REFERENCES `bejdb`.`categoria` (`id` )
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = MyISAM
DEFAULT CHARACTER SET = latin1, 
COMMENT = 'Tabla de Facturas compra y venta' ;


-- -----------------------------------------------------
-- Table `bejdb`.`financiacion`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bejdb`.`financiacion` ;

CREATE  TABLE IF NOT EXISTS `bejdb`.`financiacion` (
  `id` INT(11) NOT NULL AUTO_INCREMENT ,
  `credito` INT(11) NOT NULL ,
  `numero_cuota` TINYINT(4) NOT NULL ,
  `capital` DECIMAL(11,2) NOT NULL ,
  `interes` DECIMAL(11,2) NOT NULL ,
  `cuota_neta` DECIMAL(11,2) NOT NULL ,
  `total_a_pagar` DECIMAL(11,2) NOT NULL ,
  `ajuste_redondeo` DECIMAL(11,2) NOT NULL ,
  `fecha_vencimiento` DATE NOT NULL ,
  `fecha_pago` DATE NULL DEFAULT NULL ,
  `interes_mora` DECIMAL(11,2) NULL DEFAULT NULL ,
  `total_pagado` DECIMAL(11,2) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_credito` (`credito` ASC) ,
  CONSTRAINT `fk_credito`
    FOREIGN KEY (`credito` )
    REFERENCES `bejdb`.`credito` (`id` )
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = MyISAM
AUTO_INCREMENT = 245
DEFAULT CHARACTER SET = latin1, 
COMMENT = 'Tabla de manejo de cuotas' ;


-- -----------------------------------------------------
-- Table `bejdb`.`moto`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bejdb`.`moto` ;

CREATE  TABLE IF NOT EXISTS `bejdb`.`moto` (
  `codigo` VARCHAR(14) NOT NULL ,
  `codigo_fabrica` VARCHAR(20) NOT NULL ,
  `marca` VARCHAR(20) NOT NULL ,
  `modelo` VARCHAR(20) NOT NULL ,
  `color` VARCHAR(20) NOT NULL ,
  `fabricante` INT(11) NOT NULL ,
  `categoria` INT(11) NOT NULL ,
  PRIMARY KEY (`codigo`) ,
  INDEX `categoria_fk` (`categoria` ASC) ,
  CONSTRAINT `categoria_fk`
    FOREIGN KEY (`categoria` )
    REFERENCES `bejdb`.`categoria` (`id` )
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = MyISAM
DEFAULT CHARACTER SET = latin1, 
COMMENT = 'Tabla de Motos' ;


-- -----------------------------------------------------
-- Table `bejdb`.`ubicacion`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bejdb`.`ubicacion` ;

CREATE  TABLE IF NOT EXISTS `bejdb`.`ubicacion` (
  `id` INT(11) NOT NULL AUTO_INCREMENT ,
  `descripcion` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = MyISAM
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = latin1, 
COMMENT = 'Ubicacion fisica de las mercaderias' ;


-- -----------------------------------------------------
-- Table `bejdb`.`motostock`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bejdb`.`motostock` ;

CREATE  TABLE IF NOT EXISTS `bejdb`.`motostock` (
  `id` INT(11) NOT NULL AUTO_INCREMENT ,
  `moto` VARCHAR(14) NOT NULL ,
  `motor` VARCHAR(25) NULL DEFAULT NULL ,
  `chasis` VARCHAR(25) NOT NULL ,
  `compra` INT(11) NOT NULL ,
  `venta` INT(11) NULL DEFAULT NULL ,
  `costo` DECIMAL(10,2) NOT NULL ,
  `precio_venta` DECIMAL(10,2) NOT NULL ,
  `ubicacion` INT(11) NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `moto_fk` (`moto` ASC) ,
  INDEX `ubicacion_fk` (`ubicacion` ASC) ,
  INDEX `compra_fk` (`compra` ASC) ,
  INDEX `venta_fk` (`venta` ASC) ,
  CONSTRAINT `moto_fk`
    FOREIGN KEY (`moto` )
    REFERENCES `bejdb`.`moto` (`codigo` )
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `ubicacion_fk`
    FOREIGN KEY (`ubicacion` )
    REFERENCES `bejdb`.`ubicacion` (`id` )
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `compra_fk`
    FOREIGN KEY (`compra` )
    REFERENCES `bejdb`.`transaccion` (`id` )
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `venta_fk`
    FOREIGN KEY (`venta` )
    REFERENCES `bejdb`.`transaccion` (`id` )
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = MyISAM
AUTO_INCREMENT = 50
DEFAULT CHARACTER SET = latin1, 
COMMENT = 'tabla de stock de motos' ;


-- -----------------------------------------------------
-- Table `bejdb`.`pago`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bejdb`.`pago` ;

CREATE  TABLE IF NOT EXISTS `bejdb`.`pago` (
  `id` INT(11) NOT NULL AUTO_INCREMENT ,
  `fecha` DATE NOT NULL ,
  `financiacion` INT(11) NOT NULL ,
  `total_pagado` DECIMAL(11,2) NOT NULL ,
  `es_pago_parcial` BINARY(1) NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_financiacion` (`financiacion` ASC) ,
  CONSTRAINT `fk_financiacion`
    FOREIGN KEY (`financiacion` )
    REFERENCES `bejdb`.`financiacion` (`id` )
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = MyISAM
DEFAULT CHARACTER SET = latin1, 
COMMENT = 'Tabla donde se guardan los pagos de creditos' ;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
