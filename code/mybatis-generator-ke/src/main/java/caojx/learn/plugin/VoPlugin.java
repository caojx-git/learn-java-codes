package caojx.learn.plugin;

import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.List;
import java.util.Properties;

/**
 * @description: 扩展mybatis_generator，自定义Vo生成
 * @author caojx
 * @date 2018年7月6日
 * @version 1.0
 */
public class VoPlugin extends FalseMethodPlugin {

    /**
     * 默认继承的Dao(Mapper)接口
     */
    private static final String DEFAULT_DAO_SUPER_CLASS = "KeMapper";
    private static final String DEFAULT_EXPAND_DAO_SUPER_CLASS = "com.lianjia.cto.ke.util.BaseExpandMapper";
    private static final String DEFAULT_VO_SUFFIX = "Vo";

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
     * vo扩展名
     */
    private String voSuffix;

    private ShellCallback shellCallback = null;

    /**
     * properties配置文件
     */
    private Properties systemPro;

    /**
     * 作者
     */
    private String author;

    public VoPlugin() {
        systemPro = System.getProperties();
        shellCallback = new DefaultShellCallback(false);
    }

    /**
     * 验证参数是否有效
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

        expandDaoTargetPackage = properties.getProperty("expandTargetPackage");
        expandDaoSuperClass = properties.getProperty("expandDaoSuperClass");
        if (!StringUtility.stringHasValue(expandDaoSuperClass)) {
            expandDaoSuperClass = DEFAULT_EXPAND_DAO_SUPER_CLASS;
        }

        voSuffix = properties.getProperty("voSuffix");
        if (!StringUtility.stringHasValue(voSuffix)) {
            voSuffix = DEFAULT_VO_SUFFIX;
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
     * 给Model添加扩展名
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        try{
            FullyQualifiedJavaType fullyQualifiedJavaType =  topLevelClass.getType();
            java.lang.reflect.Field baseQualifiedNameField =  fullyQualifiedJavaType.getClass().getDeclaredField("baseQualifiedName");
            java.lang.reflect.Field baseShortNameField =  fullyQualifiedJavaType.getClass().getDeclaredField("baseShortName");
            baseQualifiedNameField.setAccessible(true);
            baseShortNameField.setAccessible(true);

            System.out.println(baseShortNameField.get(fullyQualifiedJavaType));
            System.out.println(baseQualifiedNameField.get(fullyQualifiedJavaType));

            String baseShortNameFieldValue = baseShortNameField.get(fullyQualifiedJavaType).toString();
            String baseQualifiedNameFieldValue = baseQualifiedNameField.get(fullyQualifiedJavaType).toString();

            baseShortNameField.set(topLevelClass.getType(), baseShortNameFieldValue+voSuffix);
            baseQualifiedNameField.set(topLevelClass.getType(), baseQualifiedNameFieldValue+voSuffix);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
    }
}
