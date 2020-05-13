package nbj.maven.mybatis.generator.domain;

/**
 * @author zebin
 * @since 2020-04-29.
 */
public interface MBGConst {

    /**
     * 项目名称
     */
    String PROJECT_NAME = "nbj-mybatis-generator-plugins";

    /**
     * 生成的注释的 created by 类型。
     * plugins=插件生成（default）；user=系统用户名；none=没有
     */
    String COMMENT_CREATED = "nbj.comment.created";

    /**
     * 生成的注释的 @author
     * __user=系统用户名（default）；__plugins=插件；__none=没有；（其他非空值）
     */
    String COMMENT_AUTHOR = "nbj.comment.author";


}
