package nbj.maven.mybatis.generator.plugins;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;
import java.util.logging.Logger;

/**
 * Thrift-Swift
 * 1. 实体类 model 需要新增 @ThriftStruct("TDomain") 注解
 * 2. 实体类 model 的成员变量的 getter && setter 需要新增 @ThriftField(value=xxx,name="xxx")
 * todo thrift 不支持的 数据类型/字段命名 需要支持配置告警或中断
 * @author zebin
 * @since 2020-05-13.
 */
public class ThriftWithSwiftPlugin extends PluginAdapter {

    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private Integer fieldIndex = 0;

    @Override
    public boolean validate(final List<String> warnings) {
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(final TopLevelClass topLevelClass, final IntrospectedTable introspectedTable) {
        final String clazzName = topLevelClass.getType().getShortName();
        this.logger.info("add annotation ThriftStruct for class: " + clazzName);
        topLevelClass.addImportedType("com.facebook.swift.codec.*");
        topLevelClass.addAnnotation("@ThriftStruct(\"T" + clazzName + "\")");
        return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
    }

    @Override
    public boolean modelGetterMethodGenerated(final Method method, final TopLevelClass topLevelClass, final IntrospectedColumn introspectedColumn, final IntrospectedTable introspectedTable, final ModelClassType modelClassType) {
        final String clazzName = topLevelClass.getType().getShortName();
        this.logger.info("add annotation ThriftField for class: " + clazzName + ", method: " + method.getName());
        String fieldName = method.getName().replaceFirst("^get", "");
        if (fieldName.length() == 1) {
            fieldName = (fieldName.charAt(0) + "").toLowerCase();
        } else {
            fieldName = (fieldName.charAt(0) + "").toLowerCase() + fieldName.substring(1);
        }
        method.addAnnotation("@ThriftField(value = " + this.fieldIndex + " , name = \"" + fieldName + "\")");
        this.fieldIndex++;
        return super.modelGetterMethodGenerated(method, topLevelClass, introspectedColumn, introspectedTable, modelClassType);
    }


    @Override
    public boolean modelSetterMethodGenerated(final Method method, final TopLevelClass topLevelClass, final IntrospectedColumn introspectedColumn, final IntrospectedTable introspectedTable, final ModelClassType modelClassType) {
        final String clazzName = topLevelClass.getType().getShortName();
        this.logger.info("add annotation ThriftField, class: " + clazzName + ", method: " + method.getName());
        method.addAnnotation("@ThriftField");
        return super.modelSetterMethodGenerated(method, topLevelClass, introspectedColumn, introspectedTable, modelClassType);
    }
}
