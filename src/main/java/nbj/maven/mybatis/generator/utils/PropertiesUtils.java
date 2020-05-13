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
        final Map<String, String> ret = new HashMap<>();
        for (final Map.Entry<Object, Object> entry : properties.entrySet()) {
            final String key = (String) entry.getKey();
            final String value = (String) entry.getValue();
            ret.put(key, value);
        }
        return ret;
    }
}
