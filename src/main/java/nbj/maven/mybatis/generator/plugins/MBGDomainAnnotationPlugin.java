package nbj.maven.mybatis.generator.plugins;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.*;
import java.util.logging.Logger;

/**
 * 实体类增加注解
 * 支持Runtime：MyBatis3
 *
 * @author zebin
 * @since 2020-04-28.
 */
public class MBGDomainAnnotationPlugin extends PluginAdapter {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final Set<String> fullTypeSpecification = new HashSet<>();

    @Override
    public void setProperties(final Properties properties) {
        this.logger.info("" + properties);
        for (final Map.Entry<Object, Object> entry : properties.entrySet()) {
            final String key = (String) entry.getKey();
            final String value = (String) entry.getValue();
            if (key.startsWith("@")) {
                this.fullTypeSpecification.add(key.substring(1));
            }
        }
        super.setProperties(properties);
    }

    @Override
    public boolean validate(final List<String> warnings) {
        this.logger.info("");
        if (!"MyBatis3".equalsIgnoreCase(this.context.getTargetRuntime())) {
            warnings.add("MBGDomainAnnotationPlugin only supports targetRuntime=MyBatis3");
            return false;
        }
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(final TopLevelClass topLevelClass, final IntrospectedTable introspectedTable) {
        this.logger.info("");
        for (final String fullType : this.fullTypeSpecification) {
            this.logger.info("add annotation: " + fullType);
            topLevelClass.addImportedType(fullType);
            final String[] split = fullType.split("\\.");
            topLevelClass.addAnnotation("@" + split[split.length - 1]);
        }
        return true;
    }

    @Override
    public boolean modelRecordWithBLOBsClassGenerated(final TopLevelClass topLevelClass, final IntrospectedTable introspectedTable) {
        this.logger.info("");
        for (final String fullType : this.fullTypeSpecification) {
            this.logger.info("add annotation: " + fullType);
            topLevelClass.addImportedType(fullType);
            final String[] split = fullType.split("\\.");
            topLevelClass.addAnnotation("@" + split[split.length - 1]);
        }
        return true;
    }

}
