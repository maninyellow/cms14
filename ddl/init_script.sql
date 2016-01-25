insert into sv_standard_sequence (name,current_value,data_increment) VALUES ('Camera',100,1);
insert into sv_standard_sequence (name,current_value,data_increment) VALUES ('Ccs',100,1);
insert into sv_standard_sequence (name,current_value,data_increment) VALUES ('Crs',100,1);
insert into sv_standard_sequence (name,current_value,data_increment) VALUES ('Dvr',100,1);
insert into sv_standard_sequence (name,current_value,data_increment) VALUES ('Dws',100,1);
insert into sv_standard_sequence (name,current_value,data_increment) VALUES ('Mss',100,1);
insert into sv_standard_sequence (name,current_value,data_increment) VALUES ('Organ',100,1);
insert into sv_standard_sequence (name,current_value,data_increment) VALUES ('Pts',100,1);
insert into sv_standard_sequence (name,current_value,data_increment) VALUES ('User',100,1);
insert into sv_standard_sequence (name,current_value,data_increment) VALUES ('Monitor',100,1);
insert into sv_standard_sequence (name,current_value,data_increment) VALUES ('Rms',100,1);
insert into sv_standard_sequence (name,current_value,data_increment) VALUES ('GPSDevice',100,1);
insert into sv_standard_sequence (name,current_value,data_increment) VALUES ('Rss',100,1);
insert into sv_standard_sequence (name,current_value,data_increment) VALUES ('Srs',100,1);

insert into sv_standard_sequence (name,current_value,data_increment) VALUES ('Das',100,1);
insert into sv_standard_sequence (name,current_value,data_increment) VALUES ('Ens',100,1);

insert into sv_standard_sequence (name,current_value,data_increment) VALUES ('Unit',100,1);
insert into sv_standard_sequence (name,current_value,data_increment) VALUES ('Team',100,1);
insert into sv_standard_sequence (name,current_value,data_increment) VALUES ('Resource',100,1);
insert into sv_standard_sequence (name,current_value,data_increment) VALUES ('Vehicle',100,1);
insert into sv_standard_sequence (name,current_value,data_increment) VALUES ('ResourceUser',100,1);
insert into sv_standard_sequence (name,current_value,data_increment) VALUES ('SolarBattery',100,1);

insert into sv_organ (id,standard_number,name,parent_id,path,fax,contact,phone,address,area_code,image_id,create_time,note,deep,type) values ('10010000000000000000','251000000000000000','根机构',null,'/10010000000000000000','02888888888','admin','02888888888','sc-smartvision','610000','1','1368066067374','根机构','1','0');


insert into sv_user (id,standard_number,ccs_id,logon_name,name,password,sex,email,phone,address,organ_id,image_id,priority,create_time,status,note,max_connect) VALUES ('10020000000000000000','251000000090000000',null,'admin','admin','e10adc3949ba59abbe56e057f20f883e',0,'admin@sc-smartvision.com','02888888888','四川智能视讯信息技术有限公司','10010000000000000000',2,3,1368066067374,1,'admin',50);


insert into sv_image_resource (id,image_name,image_size,image_format,user_id,create_time,content) VALUES (1,'根机构',0,null,'10020000000000000000',1368066067374,null);
insert into sv_image_resource (id,image_name,image_size,image_format,user_id,create_time,content) VALUES (2,'admin',0,null,'10020000000000000000',1368066067374,null);


insert into sv_role (id,name,type,organ_id,note,create_time) VALUES (1,'系统管理员',90,'10010000000000000000','系统管理员',1368066067374);
insert into sv_role (id,name,type,organ_id,note,create_time) VALUES (2,'高级用户',92,'10010000000000000000','高级用户',1368066067374);
insert into sv_role (id,name,type,organ_id,note,create_time) VALUES (3,'普通用户',93,'10010000000000000000','普通用户',1368066067374);
insert into sv_role (id,name,type,organ_id,note,create_time) VALUES (4,'互联平台角色',96,'10010000000000000000','互联平台的资源权限控制角色',1368066067374);


insert into r_user_role (user_id,role_id) VALUES ('10020000000000000000',1);


insert into sv_menu_operation (id,menu_name,menu_code,menu_action,parent_id,note) VALUES (1,'video','1',null,null,'视频浏览');
insert into sv_menu_operation (id,menu_name,menu_code,menu_action,parent_id,note) VALUES (2,'alarm','2',null,null,'报警管理');
insert into sv_menu_operation (id,menu_name,menu_code,menu_action,parent_id,note) VALUES (3,'gis','3',null,null,'电子地图');
insert into sv_menu_operation (id,menu_name,menu_code,menu_action,parent_id,note) VALUES (4,'display','4',null,null,'电视墙');

insert into r_role_menu_permission (role_id,menu_id) VALUES (1,1);
insert into r_role_menu_permission (role_id,menu_id) VALUES (1,2);
insert into r_role_menu_permission (role_id,menu_id) VALUES (1,3);
insert into r_role_menu_permission (role_id,menu_id) VALUES (1,4);
insert into r_role_menu_permission (role_id,menu_id) VALUES (2,1);
insert into r_role_menu_permission (role_id,menu_id) VALUES (2,2);
insert into r_role_menu_permission (role_id,menu_id) VALUES (2,3);
insert into r_role_menu_permission (role_id,menu_id) VALUES (2,4);
insert into r_role_menu_permission (role_id,menu_id) VALUES (3,1);
insert into r_role_menu_permission (role_id,menu_id) VALUES (3,2);
insert into r_role_menu_permission (role_id,menu_id) VALUES (3,3);
insert into r_role_menu_permission (role_id,menu_id) VALUES (3,4);


insert into sv_manufacturer (id,name,protocol,contact,address,phone,create_time,note) VALUES (1,'海康威视','haikang',null,null,null,1368066067374,'HIKVISION');
insert into sv_manufacturer (id,name,protocol,contact,address,phone,create_time,note) VALUES (2,'大华','dahua',null,null,null,1368066067374,'dahua');
insert into sv_manufacturer (id,name,protocol,contact,address,phone,create_time,note) VALUES (3,'安讯士','axis',null,null,null,1368066067374,'AXIS');
insert into sv_manufacturer (id,name,protocol,contact,address,phone,create_time,note) VALUES (4,'博世','bosch',null,null,null,1368066067374,'BOSCH');
insert into sv_manufacturer (id,name,protocol,contact,address,phone,create_time,note) VALUES (5,'佳能','canon',null,null,null,1368066067374,'Canon');
insert into sv_manufacturer (id,name,protocol,contact,address,phone,create_time,note) VALUES (6,'思科','cisco',null,null,null,1368066067374,'cisco');
insert into sv_manufacturer (id,name,protocol,contact,address,phone,create_time,note) VALUES (7,'霍尼韦尔','honeywell',null,null,null,1368066067374,'Honeywell');
insert into sv_manufacturer (id,name,protocol,contact,address,phone,create_time,note) VALUES (8,'华为','huawei',null,null,null,1368066067374,'HUAWEI');
insert into sv_manufacturer (id,name,protocol,contact,address,phone,create_time,note) VALUES (9,'LG','lg',null,null,null,1368066067374,'乐金');
insert into sv_manufacturer (id,name,protocol,contact,address,phone,create_time,note) VALUES (10,'松下','panasonic',null,null,null,1368066067374,'Panasonic');
insert into sv_manufacturer (id,name,protocol,contact,address,phone,create_time,note) VALUES (11,'三星','samsung',null,null,null,1368066067374,'SAMSUNG');
insert into sv_manufacturer (id,name,protocol,contact,address,phone,create_time,note) VALUES (12,'派尔高','pelco',null,null,null,1368066067374,'PELCO');
insert into sv_manufacturer (id,name,protocol,contact,address,phone,create_time,note) VALUES (13,'西门子','siemens',null,null,null,1368066067374,'SIEMENS');
insert into sv_manufacturer (id,name,protocol,contact,address,phone,create_time,note) VALUES (14,'索尼','sony',null,null,null,1368066067374,'SONY');
insert into sv_manufacturer (id,name,protocol,contact,address,phone,create_time,note) VALUES (15,'德州仪器','texasinstruments',null,null,null,1368066067374,'Texas Instruments');
insert into sv_manufacturer (id,name,protocol,contact,address,phone,create_time,note) VALUES (16,'中威电子','zhongwei',null,null,null,1368066067374,'ZHONGWEI');
insert into sv_manufacturer (id,name,protocol,contact,address,phone,create_time,note) VALUES (17,'sv_dvr','sv_dvr',null,null,null,1368066067374,'sv_dvr');
insert into sv_manufacturer (id,name,protocol,contact,address,phone,create_time,note) VALUES (18,'欧迈特','omate',null,null,null,1368066067374,'OMATE');
insert into sv_manufacturer (id,name,protocol,contact,address,phone,create_time,note) VALUES (19,'华鼎','huading',null,null,null,1368066067374,'HUADING');

insert into sv_device_update_listener (id,update_time,crs_update_time) VALUES ('1','0',0);

insert into sv_resource_operation (id,resource_type,operation_code,operation_name,note) VALUES ('1','2','1','查看权限','');
insert into sv_resource_operation (id,resource_type,operation_code,operation_name,note) VALUES ('2','2','2','操作权限','');
insert into sv_resource_operation (id,resource_type,operation_code,operation_name,note) VALUES ('3','2','4','设置权限','');
insert into sv_resource_operation (id,resource_type,operation_code,operation_name,note) VALUES ('4','10','1','查看权限','');
insert into sv_resource_operation (id,resource_type,operation_code,operation_name,note) VALUES ('5','11','1','查看权限','');
insert into sv_resource_operation (id,resource_type,operation_code,operation_name,note) VALUES ('6','12','1','查看权限','');
insert into sv_resource_operation (id,resource_type,operation_code,operation_name,note) VALUES ('7','13','1','查看权限','');
insert into sv_resource_operation (id,resource_type,operation_code,operation_name,note) VALUES ('8','14','1','查看权限','');
insert into sv_resource_operation (id,resource_type,operation_code,operation_name,note) VALUES ('9','15','1','查看权限','');
insert into sv_resource_operation (id,resource_type,operation_code,operation_name,note) VALUES ('10','16','1','查看权限','');
insert into sv_resource_operation (id,resource_type,operation_code,operation_name,note) VALUES ('11','17','1','查看权限','');
insert into sv_resource_operation (id,resource_type,operation_code,operation_name,note) VALUES ('12','17','2','操作权限','');
insert into sv_resource_operation (id,resource_type,operation_code,operation_name,note) VALUES ('13','18','1','查看权限','');
insert into sv_resource_operation (id,resource_type,operation_code,operation_name,note) VALUES ('14','18','2','操作权限','');
insert into sv_resource_operation (id,resource_type,operation_code,operation_name,note) VALUES ('15','19','1','查看权限','');
insert into sv_resource_operation (id,resource_type,operation_code,operation_name,note) VALUES ('16','19','2','操作权限','');
insert into sv_resource_operation (id,resource_type,operation_code,operation_name,note) VALUES ('17','20','1','查看权限','');
insert into sv_resource_operation (id,resource_type,operation_code,operation_name,note) VALUES ('18','20','2','操作权限','');
insert into sv_resource_operation (id,resource_type,operation_code,operation_name,note) VALUES ('19','21','1','查看权限','');
insert into sv_resource_operation (id,resource_type,operation_code,operation_name,note) VALUES ('20','21','2','操作权限','');
insert into sv_resource_operation (id,resource_type,operation_code,operation_name,note) VALUES ('21','22','1','查看权限','');
insert into sv_resource_operation (id,resource_type,operation_code,operation_name,note) VALUES ('22','22','2','操作权限','');
insert into sv_resource_operation (id,resource_type,operation_code,operation_name,note) VALUES ('23','23','1','查看权限','');
insert into sv_resource_operation (id,resource_type,operation_code,operation_name,note) VALUES ('24','23','2','操作权限','');
insert into sv_resource_operation (id,resource_type,operation_code,operation_name,note) VALUES ('25','24','1','查看权限','');
insert into sv_resource_operation (id,resource_type,operation_code,operation_name,note) VALUES ('26','24','2','操作权限','');
insert into sv_resource_operation (id,resource_type,operation_code,operation_name,note) VALUES ('27','28','1','查看权限','');
insert into sv_resource_operation (id,resource_type,operation_code,operation_name,note) VALUES ('28','28','2','操作权限','');
insert into sv_resource_operation (id,resource_type,operation_code,operation_name,note) VALUES ('29','29','1','查看权限','');
insert into sv_resource_operation (id,resource_type,operation_code,operation_name,note) VALUES ('30','29','1','操作权限','');
insert into sv_resource_operation (id,resource_type,operation_code,operation_name,note) VALUES ('31','30','1','查看权限','');
insert into sv_resource_operation (id,resource_type,operation_code,operation_name,note) VALUES ('32','30','2','操作权限','');
insert into sv_resource_operation (id,resource_type,operation_code,operation_name,note) VALUES ('33','31','2','查看权限','');
insert into sv_resource_operation (id,resource_type,operation_code,operation_name,note) VALUES ('34','31','2','操作权限','');
insert into sv_resource_operation (id,resource_type,operation_code,operation_name,note) VALUES ('35','32','1','查看权限','');
insert into sv_resource_operation (id,resource_type,operation_code,operation_name,note) VALUES ('36','34','1','控制权限','');
insert into sv_resource_operation (id,resource_type,operation_code,operation_name,note) VALUES ('37','300','1','查看权限','');
insert into sv_resource_operation (id,resource_type,operation_code,operation_name,note) VALUES ('38','300','2','操作权限','');
insert into sv_resource_operation (id,resource_type,operation_code,operation_name,note) VALUES ('39','300','4','设置权限','');

insert into sv_type_definition (id,resource_type,resource_type_name,sub_type,sub_type_name) values ('1','1','DVR',null,null);
insert into sv_type_definition (id,resource_type,resource_type_name,sub_type,sub_type_name) values ('2','2','摄像机',null,null);
insert into sv_type_definition (id,resource_type,resource_type_name,sub_type,sub_type_name) values ('3','2','摄像机','4','枪机');
insert into sv_type_definition (id,resource_type,resource_type_name,sub_type,sub_type_name) values ('4','2','摄像机','5','球机');
insert into sv_type_definition (id,resource_type,resource_type_name,sub_type,sub_type_name) values ('5','2','摄像机','6','云台枪机');
insert into sv_type_definition (id,resource_type,resource_type_name,sub_type,sub_type_name) values ('6','10','车辆检测器',null,null);
insert into sv_type_definition (id,resource_type,resource_type_name,sub_type,sub_type_name) values ('7','10','车辆检测器','1','微波车检器');
insert into sv_type_definition (id,resource_type,resource_type_name,sub_type,sub_type_name) values ('8','10','车辆检测器','2','视频车检器');
insert into sv_type_definition (id,resource_type,resource_type_name,sub_type,sub_type_name) values ('9','10','车辆检测器','3','线圈车检器');
insert into sv_type_definition (id,resource_type,resource_type_name,sub_type,sub_type_name) values ('10','11','风速风向检测器',null,null);
insert into sv_type_definition (id,resource_type,resource_type_name,sub_type,sub_type_name) values ('11','12','气象检测器',null,null);
insert into sv_type_definition (id,resource_type,resource_type_name,sub_type,sub_type_name) values ('12','13','光强检测器',null,null);
insert into sv_type_definition (id,resource_type,resource_type_name,sub_type,sub_type_name) values ('13','14','火灾检测器',null,null);
insert into sv_type_definition (id,resource_type,resource_type_name,sub_type,sub_type_name) values ('14','15','COVI检测器',null,null);
insert into sv_type_definition (id,resource_type,resource_type_name,sub_type,sub_type_name) values ('15','16','氮氧化物检测器',null,null);
insert into sv_type_definition (id,resource_type,resource_type_name,sub_type,sub_type_name) values ('16','17','可变信息标志',null,null);
insert into sv_type_definition (id,resource_type,resource_type_name,sub_type,sub_type_name) values ('17','17','可变信息标志','1','门架式可变信息标志');
insert into sv_type_definition (id,resource_type,resource_type_name,sub_type,sub_type_name) values ('18','17','可变信息标志','2','立柱式可变信息标志');
insert into sv_type_definition (id,resource_type,resource_type_name,sub_type,sub_type_name) values ('19','17','可变信息标志','3','悬臂式可变信息标志');
insert into sv_type_definition (id,resource_type,resource_type_name,sub_type,sub_type_name) values ('20','18','风机',null,null);
insert into sv_type_definition (id,resource_type,resource_type_name,sub_type,sub_type_name) values ('21','19','照明回路',null,null);
insert into sv_type_definition (id,resource_type,resource_type_name,sub_type,sub_type_name) values ('22','20','防火卷帘门',null,null);
insert into sv_type_definition (id,resource_type,resource_type_name,sub_type,sub_type_name) values ('23','21','水泵',null,null);
insert into sv_type_definition (id,resource_type,resource_type_name,sub_type,sub_type_name) values ('24','22','洞口栏杆机',null,null);
insert into sv_type_definition (id,resource_type,resource_type_name,sub_type,sub_type_name) values ('25','23','电光诱导标志',null,null);
insert into sv_type_definition (id,resource_type,resource_type_name,sub_type,sub_type_name) values ('26','24','手动报警按钮',null,null);
insert into sv_type_definition (id,resource_type,resource_type_name,sub_type,sub_type_name) values ('27','25','交通信号灯',null,null);
insert into sv_type_definition (id,resource_type,resource_type_name,sub_type,sub_type_name) values ('28','26','车道指示灯',null,null);
insert into sv_type_definition (id,resource_type,resource_type_name,sub_type,sub_type_name) values ('29','124','物资仓库',null,null);
insert into sv_type_definition (id,resource_type,resource_type_name,sub_type,sub_type_name) values ('30','100','隧道',null,null);
insert into sv_type_definition (id,resource_type,resource_type_name,sub_type,sub_type_name) values ('31','110','桥梁',null,null);
insert into sv_type_definition (id,resource_type,resource_type_name,sub_type,sub_type_name) values ('32','120','路段',null,null);
insert into sv_type_definition (id,resource_type,resource_type_name,sub_type,sub_type_name) values ('33','121','收费站',null,null);
insert into sv_type_definition (id,resource_type,resource_type_name,sub_type,sub_type_name) values ('34','122','匝道',null,null);
insert into sv_type_definition (id,resource_type,resource_type_name,sub_type,sub_type_name) values ('35','123','调度资源',null,null);
insert into sv_type_definition (id,resource_type,resource_type_name,sub_type,sub_type_name) values ('36','125','消防',null,null);
insert into sv_type_definition (id,resource_type,resource_type_name,sub_type,sub_type_name) values ('37','126','医院',null,null);
insert into sv_type_definition (id,resource_type,resource_type_name,sub_type,sub_type_name) values ('38','127','交警',null,null);
insert into sv_type_definition (id,resource_type,resource_type_name,sub_type,sub_type_name) values ('39','128','路政',null,null);
insert into sv_type_definition (id,resource_type,resource_type_name,sub_type,sub_type_name) values ('40','17','可变信息标志','5','隧道内可变信息标志');
insert into sv_type_definition (id,resource_type,resource_type_name,sub_type,sub_type_name) values ('41','17','可变信息标志','4','超大型可变信息标志');
insert into sv_type_definition (id,resource_type,resource_type_name,sub_type,sub_type_name) values ('42','20','防火卷帘门','1','车行横通道');
insert into sv_type_definition (id,resource_type,resource_type_name,sub_type,sub_type_name) values ('43','20','防火卷帘门','2','人行横通道');