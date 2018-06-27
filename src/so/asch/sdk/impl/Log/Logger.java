package so.asch.sdk.impl.Log;

public interface Logger {
    void error(String error, Throwable throwable);
    void info(String info);
    void warn(String warn);
    void debug(String debug);

    boolean isDebugEnabled();
    boolean isErrorEnabled();
    boolean isInfoEnabled();
    boolean isWarnEnabled();
}
