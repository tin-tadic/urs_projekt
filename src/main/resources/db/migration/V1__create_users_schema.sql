CREATE TABLE IF NOT EXISTS `users` (

`user_id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
`username` varchar(45) NOT NULL UNIQUE,
`password` varchar(64) NOT NULL,
`role` varchar(45) NOT NULL,
`enabled` tinyint(4) DEFAULT NULL

)ENGINE=InnoDB DEFAULT CHARSET=UTF8;