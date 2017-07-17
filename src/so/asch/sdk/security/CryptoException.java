package so.asch.sdk.security;

/**
 * 加解密签名异常类
 * @author eagle
 *
 */
public class CryptoException extends Exception {
    private static final long serialVersionUID = 1L;

    public CryptoException(String message, Throwable parent) {
        super(message, parent);
    }

    public CryptoException(String message) {
        super(message);
    }

    public CryptoException(Throwable t) {
        super(t);
    }
}
