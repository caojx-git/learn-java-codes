--创建数据库
create database db_shiro charset utf8;

--创建表，用来保存用户，注意表明必须为users，必须有如下字段
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--插入用户
insert  into `users`(`id`,`userName`,`password`) values (1,'java1234','123456');
