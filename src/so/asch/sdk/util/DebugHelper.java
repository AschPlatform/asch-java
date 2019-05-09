package so.asch.sdk.util;

import java.util.Date;

public class DebugHelper {
    private static boolean enabled = false;

    public static boolean getEnabled() {
        return DebugHelper.enabled;
    }

    public static void setEnabled(boolean enabled) {
        DebugHelper.enabled = enabled;
    }

    public static void info(String message) {
        info(message, null);
    }

    public static void info(String message, String where) {
        if (!DebugHelper.enabled) return;

        String str = String.format("%s INFO %s", new Date(), (where + ", " + message));
        System.out.println(str);
    }

    public static void error(Throwable ex, String where) {
        if (!DebugHelper.enabled) return;

        String str = String.format("%s ERROR %s", new Date(), where + ", " + ex.getMessage());
        System.out.println(str);
        ex.printStackTrace();
    }

    public static void error(Throwable ex) {
        error(ex, null);
    }
}
