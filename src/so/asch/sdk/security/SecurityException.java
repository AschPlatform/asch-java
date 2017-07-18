package so.asch.sdk.security;

/**
 * 加解密签名异常类
 * @author eagle
 *
 */
public class SecurityException extends Exception {
    private static final long serialVersionUID = 1L;

    public SecurityException(String message, Throwable parent) {
        super(message, parent);
    }

    public SecurityException(String message) {
        super(message);
    }

    public SecurityException(Throwable t) {
        super(t);
    }
}
