package nbj.maven.mybatis.generator.domain;

import java.util.Arrays;
import java.util.List;

/**
 * @author zebin
 * @since 2020-04-29.
 */
public class MBGConst {

    /**
     * 是否生成注释。
     * 1=是（default）；0=否。
     */
    public static final String COMMENT_GEN = "nbj.comment.gen";

    /**
     * 生成的注释的 created by 类型。
     * plugins=插件生成（default）；user=系统用户名；none=没有
     */
    public static final String COMMENT_CREATED = "nbj.comment.created";

    /**
     * 生成的注释的 @author
     * __user=系统用户名（default）；__plugins=插件；__none=没有；（其他非空值）
     */
    public static final String COMMENT_AUTHOR = "nbj.comment.author";

    public static final List<String> PLUGINS_PROPERTIES = Arrays.asList(COMMENT_GEN, COMMENT_CREATED, COMMENT_AUTHOR);
}
