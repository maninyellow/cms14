create database das charset utf8;

create user 'das' IDENTIFIED BY 'das';

GRANT ALL PRIVILEGES ON das.* TO das@'%' IDENTIFIED BY 'das';

use das;

CREATE TABLE `device_status` (
  `id` int(11) NOT NULL auto_increment,
  `standard_number` varchar(32) NOT NULL,
  `rec_time` datetime NOT NULL,
  `type` smallint(6) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `comm_status` tinyint(4) DEFAULT NULL,  
  `organ` varchar(32) NOT NULL,  
  PRIMARY KEY  (`id`,`rec_time`),
  KEY `standard_number` (`standard_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 
PARTITION BY RANGE COLUMNS(rec_time) (
  PARTITION device_status201301 VALUES LESS THAN ('2013-01-01')
);
alter table `device_status` add index `organ` (`organ`);