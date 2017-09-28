create table customer(
	id number(10) constraint cus_id_pk primary key,
	name varchar2(15) constraint cus_name_nn not null,
	age number(10) constraint cus_age_nn not null
);