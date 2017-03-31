Oracle触发器

1.什么是触发器
	数据库触发器是一个与表相关联的，储存的PL/SQL程序。
	每当一个特定的操作语句（insert,update,delete）在指定的表上发出时，oracle自动
	执行触发中定义的语句序列。--注意没有select


	1.如何创建触发器
	案例：每当插入新员工后自动打印“成功插入新员工”（触发器单词：trigger）


	create or replace trigger saynewemp
	after insert --after指定在插入之后触发触发器，before指定在插入之前触发触发器
	on emp 
	declare
	begin
		dbms_output.put_line('成功插入新员工');
	end;

	结果：
	SQL> insert into emp(empno,ename,sal,deptno) values(1001,'Tom',3000,10);
	成功插入新员工
	已创建1行
	
	

2.触发器的应用场景
	1.复杂的安全性检查
	2.数据确认
	3.实现数据审计功能
	4.完成数据的备份和同步

3.触发器的语法
	create [or replace] trigger 触发器名
	{before | after} --表明是触发器是在操作前还是操作后生效
	{delete|insert|update[of列名]}--触发器支持的操作，update可以指定某列执行操作时触发
	on 表名 --为某个表名创建的触发器
	[for each row[when(条件)]] --有这条语句相当于行级触发器，默认为语句级触发器
	PLSQL块

4.触发器的类型
	语句级触发器：针对的是表
		在指定的操作语句操作之前后之后执行一次（只执行一次，比如说），不管这条语句影响了多少行。
	行级触发器：针对的是行
		触发语句作用的每一条记录都被触发，在行级触发器中使用:old和:new伪记录变量，识别只得状态
		

	例如：假如我定义了一个语句级触发器，插入10号部门的员工（有3条记录）到表emp10中，触发器只会触发一次
	insert into emp10 select * from emp where deptno=10;
	     假如我定义了一个行级触发器，插入10号部门的员工（有3条记录）到表emp10中，触发器只会触发三次
		insert into emp10 select * from emp where deptno=10;



		

5.案例
	
	--1.复杂的安全性检查
	--禁止在非工作期间插入新员工
	/*
		1.周末：to_char(sysdate,'day') in ('星期六','星期日')
		2.上班前，下班后：to_number(to_char(sysdate,'hh24')) not betwwn 9 and 18
	*/

	create or replace trigger securityemp
	before insert --在插入前进行检查
	on emp
	begin
		if to_char(sysdate,'day') in ('星期六','星期日') or 
		to_number(to_char(sysdate,'hh24')) not betwwn 9 and 18 then

			--禁止插入员工,抛出异常，使用如下方式
			raise_application_error(-20001,'禁止在非工作时间插入新员工');
			--不能直接使用raise抛出异常，应该使用上边这种方式，第一个参数的取值 -20000 ~ -29999
			--第二个参数：触发器抛出异常后提示的内容
		end if;

	end;

	如果在非工作时间执行insert语句就会触发，触发器，禁止在非工作时间插入新员工
	
	--2.数据确认

	/*
	触发器二：参数的确认
	涨后的薪水不能低于涨前的薪水

	1. :old 和 :new 代表同一条记录
	2. :old表示操作该行之前的记录
	   :new表示操作该行之后的记录
	*/

	create or replace trigger checksalary
	before update
	on emp
	for each row --行级触发器
	begin
		--if 涨后的薪水 < 涨前的薪水
		if :new.sal < :old.sal then
			raise_application_error(-20002,'涨后的薪水不能少于涨前的薪水，涨前的薪水'||:new.sal||'涨后的薪水'||:old.sal);
		end if;
	end;
	
	update emp set sal=sal-1 where empno=7389; --这里会触发触发器

	3.实现数据审计功能


	/*
	触发器应用场景三.实现数据审计功能====》基于值得审计
	给员工涨工资，当涨后的工作大于6000的时候，审计该员工的信息
	*/

	--创建表保存审计信息
	create table audit_info(
		infomation varchar2(2000);
	);

	create or replace trigger do_audit_emp_salary
	after update
	on emp
	for each row
	begin
		--当员工的工资大于6000插入审计信息
		if :new.sal>6000 then
			insert into audit_info values(:new.empno||' '||:new.ename||' '||:new.sal);
		end if;
	end;
	
	给每个员工涨工资2000,如果存在员工工资大于6000就会插入审计表
	update emp set sal = sal+2000;

	4.完成数据的备份和同步

	/*
	利用触发器实现数据的同步和备份----》同步备份
	假如A表为主表，B表为备份表，如果A表中的数据发生变化，则同步到B表
	当员工涨完工资后，自动备份新的工资到备份表中
	*/
	create or replace trigger sync_salary
	after update
	on emp
	for each row
	begin
		--当主表更新后，自动进行备份
		update emp_back set sal = :new.sal where empno= :new.empno;
	end;

	update emp set sal=sal+10 where empno=7839;

	oracle中还有个利用快照备份，是异步的。 而利用触发器，是同步的。


	快照备份数据
	http://nakupanda.iteye.com/blog/1028819