--创建数据库
create database db_shiro charset utf8;
--角色表
CREATE TABLE `t_role` (
  `id` int(11)  PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `roleName` varchar(20) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

insert  into `t_role`(`id`,`roleName`) values (1,'admin');
insert  into `t_role`(`id`,`roleName`) values (2,'teacher');
--用户表
CREATE TABLE `t_user` (
  `id` int(11)  PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `userName` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `roleId` int(11) DEFAULT NULL,
   foreign key(roleId) references t_role(id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

insert  into `t_user`(`id`,`userName`,`password`,`roleId`) values (1,'java1234','123456',1);
insert  into `t_user`(`id`,`userName`,`password`,`roleId`) values (2,'jack','123456',2);
insert  into `t_user`(`id`,`userName`,`password`) values (3,'marray','123456');
insert  into `t_user`(`id`,`userName`,`password`) values (4,'json','123456');
--权限表
CREATE TABLE `t_permission` (
  `id` int(11)  PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `permissionName` varchar(50) DEFAULT NULL,
  `roleId` int(11) DEFAULT NULL,
   foreign key(roleId) references t_role(id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

insert  into `t_permission`(`id`,`permissionName`,`roleId`) values (1,'user:*',1);
insert  into `t_permission`(`id`,`permissionName`,`roleId`) values (2,'teacher:*',2);


