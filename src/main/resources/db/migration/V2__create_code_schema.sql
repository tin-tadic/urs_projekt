CREATE TABLE IF NOT EXISTS `code` (

`id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
`code` varchar(20),
`valid_until` DATETIME,
`owner` varchar(20) NOT NULL,
`participants` varchar(500)

)ENGINE=InnoDB DEFAULT CHARSET=UTF8;