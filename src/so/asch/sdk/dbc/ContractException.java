package so.asch.sdk.dbc;

/**
 * DBC异常类
 * @author eagle
 *
 */
public class ContractException extends Exception {
    private static final long serialVersionUID = 1L;

    public ContractException(String message, Throwable parent) {
        super(message, parent);
    }

    public ContractException(String message) {
        super(message);
    }

    public ContractException(Throwable t) {
        super(t);
    }
}
