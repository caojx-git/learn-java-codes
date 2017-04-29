/*系统-常量信息定义表*/
CREATE TABLE filemanager_sys_base_type (
  code_type NUMBER(8)     NOT NULL,
  code_id   NUMBER(8)     NOT NULL,
  code_name VARCHAR2(128) NOT NULL,
  notes     VARCHAR2(128),
  ext1      NUMBER(8),
  ext2      VARCHAR2(128),
  ext3      VARCHAR2(128),
  CONSTRAINT FILEMANAGER_SYS_BASE_TYPE_PK PRIMARY KEY (code_type, code_id)
);

COMMENT ON COLUMN filemanager_sys_base_type.code_type IS '类型编码';
COMMENT ON COLUMN filemanager_sys_base_type.code_id IS '编号';
COMMENT ON COLUMN filemanager_sys_base_type.code_name IS '名称';
COMMENT ON COLUMN filemanager_sys_base_type.notes IS '注释';
COMMENT ON COLUMN filemanager_sys_base_type.ext1 IS '扩展字段1';
COMMENT ON COLUMN filemanager_sys_base_type.ext2 IS '扩展字段2';
COMMENT ON COLUMN filemanager_sys_base_type.ext3 IS '扩展字段3';


/*实体-用户信息实体类*/
CREATE TABLE user_info (
  user_id           NUMBER(12) CONSTRAINT USER_INFO_PK PRIMARY KEY,
  user_name         VARCHAR2(32) NOT NULL,
  user_password     VARCHAR2(32) NOT NULL,
  user_gender       NUMBER(1) CHECK (user_gender IN (0, 1)),
  user_age          NUMBER(3)    NOT NULL,
  college_id        NUMBER(8),
  manager           NUMBER(1),
  manager_type      NUMBER(1),
  user_address      VARCHAR2(128),
  user_email        VARCHAR2(32) NOT NULL,
  user_phone_number NUMBER(11),
  create_date       DATE         NOT NULL,
  validata_code VARCHAR2(128),
  out_date      NUMBER,
  notes             VARCHAR2(128),
  ext1              NUMBER(8),
  ext2              VARCHAR2(32),
  ext3              VARCHAR2(32),
  rec_status        NUMBER(1)    NOT NULL
);

COMMENT ON COLUMN user_info.user_id IS '用户编号';
COMMENT ON COLUMN user_info.user_name IS '用户名';
COMMENT ON COLUMN user_info.user_password IS '用户密码';
COMMENT ON COLUMN user_info.user_gender IS '用户性别';
COMMENT ON COLUMN user_info.user_age IS '用户年龄';
COMMENT ON COLUMN user_info.college_id IS '学院';
COMMENT ON COLUMN user_info.manager IS '管理员,0否，1是';
COMMENT ON COLUMN user_info.manager_type IS '管理员类型，1超级管理员，2普通管理员';
COMMENT ON COLUMN user_info.user_email IS '邮箱';
COMMENT ON COLUMN user_info.user_phone_number IS '手机号';
COMMENT ON COLUMN user_info.create_date IS '创建日期';
COMMENT ON COLUMN user_info.validata_code IS '有效编号,用于找回密码';
COMMENT ON COLUMN user_info.out_date IS '失效时间，用于找回密码';
COMMENT ON COLUMN user_info.notes IS '注释';
COMMENT ON COLUMN user_info.ext1 IS '扩展字段1';
COMMENT ON COLUMN user_info.ext2 IS '扩展字段2';
COMMENT ON COLUMN user_info.ext3 IS '扩展字段3';
COMMENT ON COLUMN user_info.rec_status IS '是否有效，1有效，0无效';

/*文件表*/
CREATE TABLE file_info (
  file_id     NUMBER(12) CONSTRAINT file_info_pk PRIMARY KEY,
  college_id  NUMBER(8),
  user_id     NUMBER(12)    NOT NULL,
  file_name   VARCHAR2(128) NOT NULL,
  path_code   NUMBER(8)     NOT NULL,
  file_type   NUMBER(8),
  create_date DATE          NOT NULL,
  notes       VARCHAR2(128),
  ext1        NUMBER(8),
  ext2        VARCHAR2(32),
  ext3        VARCHAR2(32),
  rec_status  NUMBER(1)     NOT NULL
);

COMMENT ON COLUMN file_info.file_id IS '文件编号';
COMMENT ON COLUMN file_info.college_id IS '学院编号';
COMMENT ON COLUMN file_info.user_id IS '上传者编号';
COMMENT ON COLUMN file_info.file_name IS '文件名称';
COMMENT ON COLUMN file_info.path_code IS '文件保存路径编码';
COMMENT ON COLUMN file_info.file_type IS '文件类型';
COMMENT ON COLUMN file_info.create_date IS '文件上传时间';
COMMENT ON COLUMN file_info.notes IS '注释';
COMMENT ON COLUMN file_info.ext1 IS '扩展字段1';
COMMENT ON COLUMN file_info.ext2 IS '扩展字段2';
COMMENT ON COLUMN file_info.ext3 IS '扩展字段3';
COMMENT ON COLUMN file_info.rec_status IS '是否有效，1有效，0无效';

CREATE TABLE file_collection_info (
  user_id     NUMBER(12)    NOT NULL,
  uploader   VARCHAR2(32)    NOT NULL,
  file_id     NUMBER(12)    NOT NULL,
  file_name   VARCHAR2(128) NOT NULL,
  college_id  NUMBER(8),
  create_date DATE          NOT NULL,
  ext1        NUMBER(8),
  ext2        VARCHAR2(32),
  ext3        VARCHAR2(32)
);

COMMENT ON COLUMN file_collection_info.user_id IS '用户编号';
COMMENT ON COLUMN file_collection_info.uploader IS '上传者名称';
COMMENT ON COLUMN file_collection_info.file_id IS '文件编号';
COMMENT ON COLUMN file_collection_info.file_name IS '文件名';
COMMENT ON COLUMN file_collection_info.college_id IS '学院编号';
COMMENT ON COLUMN file_collection_info.create_date IS '创建时间';
COMMENT ON COLUMN file_collection_info.ext1 IS '扩展字段1';
COMMENT ON COLUMN file_collection_info.ext2 IS '扩展字段2';
COMMENT ON COLUMN file_collection_info.ext3 IS '扩展字段3';

/**
 * 获取序列号
 */
CREATE SEQUENCE seq_done_code
INCREMENT BY 1
START WITH 1
MAXVALUE 999999999;


