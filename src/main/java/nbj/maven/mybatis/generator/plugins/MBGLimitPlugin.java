package nbj.maven.mybatis.generator.plugins;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

/**
 * 分页功能：Example 增加 offset, limit
 * 支持Runtime：MyBatis3
 * 
 * 官方分页插件 {@link org.mybatis.generator.plugins.RowBoundsPlugin} 有弊端，需要把数据都查出来再过滤，数据量大时影响性能
 * https://mybatis.org/generator/reference/plugins.html
 * 
 * @author zebin
 * @since 2020-04-28.
 */
public class MBGLimitPlugin extends PluginAdapter {

    @Override
    public boolean validate(final List<String> warnings) {
        if (!"MyBatis3".equalsIgnoreCase(context.getTargetRuntime())) {
            warnings.add("MBGLimitPlugin only supported for MyBatis3");
            return false;
        }
        return true;
    }

    @Override
    public boolean modelExampleClassGenerated(final TopLevelClass topLevelClass, final IntrospectedTable introspectedTable) {
        //指定 limit, offset 的数据类型为 Integer
        final FullyQualifiedJavaType integerTypeWrapper = FullyQualifiedJavaType.getIntInstance().getPrimitiveTypeWrapper();

        //新增 limit 属性和 getter/setter
        final Field limit = new Field("limit", integerTypeWrapper);
        limit.setVisibility(JavaVisibility.PRIVATE);
        topLevelClass.addField(limit);

        final Method setLimit = new Method("setLimit");
        setLimit.setVisibility(JavaVisibility.PUBLIC);
        setLimit.addParameter(new Parameter(integerTypeWrapper, "limit"));
        setLimit.addBodyLine("this.limit = limit;");
        topLevelClass.addMethod(setLimit);

        final Method getLimit = new Method("getLimit");
        getLimit.setVisibility(JavaVisibility.PUBLIC);
        getLimit.setReturnType(integerTypeWrapper);
        getLimit.addBodyLine("return limit;");
        topLevelClass.addMethod(getLimit);

        //新增 offset 属性和 getter/setter
        final Field offset = new Field("offset", integerTypeWrapper);
        offset.setVisibility(JavaVisibility.PRIVATE);
        topLevelClass.addField(offset);

        final Method setOffset = new Method("setOffset");
        setOffset.setVisibility(JavaVisibility.PUBLIC);
        setOffset.addParameter(new Parameter(integerTypeWrapper, "offset"));
        setOffset.addBodyLine("this.offset = offset;");
        topLevelClass.addMethod(setOffset);

        final Method getOffset = new Method("getOffset");
        getOffset.setVisibility(JavaVisibility.PUBLIC);
        getOffset.setReturnType(integerTypeWrapper);
        getOffset.addBodyLine("return offset;");
        topLevelClass.addMethod(getOffset);

        return true;
    }

    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(final XmlElement element, final IntrospectedTable introspectedTable) {
        this.addIfLimitNotNullElement(element);
        return true;
    }

    @Override
    public boolean sqlMapSelectByExampleWithBLOBsElementGenerated(final XmlElement element, final IntrospectedTable introspectedTable) {
        this.addIfLimitNotNullElement(element);
        return true;
    }

    /**
     * 给已经生成的 sql 追加分页内容： 
     * <if test="limit != null">
     *     <if test="offset != null">
     *         limit ${offset}, ${limit}
     *     </if>
     *     <if test="offset == null">
     *         limit ${limit}
     *     </if>
     * </if> 
     * @param element 已经生成的 sql XmlElement
     */
    private void addIfLimitNotNullElement(final XmlElement element) {

        final XmlElement ifLimitNotNullElement = new XmlElement("if");
        ifLimitNotNullElement.addAttribute(new Attribute("test", "limit != null"));

        final XmlElement ifOffsetNotNullElement = new XmlElement("if");
        ifOffsetNotNullElement.addAttribute(new Attribute("test", "offset != null"));
        ifOffsetNotNullElement.addElement(new TextElement("limit ${offset}, ${limit}"));
        ifLimitNotNullElement.addElement(ifOffsetNotNullElement);

        final XmlElement ifOffsetNullElement = new XmlElement("if");
        ifOffsetNullElement.addAttribute(new Attribute("test", "offset == null"));
        ifOffsetNullElement.addElement(new TextElement("limit ${limit}"));
        ifLimitNotNullElement.addElement(ifOffsetNullElement);

        element.addElement(ifLimitNotNullElement);
    }

}
