--删除表语句
drop table memberspace;
drop table memberinfo;
drop table graderecord;
drop table pointrecord;
drop table pointaction;
drop table messagerecord;
drop table memberinfo;


--创建等级表
create table graderecord(
	id number primary key,
	minpoint number not null,
	maxpoint number not null,
	gradename varchar2(20) not null,
	iconpath varchar2(50) not null
);

--创建会员表
create  table memberinfo (
	id number primary key,
	nickname varchar2(20) not null,
	password varchar2(50) not null,
	gender varchar2(1) not null,
	age number not null,
	email varchar2(100) not null,
	provincecity varchar2(10),
	address varchar2(200),
	phone varchar2(50),
	passwordquestion varchar2(200),
	passwordanswer varchar2(200),
	recommender varchar2(20),
	point number default 0,
	registerdate date,
	latestdate date,
	status number default 0,
	isonline number default 0,
	gradeid number,
	constraint info_grade foreign key(gradeid) references graderecord(id)
);

--创建好友表
create table friendrecord(
	id number primary key,
	selfname varchar2(20) not null,
	friendname varchar2(20) not null
	);

--创建黑名单表
create table blackrecord(
	id number primary key,
	selfname varchar2(20) not null,
	blackname varchar2(20) not null
);
	
--创建个人空间表
create table memberspace(
	id number primary key,
	opinion varchar2(200),
	runtime varchar2(20),
	runplace varchar2(20),
	runstar varchar2(50),
	runhabit varchar2(50),
	cellphone varchar2(50),
	icon varchar2(200),
	memberid number,
	constraint space_info foreign key(memberid) references memberinfo(id)
	);
	
--创建积分动作表
create table pointaction(
	id number primary key,
	actionname varchar2(20),
	point number not null,
	description varchar2(200)
	);
	
--创建积分记录表
create table pointrecord(
	id number primary key,
	nickname varchar2(20) not null,
	receivedate date not null,
	pointactionid number ,
	constraint record_action foreign key(pointactionid)  references pointaction(id)
	);
	
--创建短消息列表
create table messagerecord(
	id number primary key,
	sender varchar2(20) not null,
	receiver varchar2(20) not null,
	senddate date  not null,
	title varchar2(100) not null,
	content varchar2(300) not null,
	senderstatus number default 0,
	receiverstatus number default 0,
	status number default 0
	);


--建造序列
drop sequence SEQ_COMMON;
create sequence SEQ_COMMON 
increment by 1 
start with 1;


--插入积分动作数据
INSERT INTO PointAction
(id,ActionName,Point,Description) Values(1,'REGISTER',50,N'注册会员');
INSERT INTO PointAction
(id,ActionName,Point,Description) Values(2,'RECOMMEND',20,N'推荐会员');
INSERT INTO PointAction
(id,ActionName,Point,Description) Values(3,'LOGIN',3,N'登录');
INSERT INTO PointAction
(id,ActionName,Point,Description) Values(4,'LOGINDESKHELPER',3,N'登录桌面助手');
INSERT INTO PointAction
(id,ActionName,Point,Description) Values(5,'CREATEPERSONALSPACE',25,N'创建个人空间');
INSERT INTO PointAction
(id,ActionName,Point,Description) Values(6,'SENDSTICK',25,N'发帖');
INSERT INTO PointAction
(id,ActionName,Point,Description) Values(7,'REPLYSTICK',25,N'回帖');
INSERT INTO PointAction
(id,ActionName,Point,Description) Values(8,'GOODSTICK',30,N'精华贴');
INSERT INTO PointAction
(id,ActionName,Point,Description) Values(9,'SUPERGOODSTICK',60,N'超级精华贴');
INSERT INTO PointAction
(id,ActionName,Point,Description) Values(10,'BBSMANAGER',200,N'成为版主');
INSERT INTO PointAction
(id,ActionName,Point,Description) Values(11,'REPLYSTICK',5,N'每10个回复帖子楼主+5');
INSERT INTO PointAction
(id,ActionName,Point,Description) Values(12,'EDM',10,N'成为版主');
INSERT INTO PointAction
(id,ActionName,Point,Description) Values(13,'JOINRUNNING',500,N'参加都市跑活动');
INSERT INTO PointAction
(id,ActionName,Point,Description) Values(14,'WINRUNNING1',5000,N'都市跑活动得名次');
INSERT INTO PointAction
(id,ActionName,Point,Description) Values(15,'WINRUNNING2',4000,N'都市跑活动得名次');
commit;

--插入等级记录数据
INSERT INTO GradeRecord VALUES(1,0,300,'业余爱好者','/images/face1.gif');
INSERT INTO GradeRecord VALUES(2,300,700,'跑步小将','/images/face2.gif');
INSERT INTO GradeRecord VALUES(3,700,1200,'跑步健将','/images/face3.gif');
INSERT INTO GradeRecord VALUES(4,1200,1800,'专业运动员','/images/face4.gif');
INSERT INTO GradeRecord VALUES(5,1800,2500,'顶级运动员','/images/face5.gif');
commit;

select * from memberinfo;
delete * from memberinfo;
select * from memberspace;

查找积分等级前n名的用户
select *
from memberinfo
where rownum <=10
order by point asc

