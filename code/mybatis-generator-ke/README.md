# mybatis-generator-ke

该项目用于快速生成vo、model、mapper、mapping

## 一、快速入门
### 1.1 StartUp

该代码位于test/java目录下，StartUp是生成代码的入口   
generatorConfig.xml 用 model、mapper、mapping的生成，代码生成路径可自行配置    
generatorConfig-vo.xml 用于vo的生成，代码生成路径可自行配置

### 1.2 主要配置项

#### 1.作者
将author改成自己的名称，不配置使用默认
```xml
      <!-- 实现自定义的代码生成器插件 -->
        <plugin type="MapperPlugin">
            <property name="targetProject" value="src/test/java"/>
            <property name="targetPackage" value="com.lianjia.cto.ke.dao"/>
            <property name="daoSuperClass" value="KeMapper"/>
            <!--Mapper扩展名-->
            <property name="daoSuffix" value="Mapper"/>
            <!--author-->
            <property name="author" value="caojianxinag001"/>
        </plugin>

        <!--自定义注释插件-->
        <commentGenerator type="MyCommentGenerator">
            <property name="suppressAllComments" value="false"></property>
            <!--author-->
            <property name="author" value="caojianxinag001"/>
        </commentGenerator>
```

#### 2.配置需要生成的表
```xml
         <!--指定数据库表-->
        <table  tableName="area_code" enableSelectByExample="false" enableDeleteByExample="false"
                enableCountByExample="false" enableUpdateByExample="false"
                selectByExampleQueryId="false" domainObjectName="AreaCode">
            <generatedKey column="ID" sqlStatement="MySql" identity="true"/>
            <!--设置别名, 不指定的话(下边的可以不配置)，按照默认规则-->
            <columnOverride column="id" property="Id" />
            <columnOverride column="province_id" property="provinceId" />
            <columnOverride column="province_name" property="provinceName" />
            <columnOverride column="city_id" property="cityId" />
            <columnOverride column="city_name" property="cityName" />
            <columnOverride column="district_id" property="districtId" />
            <columnOverride column="district_name" property="districtName" />
            <columnOverride column="type" property="type" />
        </table>
```