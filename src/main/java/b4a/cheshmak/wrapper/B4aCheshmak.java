package b4a.cheshmak.wrapper;

import android.util.Log;
import anywheresoftware.b4a.BA;
import me.cheshmak.android.sdk.core.Cheshmak;


@BA.ShortName(value = "Cheshmak")
public class B4aCheshmak {

    private String tag = "cheshmak_b4a";

    public String getCheshmakID(final BA ba) {
        try {
            return Cheshmak.getCheshmakID(ba.context);
        } catch (Exception e) {
            Log.d(tag, e.toString());
            return "";
        }

    }



}
