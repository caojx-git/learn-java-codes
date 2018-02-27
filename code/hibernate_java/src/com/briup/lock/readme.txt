事务以及事务并发所引发的问题

1.脏读  主要针对update操作。 一个事务A读到另一个事务B中修改过但是还没有提交的数据

2.不可重复读  主要针对update操作。 一个事务A在第一次读数据和第二次读数据之间,有另一个事务B把这个数据更改并提交了,
  	所以就出现了事务A里面读一个数据俩次,但是读到的结果是不同的。
  	

3.幻读  主要针对的是insert/delete操作。事务A第一次用where条件筛选出了10条数据,事务A第二次用通样的where条件筛选出的却是11条数据,
	因为事务B在事务A的第一次和第二次查询直接进行了插入操作,并且插入的这个数据满足事务A的where筛选条件.




isolation  事务隔离级别

 read-uncommitted  不提交也能读
 read-committed    提交之后才能读 解决了脏读
 repeatable-read   解决了脏读和不可重复读
 serializable      三个问题都解决了

 级别越高解决的问题越多但是效率越低。
 注意:并不是所有数据库都支持这四种事务隔离级别,比如oracle就只支持第二种和第四种这俩种,比如mysql就四种全支持.
 
 oracle里面默认的事务隔离级别是第二种:read-committed
 
 oralce里面设置事务隔离级别:
 Set Transaction Isolation Level Read Uncommitted
 Set Transaction Isolation Level Read Committed
 Set Transaction Isolation Level Read Repeatable
 Set Transaction Isolation Level Serializable

 hibernate.cfg.xml中也可以设置事务隔离级别:
 <property name="connection.isolation">2</property>

 read-uncommitted    1
 read-committed      2
 repeatable-read     4
 serializable        8
 
 hibernate中就算设置了事务隔离级别也是要看数据库中是否支持的,oracle数据库只支持read-committed和serializable.




hibernate中的锁机制:

如果在hibernate中把事务隔离级别设置为serializable,那么我们就完全没有必要使用hibernate中的锁机制了,但是这样设置后事务的执行效率很低,
	所有我们一般会设置为read-committed(一般默认也是这个),这样既能解决脏读又有一定的效率,只是在这种设置下仍然会有不可重复读的问题(幻读不考虑,很少出现),
	所有我们在hibernate就可以使用锁机制来解决这个问题.



hibernate中锁机制分为乐观锁和悲观锁

悲观锁:hibernate是利用了数据库里面的锁机制来完成的,比如oracle中sql语句里面的for update和for update nowait来实现的。
update teacher set name='lisi' where id=1

select * from teacher where id=1 for update											 ---会等待
select * from teacher where id=1 for update nowait  如果另外一端没有提交事物，然后执行该查询语句---会不等待提示nowait

乐观锁:依靠hibernate中的一些设置和配置来完成的.


//查看数据库中那个session中加了锁
select sess.sid,sess.serial#
from v$locked_object lo, dba_objects ao, v$session sess
where ao.object_id = lo.object_id
and lo.session_id = sess.sid;

//把这个session杀死 强制解锁
alter system kill session 'sid,serial#'; 