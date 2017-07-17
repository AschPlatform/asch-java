package so.asch.sdk.codec;

/**
 * Created by eagle on 17-7-16.
 */
public class DecodingException extends Exception{
    private static final long serialVersionUID = 1L;

    public DecodingException(String message, Throwable parent) {
        super(message, parent);
    }

    public DecodingException(String message) {
        super(message);
    }

    public DecodingException(Throwable t) {
        super(t);
    }
}
