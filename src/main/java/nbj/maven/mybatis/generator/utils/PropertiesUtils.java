package nbj.maven.mybatis.generator.utils;

import nbj.maven.mybatis.generator.domain.MBGConst;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author zebin
 * @since 2020-04-29.
 */
public final class PropertiesUtils {
    private PropertiesUtils() {}

    public static Map<String, String> parsePropertiesToMap(final Properties properties) {
        final Map<String, String> mbgProperties = new HashMap<>();
        for (final Map.Entry<Object, Object> entry : properties.entrySet()) {
            final String key = (String) entry.getKey();
            final String value = (String) entry.getValue();
            if (MBGConst.PLUGINS_PROPERTIES.contains(key)) {
                mbgProperties.put(key, value);
            }
        }
        return mbgProperties;
    }
}
