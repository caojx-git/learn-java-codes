
--创建用户表
create table user(
	uid int PRIMARY KEY not null auto_increment,
	username varchar(100),
	password varchar(100),
	email varchar(100),
	birthday date,
	sex	char(1),
	state int,
	code varchar(10)
);

--插入用户
insert into user(username,password,email,birthday,sex,state)
values('aaaa','aaa','aaa@store.com',now(),'男',1);

