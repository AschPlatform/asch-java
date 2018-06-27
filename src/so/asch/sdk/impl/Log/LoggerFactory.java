package so.asch.sdk.impl.Log;

public class LoggerFactory {
    public static Logger getLogger(Class clazz){
        return new NopLogger();
    }
}
