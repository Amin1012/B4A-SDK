package b4a.cheshmak.wrapper;

import android.util.Log;
import anywheresoftware.b4a.BA;
import me.cheshmak.android.sdk.core.config.CheshmakConfig;

@BA.ShortName(value = "RemoteConfig")
public class RemoteConfig {
    private static String tag = "cheshmak_b4a_config";

    static public Boolean getBoolean(String key, Boolean defaultValue) {
        try {
            return CheshmakConfig.getBoolean(key, defaultValue);
        } catch (Exception e) {
            Log.d(tag, e.toString());
            return defaultValue;
        }

    }

    static public int getInt(String key, int defaultValue) {
        try {
            return CheshmakConfig.getInt(key, defaultValue);
        } catch (Exception e) {
            Log.d(tag, e.toString());
            return defaultValue;
        }

    }

    static public long getLong(String key, long defaultValue) {
        try {
            return CheshmakConfig.getLong(key, defaultValue);
        } catch (Exception e) {
            Log.d(tag, e.toString());
            return defaultValue;
        }

    }

    static public String getString(String key, String defaultValue) {
        try {
            return CheshmakConfig.getString(key, defaultValue);
        } catch (Exception e) {
            Log.d(tag, e.toString());
            return defaultValue;
        }

    }
    static public Double getDouble(String key, Double defaultValue) {
        try {
            return CheshmakConfig.getDouble(key, defaultValue);
        } catch (Exception e) {
            Log.d(tag, e.toString());
            return defaultValue;
        }

    }




}
