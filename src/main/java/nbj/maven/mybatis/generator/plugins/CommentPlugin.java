package nbj.maven.mybatis.generator.plugins;

import nbj.maven.mybatis.generator.domain.MBGConst;
import nbj.maven.mybatis.generator.utils.PropertiesUtils;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.AbstractJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author zebin
 * @since 2020-05-13.
 */
public class CommentPlugin extends PluginAdapter {

    @Override
    public boolean validate(final List<String> warnings) {
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(final TopLevelClass topLevelClass, final IntrospectedTable introspectedTable) {
        this.addClassCommonJavaDocLines(topLevelClass, this.properties);
        return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
    }

    @Override
    public boolean modelRecordWithBLOBsClassGenerated(final TopLevelClass topLevelClass, final IntrospectedTable introspectedTable) {
        this.addClassCommonJavaDocLines(topLevelClass, this.properties);
        return super.modelRecordWithBLOBsClassGenerated(topLevelClass, introspectedTable);
    }

    @Override
    public boolean modelExampleClassGenerated(final TopLevelClass topLevelClass, final IntrospectedTable introspectedTable) {
        this.addClassCommonJavaDocLines(topLevelClass, this.properties);
        return super.modelExampleClassGenerated(topLevelClass, introspectedTable);
    }

    @Override
    public boolean clientGenerated(final Interface interfaze, final IntrospectedTable introspectedTable) {
        this.addClassCommonJavaDocLines(interfaze, this.properties);
        return super.clientGenerated(interfaze, introspectedTable);
    }

    private void addClassCommonJavaDocLines(final AbstractJavaType javaType, final Properties properties) {
        final Map<String, String> propertiesMap = PropertiesUtils.parsePropertiesToMap(properties);
        if (javaType.getJavaDocLines() != null && javaType.getJavaDocLines().size() > 0) {
            //已经有注释
            return;
        }
        javaType.addJavaDocLine("/**");
        final String commentCreated = propertiesMap.getOrDefault(MBGConst.COMMENT_CREATED, "__plugins").toLowerCase();
        if ("__user".equals(commentCreated)) {
            javaType.addJavaDocLine(" * Created by " + System.getProperties().getProperty("user.name"));
        } else if ("__none".equals(commentCreated)) {
        } else {
            javaType.addJavaDocLine(" * Created by " + MBGConst.PROJECT_NAME);
        }
        final String commentAuthor = propertiesMap.getOrDefault(MBGConst.COMMENT_AUTHOR, "__user").toLowerCase();
        if ("__plugins".equals(commentAuthor)) {
            javaType.addJavaDocLine(" * @author " + MBGConst.PROJECT_NAME);
        } else if ("__none".equals(commentAuthor)) {
        } else if ("__user".equals(commentAuthor)) {
            javaType.addJavaDocLine(" * @author " + System.getProperties().getProperty("user.name"));
        } else {
            javaType.addJavaDocLine(" * @author " + commentAuthor);
        }
        final String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        javaType.addJavaDocLine(" * @since " + dateStr + ".");
        javaType.addJavaDocLine(" */");
    }
}
