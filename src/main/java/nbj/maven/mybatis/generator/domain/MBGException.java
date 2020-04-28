package nbj.maven.mybatis.generator.domain;

/**
 * @author zebin
 * @since 2020-04-28.
 */
public class MBGException extends RuntimeException {
    public MBGException() {
        super();
    }

    public MBGException(String message) {
        super(message);
    }

    public MBGException(String message, Throwable cause) {
        super(message, cause);
    }

    public MBGException(Throwable cause) {
        super(cause);
    }

    protected MBGException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
