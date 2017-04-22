DROP  table filemanager_sys_base_type;
DROP TABLE user_info;
/*系统-常量信息定义表*/
create table filemanager_sys_base_type(
  code_type number(8) not null,
  code_id number(8) not null,
  code_name varchar2(64) not null,
  notes varchar2(128),
  ext1 number(8),
  ext2 varchar2(128),
  ext3 varchar2(128),
  constraint filemanager_sys_base_type_pk primary key(code_type, code_id)
);

comment on column filemanager_sys_base_type.code_type is '类型编码';
comment on column filemanager_sys_base_type.code_id is '编号';
comment on column filemanager_sys_base_type.code_name is '名称';
comment on column filemanager_sys_base_type.notes is '注释';
comment on column filemanager_sys_base_type.ext1 is '扩展字段1';
comment on column filemanager_sys_base_type.ext2 is '扩展字段2';
comment on column filemanager_sys_base_type.ext3 is '扩展字段3';


/*实体-用户信息实体类*/
create table user_info(
  user_id number(12) constraint user_info_pk primary key,
  user_name varchar2(32) not null,
  user_password varchar2(32) not null,
  user_gender number(1) check(user_gender in(0,1)),
  user_age number(3) not null,
  college_id number(8),
  manager number(1),
  manager_type number(1),
  user_address varchar2(128),
  user_email varchar2(32) not null,
  user_phone_number number(11),
  create_date date not null,
  notes varchar2(128),
  ext1 number(8),
  ext2 varchar2(32),
  ext3 varchar2(32),
  rec_status number(1) not null
);

comment on column user_info.user_id is '用户编号';
comment on column user_info.user_name is '用户名';
comment on column user_info.user_password is '用户密码';
comment on column user_info.user_gender is '用户性别';
comment on column user_info.user_age is '用户年龄';
comment on column user_info.college_id is '学院';
comment on column user_info.manager is '管理员,0否，1是';
comment on column user_info.manager_type is '管理员类型，1超级管理员，2普通管理员';
comment on column user_info.user_email is '邮箱';
comment on column user_info.user_phone_number is '手机号';
comment on column user_info.create_date is '创建日期';
comment on column user_info.notes is '注释';
comment on column user_info.ext1 is '扩展字段1';
comment on column user_info.ext2 is '扩展字段2';
comment on column user_info.ext3 is '扩展字段3';
comment on column user_info.rec_status is '是否有效，1有效，0无效';



/*用户关系表*/
create table user_relation_info(
  parent_id number(12) not null,
  user_id number(12) not null,
  ext1 number(8),
  ext2 varchar2(32),
  ext3 varchar2(32)
);

comment on column user_relation_info.parent_id is '管理员编号';
comment on column user_relation_info.user_id is '用户编号';
comment on column user_relation_info.ext1 is '扩展字段1';
comment on column user_relation_info.ext2 is '扩展字段2';
comment on column user_relation_info.ext3 is '扩展字段3';


/*文件表*/
create table file_info(
    file_id number(12) constraint file_info_pk primary key,
    user_id number(12),
    file_name varchar2(32) not null,
    path_code number(8) not null,
    file_type number(8),
    create_date date not null,
    notes varchar2(128),
    ext1 number(8),
    ext2 varchar2(32),
    ext3 varchar2(32),
    rec_status number(1)
);

comment on column file_info.file_id is '文件编号';
comment on column file_info.user_id is '上传者编号';
comment on column file_info.file_name is '文件名称';
comment on column file_info.path_code is '文件保存路径编码';
comment on column file_info.file_type is '文件类型';
comment on column file_info.create_date is '文件上传时间';
comment on column file_info.notes is '注释';
comment on column file_info.ext1 is '扩展字段1';
comment on column file_info.ext2 is '扩展字段2';
comment on column file_info.ext3 is '扩展字段3';
comment on column file_info.rec_status is '是否有效，1有效，0无效';

/*用户与文件权限关系表*/
create table authority_relation_info(
  file_id number(12),
  user_id number(12),
  author_code number(1),
  ext1 number(8),
  ext2 varchar2(32),
  ext3 varchar2(32),
  constraint authority_relation_info_pk primary key(file_id,user_id)
);

COMMENT ON COLUMN authority_relation_info.file_id IS '文件编号';
COMMENT ON COLUMN authority_relation_info.user_id IS '用户编号';
COMMENT ON COLUMN authority_relation_info.author_code IS '权限值';
COMMENT ON COLUMN authority_relation_info.ext1 IS '扩展字段1';
COMMENT ON COLUMN authority_relation_info.ext2 IS '扩展字段2';
COMMENT ON COLUMN authority_relation_info.ext3 IS '扩展字段3';

