package so.asch.sdk.dbc;


import java.util.function.Predicate;

/**
 * DBC前置条件
 * @author eagle
 *
 */
public class Argument {
    public static void require(boolean condition, String errorInfo) throws ContractException {
        if (!condition)
            throw new ContractException(errorInfo);
    }

    public static <T>void optional(T object, Predicate<T> validation, String errorInfo) throws ContractException {
        if (object != null && !validation.test(object))
            throw new ContractException(errorInfo);
    }

    public static void notNull(Object argument, String argumentName)throws ContractException {
        if (argument == null)
            throw new ContractException(String.format("Argument %s can not be NULL", argumentName ));
    }

    public static void notNullOrEmpty(byte[] argument, String argumentName)throws ContractException {
        if (argument == null)
            throw new ContractException(String.format("Argument '%s' can not be NULL", argumentName ));
        if (argument.length == 0)
            throw new ContractException(String.format("Argument '%s' can not be Empty", argumentName ));
    }

    public static void notNullOrEmpty(String argument, String argumentName)throws ContractException {
        if (argument == null)
            throw new ContractException(String.format("Argument '%s' can not be NULL", argumentName ));
        if (argument == "")
            throw new ContractException(String.format("Argument '%s' can not be Empty", argumentName ));
    }

    public static void length(String argument, int length, String argumentName)throws ContractException {
        if (argument == null)
            throw new ContractException(String.format("Argument '%s' can not be NULL", argumentName ));

        if (argument.length() != length)
            throw new ContractException(String.format("Argument '%s' length must be %d", argumentName, length ));
    }

    public static void length(byte[] argument, int length, String argumentName)throws ContractException {
        if (argument == null)
            throw new ContractException(String.format("Argument '%s' can not be NULL", argumentName ));

        if (argument.length != length)
            throw new ContractException(String.format("Argument '%s' length must be %d", argumentName, length ));
    }

    public static void length(String argument, int minLength, int maxLength, String argumentName)throws ContractException {
        if (argument == null)
            throw new ContractException(String.format("Argument '%s' can not be NULL", argumentName ));

        if (argument.length()< minLength || argument.length() > maxLength)
            throw new ContractException(String.format("Argument '%s' length must between %d and %d", argumentName, minLength, maxLength ));
    }

    public static void match(String argument, String pattern, String argumentName)throws ContractException {
        if (!argument.matches(pattern)){
            throw new ContractException(String.format("Argument '%s' must match '%s'", argumentName, pattern ));
        }
    }
}
