package so.asch.sdk.dbc;

/**
 * DBC异常类
 * @author eagle
 *
 */
public class ContactException extends Exception {
    private static final long serialVersionUID = 1L;

    public ContactException(String message, Throwable parent) {
        super(message, parent);
    }

    public ContactException(String message) {
        super(message);
    }

    public ContactException(Throwable t) {
        super(t);
    }
}
