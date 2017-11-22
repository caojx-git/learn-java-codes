PLSQL程序设计

一使用PL/SQL打印hello world

注意：如果需要在屏幕中打印信息需要serveroutput 开关打开

set serveroutput on;

declare
	--说明部分
begin
	--程序开始
	dbms_output.put_line('hello world');
end;


dbms_output.put_line是oracle为我们提供的程序包，oracle中提供了很多程序包，可以自己去查相关资料
可以sqlplus中使用：desc dbms_output查看程序包的结构



二、什么是PL/SQL程序

2.1PL/SQL（procedure Language/SQL）

PL/SQL是Oracle对sql语言的过程话扩展
只在sql命令语言中增加 过程化处理（如分支，循环），使用SQL语言具有过程处理能力

PL/SQL通过将SQL语言的数据操纵能力与过程语言的数据处理能力结合起来使得SQL具有面向过程语言的扩张

2.2不同的数据库的SQL扩展
oracle:PL/SQL
DB2:SQL/PL
SQL Server:Transac-SQL（T-SQL）

三、PL/SQL的程序结构

3.1完整的PL/SQL结构块

	declare
		说明部分（变量说明，光标申明，例外说明）

	begin
		语句序列（DML语句）

	exception
		例外处理语句
	end;


	其中：declare 和exception如果没有可以不写，是非必需的但是必需有 begin 和end


3.2说明部分：


3.2.1定义基本变量

类型：char ,varchar2,date,number,boolean,long
举例：
	var1 char(15);
	married boolean :=ture; --声明的同时初始化 PL/SQL中赋值使用 :=
	pasl number(7,2);

案例：
declare
	--定义基本变量类型
	pnumber number(7,2);
	pname varchar2(20);
	pdate date;
begin
	pnumber := 1;
	dbms_output.put_line(pnumber);

	pname := 'tom';
	dbms_output.put_line(pname);

	pdate := sysdate;
	dbms_output.put_line(pdate);
end;

结果：
1
tom
12-10月-16


3.2.2两种特殊变量 引用类型变量和记录类型 变量

引用型变量：
	my_name emp.ename%type
	表示变量 my_name 的类型为表emp中列ename的类型一样
	举例：

	declare
		--定义引用型变量
		pename emp.ename%type;
		psal  emp.sal%type;
	begin
		--得到员工7839的姓名和薪水
		select ename,sal into pename,psal from emp where empno=7839;
		dbms_output.put_line('员工姓名:'||pename);
		dbms_output.put_line('员工薪水:'||psal);
	end;

	注意：1.plsql中赋值有两种方式，第一种为 :=  第二种使用into 关键字注意，into后边的变量
		需要与前边的查询结果顺序一致

	       2.select ename,sal into pename,psal from emp where empno=7839; 后边的赋值不用:= 与变量的赋值有区别




记录型变量：
	emp_rec emp%rowtype
	emp_rec表示记录了表emp一行的类型，可以认为是一个数组记录了一行中的每一列

	举例：

	declare
		--定义记录型变量
		emp_rec emp%rowtype;
	begin
		--得到员工7839的姓名和薪水
		select * into emp_rec from emp where empno=7839;
		dbms_output.put_line('员工姓名:'||emp_rec.ename);
		dbms_output.put_line('员工薪水:'||emp_rec.sal);
	end;


3.3程序中的语句体

	3.3.1if语句

	语法：
	1.
		if 条件 then
		语句1；
		end if;

	2.
		if 条件 then
			语句1；
		else
			语句2；
		end if;
	3.
		if 条件 then
			语句1；
		elsif 条件 then
			语句2；
			...
		elsif
			语句..；
		end if;

	案例：接受一个用户从键盘输入的数字，判断数字

	/*
	判断用户从键盘输入的数字
	1.接受键盘输入
	2.如何使用if语句
	*/

	set serveroutput on;
	--接受一个键盘输入
	--num是一个地址只，在该地址中保存输入的值
	accept num prompt '请输入一个数字'；

	declare
		--定义个变量保存键盘输入的数字
		pnum number := &num;

	begin
		--执行条件判断语句
		if pnum = 0 then
			dbms_output.put_line('你输入的数字是0');
		elsif pnum = 1 then
			dbms_output.put_line('你输入的数字是1');
		else
			dbms_output.put_line('其他数字');

		end if;
	end;



	3.3.2 3中循环语句

		1.while循环

		while total <= 2500 loop

		...
		total :=total +10;
		end loop;

		案例：while打印1。。10

		declare
			--定义循环变量
			pnum number := 1;
		begin
			while pnum <=10 loop
				--执行循环体
				dbms_output.put_line(pnum);
				pnum := pnum +1;
			end loop;
	end;

		注意：pl/sql中不能使用++ += --等运算


		2.loop循环,至少会执行循环体一次

		loop
		exit [when 条件]；
		....
		end loop;

		案例：loop打印1。。10

		declare
			--定义循环变量
			pnum number := 1;
		begin
			loop
				--退出条件
				exit when pnum > 10;
				--没有达到退出条件打印
				dbms_output.put_line(pnum);
				pnum := pnum +1;
			end loop;
		 end;

		3.for循环
		for i in 1..10 loop
		语句序列
		end loop;

		案例：使用for循环打印1。。10

		declare
			--定义循环变量
			pnum number := 1;
		begin
			-- 1..10表示每次取出一个给pnum变量
			for pnum in 1..10 loop
				dbms_output.put_line(pnum);
			end loop;
		 end;


3.4光标的引入

	光标就是一个结果集合Result Set

	语法：CURSOR 光标名称[(参数名 数据类型[参数名 数据类型]...)]

	is select 语句；

	案例：查询并打印员工的薪水

	/*
	1.光标的属性 4个 都是 %开头
	%found-取到记录  %notfound-没有取到记录
	%isopen 判断光标是否打开
	%rowcount 影响行数，比如说我们光标中总共有100条记录，我取走10条记录，那么%rowcount应该是10而不是100

	*/

	declare
		--定义一个光标,可以不带参数
		cursor cemp is select ename,sal from emp;
		--定义变量
		pename emp.ename%type;
		psal emp.sal%type;
	begin
		--打开光标 --关闭使用之前需要打开，使用完了需要关闭
		open cemp;
		--判断光标是否打开
		if cemp%isopen then
			dbms_output.put_line('光标打开了');

			--循环从光标结果集合中取出数据
			loop
				--取一条记录,使用fetch从光标中取出一条记录，之后光标会向后移动
				fetch cemp into pename,psal;
				--循环退出没有取到记录的时候
				exit when cemp%notfound;
				--否则打印记录
				dbms_output.put_line(pename||'薪水是'||psal);
				--打印影响行数
				dbms_output.put_line(cemp%rowcount);
			end loop;

		else
			dbms_output.put_line('光标没有打开');
		  --关闭光标
		  close cemp;

		end if;

	end;




	光标的属性：

	%found光标可以取到数据  %notfound 光标不能取到数据
	%isopen 判断光标是否打开
	%rowcount 影响行数，比如说我们光标中总共有100条记录，我取走10条记录，那么%rowcount应该是10而不是100
	光标的限制 默认情况下 oracle数据库只允许在同一个回话中打开300个光标
	这些信息我们可以在数据库的管理员用户中查询到

	通过管理员用户可以执行如下命令

	show parameter abcd  --语句相当于做了模糊查询

	show parameter cursor --查询光标的相关参数

	cursor_sharing                       string
EXACT
cursor_space_for_time                boolean
FALSE
open_cursors                         integer
300
session_cached_cursors               integer
20


修改默认可以打开的光标数

通过管理员用户修改默认光标的打开数量：
	alter system set open_cursors=4000 scope = both;
	scope的取值：both，memory(表示只更改当前实例，不更改参数文件),spfile(只更改参数文件，不更改当前实例，需要重新启动数据生效)


3.5带参数的光标

	定义的时候
		与不带参数的光标的区别就是定义的时候，可以设定形参和实参
	打开的时候
		带参数的光标打开的时候需要传递实参

declare
	--定义带参数的光标  括号中dno作为形参  = dno 作为实参
	cursor cemp(dno number) is select ename from emp where deptno = dno;

	--定义变量
	pename emp.ename%type;
begin

	--打开光标的时候需要传递一个实参  比如说需要查询10号部门的员工姓名
	open cemp(10)；

		loop
			--取出每个员工的姓名
			fetch cemp into pename;
			--没有取到记录退出循环
			exit when cemp%notfoun;
			--否则打印员工姓名
			dbms_output.put_line(pename);

		end loop;
	--关闭光标
	close cemp;
end;



3.6例外，异常处理

	oracle中有两种例外
		internally defined (系统定义好的例外)
			比如：  No_data_found 没有找到数据
				Too_many_rows （select..into 语句匹配多个行）
				Zero_Divide(被除零)
				Value_error(算术或转换错误)
				Timeout_on_resource(在等待资源时发生超时)
					比如说在分布式数据库中  一个数据库在北京    一个数据库在上海  北京的数据库想访问上海的数据库网络断了，很久都没有等到上海的数据
					给我返回结果。就会发生这种情况
				....
		user defined（自定义例外）


	3.6.1系统例外案例：

	--系统例外 没有返现数据 no_data_found
	declare
		pname emp.ename%type;
	begin
		--查询员工工号是1234的员工的姓名(假如不存在该员工就会找不到数据)
		select ename into pename from emp where empno =1234;

	exception
		when no_data_found then
		dbms_output.put_line('没有找到该员工');
		when others then --除了上边这个数据库中情况，都由这里捕获，避免将例外抛给数据库
		dbms_output.put_line('其他例外');
	end;


	结果：没有找到该员工



	--系统例外 返回多个结果 Too_many_rows
	declare
		pname emp.ename%type;
	begin
		--查询所有10号部分的员工的姓名
		select ename into pename from emp where deptno =10;

	exception
		when too_many_rows then
		dbms_output.put_line('匹配了多个行');
		when others then --除了上边这个数据库中情况，都由这里捕获，避免将例外抛给数据库
		dbms_output.put_line('其他例外');
	end

	结果：匹配了多个行

	--系统例外 被零除 Zero_Divide
	declare
		--定义一个基本变量
		pnum number;
	begin
		pnum :=1/0;

	exception
		when zero_divide then
		dbms_output.put_line('0不能做除数');
		when others then --除了上边这个数据库中情况，都由这里捕获，避免将例外抛给数据库
		dbms_output.put_line('其他例外');
	end;

	结果：0不能做除数


	--系统例外 算术或转换错误 value_error
	declare
		--定义一个基本变量
		pnum number;
	begin
		pnum :='abc';

	exception
		when value_error then
		dbms_output.put_line('算术或转换错误');
		when others then --除了上边这个数据库中情况，都由这里捕获，避免将例外抛给数据库
		dbms_output.put_line('其他例外');
	end;

	结果：算术或转换错误



3.6.2自定义例外

	定义变量，类型是exception
	使用raise抛出自定义例外

	案例：

	declare
		my_job char(20);
		v_sal emp.sal%type;
		--自定义异常
		no_data exception;

		cursor c1 is select distinct job from emp order by job;

	begin
		open c1;
		fetch c1 into v_job;

		if c1%notfound then
			--抛出自定义异常
			raise no_data;
		end if;

		close c1;

	exception

		when no_data then  --捕获自定义异常
		dbms_output.put_line('没有发现数据');
		when others then --除了上边这个数据库中情况，都由这里捕获，避免将例外抛给数据库
		dbms_output.put_line('其他例外');
	end;


本节总结： 两个比较复杂的PL/SQL块

1.说明
	1.下边的for emm in (select * from newqdgl.agent_point_not_user_sql) loop .. end loop; 中for循环将查询结果
		集合保存到emm临时变量中，这很像光标cursor的功能

	2.oracle的循环语句在较低的版本中有break表示结束整个循环，不过没有continue跳过本次循环的功能，我们可以通过
		goto语句间接的实现continue功能 如下定义命名块<<endlabel>> ，使用goto endlabel;就可以跳转到有名块

	3.有名块和匿名块
		前边我们用的都是匿名块，定义有名块语法 <<名字>>


declare
  v_OpId         number(12);
  v_Org_Id       number(12);
  v_llCountMonth number(12); --表示本月需要积分出账的月份
  v_iChangePoint number(12); --表示转出积分
  v_pointsSysId  number(12); --积分编号
  v_lPointsValue number(12); --转出积分临时变量
  v_sql          varchar2(1024);

  v_thisyearValuedPoints number(12); --积分子表年度积分
  v_CurrPoints           number(12); --积分子表当前积分
  v_docode               number(12); --获取业务记录
  v_doneCode            number(12);
  v_subdtail_CurrPoints number(12); --积分明细当前积分
  v_POINTS_INFO_count   number(2);

begin
  v_OpId                := 999;
  v_Org_Id              := 999;
  v_llCountMonth        := 201608;
  v_iChangePoint        := 0;
  v_pointsSysId         := 0;
  v_lPointsValue        := 0;
  v_subdtail_CurrPoints := 0;
  v_POINTS_INFO_count   :=0;

  -- 通过下边这种for循环的方式，可以将查询结果集合保存到一个临时集合变量中，可以达到类似于光标cursor的效果
  for emm in (select * from newqdgl.agent_point_not_user_sql) loop

    dbms_output.put_line('1---' || emm.agent_id || '--' ||emm.curr_point_not_exc);

    -- 3.积分变更
    --根据代理商编号查询对应积分分表编号

    select count(1) into v_POINTS_INFO_count from
    newqdgl.POINTS_INFO
     WHERE 1 = 1
       AND POINTS_ID = emm.agent_id;
     if v_POINTS_INFO_count =0 then
       goto endlabel;
      end if;

    SELECT POINTS_SYS_ID
      into v_pointsSysId
      FROM newqdgl.POINTS_INFO
     WHERE 1 = 1
       AND POINTS_ID = emm.agent_id;

    dbms_output.put_line('2---'||v_pointsSysId);

    -- 3.1 计算  不可兑换转出积分  == 可兑换新增积分
    if mod(v_llCountMonth, 100) = 1 then
      v_iChangePoint := emm.curr_point_not_exc;
    else
      v_iChangePoint := emm.curr_point_not_exc /
                        (14 - mod(v_llCountMonth, 100));
    end if;
    dbms_output.put_line('3----'||v_pointsSysId||'---'||v_iChangePoint);

    --3.2更新AGENT_POINT_NOT_USER 表中的不可兑换积分
    UPDATE newqdgl.AGENT_POINT_NOT_USER
       set CURR_POINT_NOT_EXC =
           (emm.curr_point_not_exc - v_iChangePoint),
           REC_STATUS         = 1
     WHERE AGENT_ID = emm.agent_id;

    --3.3 不考虑赤字积分

    v_lPointsValue := v_iChangePoint;

    --3.4新增积分addAgentCurrPoint(pointsSysId, agentId, lPointsValue, llCountMonth);

    dbms_output.put_line('积分新增开始' || v_pointsSysId || '---' ||emm.agent_id);
    --3.4.1查询积分子表中的信息
    SELECT CURR_POINTS, THISYEAR_VALUED_POINTS
      into v_CurrPoints, v_thisyearValuedPoints
      FROM newqdgl.ods_points_subinfo
     WHERE 1 = 1
       AND POINTS_SYS_ID = v_pointsSysId
       AND POINTS_ID = emm.agent_id;

    dbms_output.put_line('4----');

    -- 2.4.2插入积分子表记录到临时表
    insert into ods_points_subinfo_temp
      (POINTS_SYS_ID,
       POINTS_ID_TYPE,
       POINTS_ID,
       CURR_POINTS,
       THISYEAR_VALUED_POINTS)
    values
      (v_pointsSysId,
       4,
       emm.agent_id,
       (v_CurrPoints + v_lPointsValue),
       (v_thisyearValuedPoints + v_lPointsValue));

    dbms_output.put_line('5----');

    --3.4.3查询积分明细
    SELECT POINTS_SYS_ID SUB_CURR_POINTS
      into v_subdtail_CurrPoints
      FROM points_info_detail_info
     WHERE 1 = 1
       AND POINTS_ID = emm.agent_id
       AND POINTS_ID_TYPE = 4
       AND POINTS_SYS_ID = v_pointsSysId
       AND POINTS_SUB_TYPE = 1;

    dbms_output.put_line('6----');

    --3.4.4插入积分明细到临时表
    insert into points_info_detail_info_temp
      (POINTS_SYS_ID,
       POINTS_ID_TYPE,
       POINTS_ID,
       POINTS_SUB_TYPE,
       SUB_CURR_POINTS)
    values
      (v_pointsSysId,
       4,
       emm.agent_id,
       1,
       (v_subdtail_CurrPoints + v_lPointsValue));

    dbms_output.put_line('7----');

    --3.4.5 获取业务记录序列
    SELECT newqdgl.SEQ_DONE_NEW_CODE.NEXTVAL into v_docode FROM DUAL;

    INSERT INTO points_subcount_info_temp
      (points_sys_id,
       points_id_type,
       points_id,
       get_sub_points,
       points_sub_type,
       done_code)
    VALUES
      (v_pointsSysId, 4, emm.agent_id, v_lPointsValue, 1, v_docode);


    dbms_output.put_line('8----' || v_CurrPoints || '--' ||v_thisyearValuedPoints || '--' || v_lPointsValue || '--' ||v_llCountMonth);

    insert into ods_points_count_temp
      (points_sys_id,
       points_id_type,
       points_id,
       get_points,
       points_busi_code,
       done_date,
       done_code,
       bill_month,
       op_id,
       curr_points,
       thisyear_valued_points,
       ext4)
    values
      (v_pointsSysId,
       4,
       emm.agent_id,
       v_lPointsValue,
       19,
       to_date('20160805', 'yyyymmdd'),
       v_docode,
       v_llCountMonth,
       9,
       (v_CurrPoints + v_lPointsValue),
       (v_thisyearValuedPoints + v_lPointsValue),
       to_char(v_CurrPoints + v_lPointsValue));

    dbms_output.put_line('积分新增结束' || v_pointsSysId || '---' ||emm.agent_id);

    select newqdgl.SEQ_DONE_CODE.nextval into v_doneCode from dual;
    --插入积分记录
    insert into newqdgl.Channel_Point_Record_Ext
      (Chanenel_Entity_Id,
       Operate_Code,
       Oper_Value,
       Done_Code,
       Done_Date,
       Org_Id,
       Op_Id)
    values
      (emm.agent_id,
       8020311,
       0 - v_iChangePoint,
       v_doneCode,
       to_date('20160805', 'YYYY/MM/DD'), --可以写成具体的出账日期
       v_Org_Id,
       v_OpId);

    dbms_output.put_line('9----');

    insert into newqdgl.Channel_Point_Record_Ext
      (Chanenel_Entity_Id,
       Operate_Code,
       Oper_Value,
       Done_Code,
       Done_Date,
       Org_Id,
       Op_Id)
    values
      (emm.agent_id,
       8020310,
       v_iChangePoint,
       v_doneCode,
       to_date('20160805', 'YYYY/MM/DD'), ----可以写成具体的出账日期
       v_Org_Id,
       v_OpId);


    dbms_output.put_line('10----');

    <<endlabel>> --这里相当于定义了一个有名的的块，endlabel ，使用goto endlabel 可以跳过goto endlabel 到endlabel之间的代码
		--即跳到for循环的最后，便相当于实现了continue,跳到这里后，不干什么需要些null;
    null;
  end loop;
  --commit;
EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    dbms_output.put_line('error');
    RETURN;
end;



2.下边的方式动态的拼接出sql

	使用 execute immediate v_sql; 可以立即执行拼接后的sql,同时我们也可以使用输出语句将生成后的sql打印出来。


//------------------3.切换用户到"aicbs"  将临时表中的数据插入到各个分表--------------------------
-- 1. ods_points_count_temp
declare
       v_sql varchar2(1024);
begin
  for i in 0 .. 9 loop
     v_sql:='insert into aicbs.points_count0'||i||'_2016  select * from newqdgl.ods_points_count_temp where mod(points_sys_id,10) = '||i;
       execute immediate v_sql;
  end loop;
  --commit;
  EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    dbms_output.put_line('error');
    RETURN;
end;





4.存储过程和存储函数

	存储过程和存储函数跟我们知道的表、视图、索引、序列、同义词等一样是我们数据中的对象。

4.1什么是存储过程和存储函数：
	指存储在数据库中供所有的用户程序调用的 子程序叫存储过程、存储函数。

	存储过程和存储函数的相同点：完成特定功能的程序
	存储过程和存储函数的区别：是否用return 语句返回值，存储过程不能使用return 返回一个函数的值，存储函数可以，对于其他的
	可以认为他们是相同的。

4.2创建和使用存储过程

	使用create procedure命令建立存储过程和存储函数
	语法：
	--创建或替换一个存储过程              参数列表需要指明输入或者输出参数
	create [or replace] procedure 过程名（Name in | out type, Name in | out type, ...）
	as | is --相当于PL/SQL块的declare，这里不可省略
	PLSQL子程序体；


	4.2.1不带参数的存储过程

	--第一个存储过程打印helloworld --注意不带参数的存储过程过程名不能有()
	create or replace procedure sayhelloWorld
	as
	--相当于PL/SQL中declare说明部分，不过这里即使没有说明部分也需要写
	begin
		dbms_output.put_line('helloWorld');
	end sayhelloWorld;

	如果使用PL/SQL Developer 工具，可以在左边的procedure区域看到我们执行后编译后的存储过程

	4.2.2调用存储过程


		方式一：在 Command Windows（命令窗口中执行） exec sayhelloWorld();

		SQL> set serveroutput on;
		SQL> exec sayhelloworld();
		结果：
			helloWorld
		方式二：可以在SQL Windows或 Command Windowsz中执行PL/SQL调用
			begin
				sayhelloWorld();
			end;
		结果：
			helloWorld

	4.2.2带参数的存储过程
		--创建一个带参数的存储过程，给指定的员工涨100员工资，并打印涨前和涨后的薪水
		--in表示是一个输入参数，如果带参数，需要指明是输入参数还是输出参数
		create or repalce procedure raisesalary(eno in number)
		as
			--定义一个变量保存涨前的薪水
			psal emp.sal%type;
		begin
			--得到员工的涨前的薪水
			select sal into psal emp where empno=eno;
			--给员工涨100
			update emp set sal = sal+100 where empno = eno;
			--这里进行了update,不过我们一般不在存储过程和存储函数中进行提交事务，一般由调用者进行提交

			--打印涨前和涨后的薪水
			dbms_output.put_line('涨前:'||psal||'涨后'||(psal+100));
		end;


		调用
			begin
				raisesalary(7839); --给员工号为7839涨工资
				raisesalary(7566); --给员工号为7566涨工资
			end;


		存储过程中Debug调试
			见图



4.3存储函数

	函数（Function） 为一命名的存储程序，可带参数，并返回一计算值
	函数和过程结构类似，但必需要有一个return子句，用于返回函数数值。


	创建存储函数的语法：

	--带参数的存储函数必需指明参数列表是输入参数还是输出参数
	--假如不带参数，不能带（）
	create [or replace] function 函数名（Name in | out type, Name in | out type, ...）
	return 函数值类型
	as | is
	PL/SQL子程序体；

	案例：
		--存储函数：查询某个员工的年收入
		create or replace function queryempincomme(eno in number)
		return number
		as
			--定义变量保存员工的薪水和奖金
			psal emp.sal%type;
			pcomm emp.comm%type;

		begin
			--得到员工的月薪和奖金
			select sal,comm into psal,pcomm from emp where empno=eno;
			--直接返回年收入
			return psal*12+nvl(pcomm,0);
		end;

		调用

		declare
			ypsal number;
		begin
			--得到员工7891的年收入
			ypsal:=queryempincomme(7891);
			dbms_output.put_line(ypsal);

		end;

4.4in和out 参数

	前边我们介绍了存储过程和存储函数
	a.一般来讲，存储过程和存储函数的区别在于存储函数可以有一个返回值；而存储过程没有返回值
	b.如果存储过程或存储函数带参数的话我们需要指明是输入参数还是输出参数
	c.存储过程和存储函数都可以通过out参数指定一个或多个输出参数，我们可以利用out参数，在过程或函数中实现返回一个或多个值
	  （即存储过程本来不能有返回值，但利用out参数，我们就可以实现存储过程返回值）
	d.一般如果需要返回多个值，我们优先使用存储过程，如果只要返回一个值我们优先使用存储函数


	案例：
	--利用out参数查询员工的姓名，月薪和职位
	create or replace procedure queryempinfo(eno in number,pename out varchar2,psal out nubmer,pjob out varchar2)

	as
	begin
		--得到员工的姓名，月薪，职位
		select ename,sal,empjob into pename,psal,pjob from emp where empno=eno;

	end;

	调用：

	declare

		eno number;
		pename varchar2(30);
		psal number;
		pjob varchar2(200);

	begin
		eno := 7839;
		--调用存储过程，我们可以得到out参数的返回值
		queryempinfo(eno,pename,psal,pjob);

		dbms_output.put_line(pename);
		dbms_output.put_line(psal);
		dbms_output.put_line(pjob);
	end;


	问题：上边的案例只利用out参数返回了员工的部分信息
		1.假如需要查询员工的所有信息，out参数有很多，难道要写很多个out参数？
		2.查询某个部门中所有员工的所有信息---》out中返回一个集合





5.程序包

	在out参数中使用光标我们需要使用到包，（包也是oracle中的数据库对象）
	oracle中的包分为包头和包体，包头负责声明，包体负责实现（者很像java中的接口与实现类的关系）

	包头语法：
	create [or replace] package package_name
	is | as
	--定义公用常量、变量、游标、类型
	--定义公用的过程和函数
	end package_name;

	包体语法

	create [or replace] package body package_name
	is | as
	--定义私有常量、变量、类型、游标、过程和函数
	--实现公用的过程和函数
	end package_name


	案例：查询某个部门中所有员工的所有信息,这里使用如下方案，实现第4条留下的几个问题out参数很多显然不可取，我们是使用cursor光标实现

	创建包头
		create or replace package mypackage as
		--定义公用的类型 自定义类型empcursor 为 cursor类型
		type empcursor is ref cursor;
		--定义公用的过程和函数 --之后需要在包体中实现
		procedure queryEmpList(dno in number,empList out empcursor);

		end mypackage;
	创建包体
		create or replace package body mypackage
		as

			procedure queryEmpList(dno in number,empList out empcursor)
			as
			begin
				--打开光标
				open empcursor from select * from emp where deptno=dno;
			end queryEmpList;
		end mypackage;




	调用程序包：
		调用公用变量
		exec 程序包名.公用变量名 := 赋值；
		调用公共过程
		exec 程序包名.公用过程名(参数)；





