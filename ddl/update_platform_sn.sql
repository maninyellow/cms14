update sv_device set standard_number = concat('26301',substring(standard_number,6));
update sv_organ set standard_number = concat('26301',substring(standard_number,6));
update sv_user set standard_number = concat('26301',substring(standard_number,6));
update sv_platform_server set standard_number = concat('26301',substring(standard_number,6));
update sv_standard_number set standard_number = concat('26301',substring(standard_number,6));