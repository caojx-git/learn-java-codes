create table message(
	id number(10)  constraint message_pk_id primary key,
	command varchar2(20) not null,
	description varchar2(20) not null,
	content varchar2(100)
);

alter table message modify content varchar2(200);

insert into message values(0001,'段子','精彩段子','精彩段子内容1');
insert into message values(0002,'段子','精彩段子','精彩段子内容2');
insert into message values(0003,'段子','精彩段子','精彩段子内容3');
insert into message values(0004,'段子','精彩段子','精彩段子内容3');
insert into message values(0005,'段子','精彩段子','精彩段子内容4');
insert into message values(0006,'段子','精彩段子','精彩段子内容5');
insert into message values(0007,'笑话','笑话段子','精彩段子内容1');
insert into message values(0008,'笑话','笑话段子','精彩段子内容2');
insert into message values(0009,'笑话','笑话段子','精彩段子内容3');
insert into message values(0010,'笑话','笑话段子','精彩段子内容4');
insert into message values(0011,'笑话','笑话段子','精彩段子内容5');
INSERT INTO message VALUES (0012, '查看', '精彩内容', '精彩内容');
INSERT INTO message VALUES (0013, '段子', '精彩段子', '如果你的月薪是3000块钱，请记得分成五份，一份用来买书，一份给家人，一份给女朋友买化妆品和衣服，一份请朋友们吃饭，一份作为同事的各种婚丧嫁娶的份子钱。');
INSERT INTO message VALUES (0014, '新闻', '今日头条', '7月17日，马来西亚一架载有298人的777客机在乌克兰靠近俄罗斯边界坠毁。另据国际文传电讯社消息，坠毁机型为一架波音777客机，机载约280名乘客和15个机组人员。');
INSERT INTO message VALUES (0015, '娱乐', '娱乐新闻', '昨日,邓超在微博分享了自己和孙俪的书法。夫妻同样写幸福，但差距很大。邓超自己都忍不住感慨字丑：左边媳妇写的。右边是我写的。看完我再也不幸福了。');
INSERT INTO message VALUES (0018, '电影', '近日上映大片', '《忍者神龟》[2]真人电影由美国派拉蒙影业发行，《洛杉矶之战》导演乔纳森·里贝斯曼执导');
INSERT INTO message VALUES (0017, '彩票', '中奖号码', '查啥呀查,你不会中奖的！');

select * from message where id=0017


--一对多的关系

--一
create table command(
	id number(10)  constraint command_pk_id primary key,
	name varchar2(20) not null,
	description varchar2(20) not null
);

--多
create table command_content(
	id number(10)  constraint command_content_pk_id primary key,
	content varchar2(200) not null,
	command_id number(10) references command(id)
);

insert into command values(001,'段子','精彩段子');
insert into command values(002,'笑话','笑话大王');
insert into command values(003,'体育','体育新闻');

insert into command_content(id,command_id,content) values(001,001,'每个人都希望自己成为重要的人， 但大部分都只能成为让別人重要的人。');
insert into command_content(id,command_id,content) values(002,001,'距离之所以可怕，因为根本不知道对方是把你想念还是把你忘记。');
insert into command_content(id,command_id,content) values(003,001,'20岁的贪玩，造就了30岁的无奈，30岁的无奈，导致了40岁的无为，40岁的无为，奠定了50岁的失败，50岁的失败酿造了一辈子的悲哀');
insert into command_content(id,command_id,content) values(004,002,'后来妹妹真的把灯关了 忽然间一双萤火虫飞了进来 妹妹很紧张的说：姐惨了，蚊子提着灯笼来找我们了');
insert into command_content(id,command_id,content) values(005,002,'滴～老人卡～！全车人冻住，皆而望她。她一脸黑线说：看什么，天山童老，没见过啊？一大爷起身，说：来，大娘，您坐这。');
insert into command_content(id,command_id,content) values(006,002,'今天坐在我旁边，看着我的QQ作思考状，突然跳起大怒道：”你的QQ分组是不是根据胸大小来分的？，给我分在A组里“然后真没然后了');

insert into command_content(id,command_id,content) values(007,003,'国乒入住奥运村:很失望 伊娃恐因禁赛退役');
insert into command_content(id,command_id,content) values(008,003,'热身赛-皇马1-3负巴黎 20时播多特VS曼城');
insert into command_content(id,command_id,content) values(009,003,'新浪前方探营举重队 老干妈成为大明星');
commit;


