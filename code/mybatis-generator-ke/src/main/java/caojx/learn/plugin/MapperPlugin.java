package caojx.learn.plugin;

import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.exception.ShellException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.generator.internal.util.StringUtility;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * @description: 扩展mybatis_generator，自定义Mapper
 * @author caojx
 * @date 2018年7月6日
 * @version 1.0
 */
public class MapperPlugin extends FalseMethodPlugin {

    /**
     * 默认继承的Dao(Mapper)接口
     */
    private static final String DEFAULT_DAO_SUPER_CLASS = "KeMapper";
    private static final String DEFAULT_EXPAND_DAO_SUPER_CLASS = "com.lianjia.cto.ke.util.BaseExpandMapper";

    /**
     * Dao(Mapper)接口扩展名
     */
    private static final String DEFAULT_DAO_SUFFIX = "Mapper";

    /**
     * dao(Mapper接口)所在文件夹targetProject
     */
    private String daoTargetDir;
    /**
     * dao(Mapper接口)所在包targetPackage
     */
    private String daoTargetPackage;

    /**
     * dao(Mapper继承的父接口)daoSuperClass
     */
    private String daoSuperClass;

    // 扩展
    private String expandDaoTargetPackage;
    private String expandDaoSuperClass;

    /**
     * Dao（Mapper）扩展名
     */
    private String daoSuffix;

    private ShellCallback shellCallback = null;

    /**
     * properties配置文件
     */
    private Properties systemPro;

    /**
     * 作者
     */
    private String author;

    public MapperPlugin() {
        systemPro = System.getProperties();
        shellCallback = new DefaultShellCallback(false);
    }

    /**
     * 获取配置参数并验证参数是否有效
     * @param warnings
     * @return
     */
    public boolean validate(List<String> warnings) {
        daoTargetDir = properties.getProperty("targetProject");
        boolean valid = StringUtility.stringHasValue(daoTargetDir);

        daoTargetPackage = properties.getProperty("targetPackage");
        boolean valid2 = StringUtility.stringHasValue(daoTargetPackage);

        daoSuperClass = properties.getProperty("daoSuperClass");
        if (!StringUtility.stringHasValue(daoSuperClass)) {
            daoSuperClass = DEFAULT_DAO_SUPER_CLASS;
        }

        daoSuffix = properties.getProperty("daoSuffix");
        if(!StringUtility.stringHasValue(daoSuffix)){
            daoSuffix = DEFAULT_DAO_SUFFIX;
        }

        expandDaoTargetPackage = properties.getProperty("expandTargetPackage");
        expandDaoSuperClass = properties.getProperty("expandDaoSuperClass");
        if (!StringUtility.stringHasValue(expandDaoSuperClass)) {
            expandDaoSuperClass = DEFAULT_EXPAND_DAO_SUPER_CLASS;
        }

        author = properties.getProperty("author");
        if (!StringUtility.stringHasValue(author)) {
            author = systemPro.getProperty("user.name");
        }
        return valid && valid2;
    }

    /**
     * 生成mapping 添加自定义sql
     * @param document
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {

        //创建Select查询
    /*    XmlElement select = new XmlElement("select");
        select.addAttribute(new Attribute("id", "selectAll"));
        select.addAttribute(new Attribute("resultMap", "BaseResultMap"));
        select.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType()));
        select.addElement(new TextElement("select * from " + introspectedTable.getFullyQualifiedTableNameAtRuntime()));

        XmlElement queryPage = new XmlElement("select");
        queryPage.addAttribute(new Attribute("id", "queryPage"));
        queryPage.addAttribute(new Attribute("resultMap", "BaseResultMap"));
        queryPage.addAttribute(new Attribute("parameterType", "com.fendo.bean.Page"));
        queryPage.addElement(new TextElement("select * from " + introspectedTable.getFullyQualifiedTableNameAtRuntime()));

        XmlElement parentElement = document.getRootElement();
        parentElement.addElement(select);
        parentElement.addElement(queryPage);*/
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }

    /**
     * 给Mapper（Dao）添加添加扩展名并添加类的文档注释
     * @param introspectedTable
     * @return
     */
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        JavaFormatter javaFormatter = context.getJavaFormatter();
        List<GeneratedJavaFile> mapperJavaFiles = new ArrayList<GeneratedJavaFile>();
        SimpleDateFormat sdf1=new SimpleDateFormat("yyyy");
        SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd");

        for (GeneratedJavaFile javaFile : introspectedTable.getGeneratedJavaFiles()) {
            CompilationUnit unit = javaFile.getCompilationUnit();
            FullyQualifiedJavaType baseModelJavaType = unit.getType();

            String shortName = baseModelJavaType.getShortName();

            GeneratedJavaFile mapperJavafile = null;

            if (shortName.endsWith("Mapper")) { // 扩展Mapper
                if (StringUtility.stringHasValue(expandDaoTargetPackage)) {
                    Interface mapperInterface = new Interface(expandDaoTargetPackage + "." + shortName.replace("Mapper", "ExpandMapper"));
                    //文档注释
                    mapperInterface.setVisibility(JavaVisibility.PUBLIC);
                    mapperInterface.addJavaDocLine("/**");
                    mapperInterface.addJavaDocLine(" *");
                    mapperInterface.addJavaDocLine(" * @author "+author);
                    mapperInterface.addJavaDocLine(" * @description  "+shortName+" 扩展");
                    mapperInterface.addJavaDocLine(" * @Copyright (c) "+ sdf1.format(new Date())+", Lianjia Group All Rights Reserved.");
                    mapperInterface.addJavaDocLine(" * @date "+sdf2.format(new Date())+"");
                    mapperInterface.addJavaDocLine(" * @version 1.0");
                    mapperInterface.addJavaDocLine(" */");

                    //添加泛型支持
                    FullyQualifiedJavaType daoSuperType = new FullyQualifiedJavaType(expandDaoSuperClass);
                    mapperInterface.addImportedType(daoSuperType);
                    mapperInterface.addSuperInterface(daoSuperType);

                    mapperJavafile = new GeneratedJavaFile(mapperInterface, daoTargetDir, javaFormatter);
                    try {
                        File mapperDir = shellCallback.getDirectory(daoTargetDir, daoTargetPackage);
                        File mapperFile = new File(mapperDir, mapperJavafile.getFileName());
                        // 文件不存在
                        if (!mapperFile.exists()) {
                            mapperJavaFiles.add(mapperJavafile);
                        }
                    } catch (ShellException e) {
                        e.printStackTrace();
                    }
                }
            } else if (!shortName.endsWith("Example")) {

                //给Dao接口添加扩展名Mapper
                Interface mapperInterface = new Interface(daoTargetPackage + "." + shortName + daoSuffix);

                //给Dao接口添加文档注释
                mapperInterface.setVisibility(JavaVisibility.PUBLIC);

                mapperInterface.addJavaDocLine("/**");
                mapperInterface.addJavaDocLine(" *");
                mapperInterface.addJavaDocLine(" * @author "+author);
                mapperInterface.addJavaDocLine(" * @description  ");
                mapperInterface.addJavaDocLine(" * @Copyright (c) "+ sdf1.format(new Date())+", Lianjia Group All Rights Reserved.");
                mapperInterface.addJavaDocLine(" * @date "+sdf2.format(new Date())+"");
                mapperInterface.addJavaDocLine(" * @version 1.0");
                mapperInterface.addJavaDocLine(" */");

                FullyQualifiedJavaType daoSuperType = new FullyQualifiedJavaType(daoSuperClass);
                // 添加泛型支持
                daoSuperType.addTypeArgument(baseModelJavaType);
                mapperInterface.addImportedType(baseModelJavaType);
                mapperInterface.addImportedType(daoSuperType);
                mapperInterface.addSuperInterface(daoSuperType);

                mapperJavafile = new GeneratedJavaFile(mapperInterface, daoTargetDir, javaFormatter);
                mapperJavaFiles.add(mapperJavafile);

            }
        }
        return mapperJavaFiles;
    }

}
