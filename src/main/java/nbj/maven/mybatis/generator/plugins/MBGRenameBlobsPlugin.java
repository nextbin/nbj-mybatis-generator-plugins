package nbj.maven.mybatis.generator.plugins;

import nbj.maven.mybatis.generator.domain.MBGException;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.ModelType;

import java.util.List;

/**
 * 移除多余的Blobs方法
 * 支持Runtime：MyBatis3
 * 支持DefaultModelType：flat
 *
 * selectByExampleWithBLOBs ==rename==> selectByExample
 * updateByExampleWithBLOBs ==rename==> updateByExample
 * updateByPrimaryKeyWithBLOBs ==rename==> updateByPrimaryKey
 *
 * @author zebin
 * @since 2020-04-28.
 */
public class MBGRenameBlobsPlugin extends PluginAdapter {

    @Override
    public boolean validate(final List<String> warnings) {
        if (!"MyBatis3".equalsIgnoreCase(this.context.getTargetRuntime())) {
            warnings.add("MBGRenameBlobsPlugin only supports targetRuntime=MyBatis3");
            return false;
        }
        if (this.context.getDefaultModelType() != ModelType.FLAT) {
            warnings.add("MBGRenameBlobsPlugin only supports defaultModelType=flat");
            return false;
        }
        return true;
    }

    @Override
    public boolean clientSelectByExampleWithBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        method.setName(method.getName().replaceFirst("WithBLOBs$", ""));
        return true;
    }

    @Override
    public boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByExampleWithBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        method.setName(method.getName().replaceFirst("WithBLOBs$", ""));
        return true;
    }

    @Override
    public boolean clientUpdateByExampleWithoutBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        method.setName(method.getName().replaceFirst("WithBLOBs$", ""));
        return true;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapResultMapWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return super.sqlMapResultMapWithoutBLOBsElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean sqlMapResultMapWithBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return super.sqlMapResultMapWithBLOBsElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapSelectByExampleWithBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        this.renameXmlElementId(element);
        return true;
    }

    @Override
    public boolean sqlMapUpdateByExampleWithBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        this.renameXmlElementId(element);
        return true;
    }

    @Override
    public boolean sqlMapUpdateByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapUpdateByPrimaryKeyWithBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        this.renameXmlElementId(element);
        return true;
    }

    @Override
    public boolean sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    private void renameXmlElementId(XmlElement element) {
        try {
            for (final Attribute attribute : element.getAttributes()) {
                if ("id".equals(attribute.getName())) {
                    java.lang.reflect.Field field = attribute.getClass().getDeclaredField("value");
                    boolean isAccessible = field.isAccessible();
                    field.setAccessible(true);
                    field.set(attribute, attribute.getValue().replaceFirst("WithBLOBs$", ""));
                    field.setAccessible(isAccessible);
                    break;
                }
            }
        } catch (Exception e) {
            throw new MBGException(e);
        }
    }
}
