package so.asch.sdk.impl.Log;

public class NopLogger implements Logger {
    @Override
    public void error(String error, Throwable throwable) {

    }

    @Override
    public void info(String info) {

    }

    @Override
    public void warn(String warn) {

    }

    @Override
    public void debug(String debug) {

    }

    @Override
    public boolean isDebugEnabled() {
        return false;
    }

    @Override
    public boolean isErrorEnabled() {
        return false;
    }

    @Override
    public boolean isInfoEnabled() {
        return false;
    }

    @Override
    public boolean isWarnEnabled() {
        return false;
    }
}
