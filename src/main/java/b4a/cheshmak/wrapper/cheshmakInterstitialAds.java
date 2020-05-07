package b4a.cheshmak.wrapper;

import android.util.Log;
import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.BA;
import me.cheshmak.cheshmakplussdk.advertise.CheshmakInterstitialAd;
import me.cheshmak.cheshmakplussdk.advertise.InterstitialCallback;


@BA.ShortName(value = "cheshmakInterstitialAds")
@BA.ActivityObject
public class cheshmakInterstitialAds extends AbsObjectWrapper<CheshmakInterstitialAd> {
    private String tag = "cheshmak_b4a";
    private String eventName = "cheshmak_interstitial_on";

    public cheshmakInterstitialAds() {
    }

    public void Initialize(final BA ba) {
        CheshmakInterstitialAd cheshmakInterstitialAd = new CheshmakInterstitialAd(ba.activity, new InterstitialCallback() {
            @Override
            public void onAdLoaded() {
                ba.raiseEvent(this, eventName + "_loaded");
            }

            @Override
            public void onAdOpened() {
                ba.raiseEventFromDifferentThread(this, null, 0, eventName + "_opened", false, null);
            }

            @Override
            public void onAdFailedToLoad() {
                ba.raiseEventFromDifferentThread(this, null, 0, eventName + "_failed", false, null);
            }


            @Override
            public void onAdClosed() {
                ba.raiseEventFromDifferentThread(this, null, 0, eventName + "_closed", false, null);
            }


        });
        this.setObject(cheshmakInterstitialAd);



    }

    public Boolean isLoaded() {
        try {
            return this.getObject().isLoaded();
        } catch (Exception e) {
            Log.d(tag, e.toString());
            return false;
        }
    }

    public void show(BA baObject) {
        final CheshmakInterstitialAd cheshmakInterstitialAd = this.getObject();

        baObject.activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {

                    if (cheshmakInterstitialAd.isLoaded()) {
                        cheshmakInterstitialAd.show();
                    }
                } catch (Exception e) {
                    Log.d(tag, e.toString());
                }
            }
        });
    }

}
