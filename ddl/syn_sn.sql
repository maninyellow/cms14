delete from sv_standard_number;

insert into sv_standard_number (standard_number, class_type) select standard_number, 'Dvr' from sv_device where type = '1';
insert into sv_standard_number (standard_number, class_type) select standard_number, 'Camera' from sv_device where type = '2';

insert into sv_standard_number (standard_number, class_type) select standard_number, 'Ccs' from sv_platform_server where type = '3';
insert into sv_standard_number (standard_number, class_type) select standard_number, 'Crs' from sv_platform_server where type = '4';
insert into sv_standard_number (standard_number, class_type) select standard_number, 'Mss' from sv_platform_server where type = '5';
insert into sv_standard_number (standard_number, class_type) select standard_number, 'Dws' from sv_platform_server where type = '7';
insert into sv_standard_number (standard_number, class_type) select standard_number, 'Das' from sv_platform_server where type = '8';
insert into sv_standard_number (standard_number, class_type) select standard_number, 'Ens' from sv_platform_server where type = '9';

insert into sv_standard_number (standard_number, class_type) select standard_number, 'Monitor' from sv_monitor;

insert into sv_standard_number (id, standard_number, class_type) select standard_number, standard_number, 'User' from sv_user;

insert into sv_standard_number (standard_number, class_type) select standard_number, 'Organ' from sv_organ;

insert into sv_standard_number (standard_number, class_type) select standard_number, 'VehicleDetector' from tm_vehicle_detector;

insert into sv_standard_number (standard_number, class_type) select standard_number, 'WeatherStat' from tm_weather_stat;

insert into sv_standard_number (standard_number, class_type) select standard_number, 'WindSpeed' from tm_wind_speed;

insert into sv_standard_number (standard_number, class_type) select standard_number, 'LoLi' from tm_loli;

insert into sv_standard_number (standard_number, class_type) select standard_number, 'NoDetector' from tm_no_detector;

insert into sv_standard_number (standard_number, class_type) select standard_number, 'Covi' from tm_covi;

insert into sv_standard_number (standard_number, class_type) select standard_number, 'FireDetector' from tm_fire_detector;

insert into sv_standard_number (standard_number, class_type) select standard_number, 'PushButton' from tm_push_button;

insert into sv_standard_number (standard_number, class_type) select standard_number, 'ControlDevice' from tm_control_device;