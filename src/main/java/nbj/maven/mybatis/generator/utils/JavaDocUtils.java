package nbj.maven.mybatis.generator.utils;

import nbj.maven.mybatis.generator.domain.MBGConst;
import org.mybatis.generator.api.dom.java.AbstractJavaType;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

/**
 * @author zebin
 * @since 2020-04-29.
 */
public final class JavaDocUtils {
    private JavaDocUtils() {}

    public static void addClassCommonJavaDocLines(final AbstractJavaType javaType, final Properties properties) {
        final Map<String, String> propertiesMap = PropertiesUtils.parsePropertiesToMap(properties);
        if ("0".equals(propertiesMap.getOrDefault(MBGConst.COMMENT_GEN, "1"))) {
            return;
        }
        if (javaType.getJavaDocLines() != null && javaType.getJavaDocLines().size() > 0) {
            //已经有注释
            return;
        }
        javaType.addJavaDocLine("/**");
        final String commentCreated = propertiesMap.getOrDefault(MBGConst.COMMENT_CREATED, "plugins").toLowerCase();
        if ("user".equals(commentCreated)) {
            javaType.addJavaDocLine(" * Created by " + System.getProperties().getProperty("user.name"));
        } else if ("none".equals(commentCreated)) {
        } else {
            javaType.addJavaDocLine(" * Created by nbj-mybatis-generator-plugins");
        }
        final String commentAuthor = propertiesMap.getOrDefault(MBGConst.COMMENT_AUTHOR, "__user").toLowerCase();
        if ("__plugins".equals(commentAuthor)) {
            javaType.addJavaDocLine(" * @author plugins");
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
