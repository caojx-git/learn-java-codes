#资源库流程规则表
select * from ACT_RE_DEPLOYMENT; 	    #部署信息表
select * from ACT_RE_MODEL;  		      #流程设计模型部署表
select * from ACT_RE_PROCDEF;  		    #流程定义数据表

#运行时数据库表
select * from ACT_RU_EXECUTION;		    #运行时流程执行对象表
select * from ACT_RU_IDENTITYLINK;		#运行时流程人员表，主要存储任务节点与参与者的相关信息
select * from ACT_RU_TASK;			      #运行时任务节点表
select * from ACT_RU_VARIABLE;		    #运行时流程变量数据表

#历史数据库表
select * from ACT_HI_ACTINST; 		    #历史活动节点表
select * from ACT_HI_ATTACHMENT;		  #历史附件表
select * from ACT_HI_COMMENT;		      #历史意见表
select * from ACT_HI_IDENTITYLINK;		#历史流程人员表
select * from ACT_HI_DETAIL;			    #历史详情表，提供历史变量的查询
select * from ACT_HI_PROCINST;		    #历史流程实例表
select * from ACT_HI_TASKINST;		    #历史任务表
select * from ACT_HI_VARINST;			    #历史变量表

#组织机构表
select * from ACT_ID_GROUP;		        #用户组信息表
select * from ACT_ID_INFO;			      #用户扩展信息表
select * from ACT_ID_MEMBERSHIP;	    #用户与用户组对应信息表
select * from ACT_ID_USER;			      #用户信息表
#这四张表很常见，基本的组织机构管理，关于用户认证方面建议还是自己开发一套，组件自带的功能太简单，使用中有很多需求难以满足

#通用数据表
select * from ACT_GE_BYTEARRAY;		    #资源文件表，二进制数据表
select * from ACT_GE_PROPERTY;			  #主键生成策略表，属性数据表存储整个流程引擎级别的数据,初始化表结构时，会默认插入三条记录，
