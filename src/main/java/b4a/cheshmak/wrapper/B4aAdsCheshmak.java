package b4a.cheshmak.wrapper;


import android.util.Log;
import anywheresoftware.b4a.BA;
import me.cheshmak.cheshmakplussdk.core.CheshmakPlus;

@BA.ShortName(value = "CheshmakAds")
public class B4aAdsCheshmak {


    private String tag = "cheshmak_b4a";

    public void setTestMode() {
        Log.d(tag, "setTestMode(): " );

        CheshmakPlus.setTestMode(true);
    }

}
