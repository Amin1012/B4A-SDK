package b4a.cheshmak.wrapper;

import android.util.Log;
import anywheresoftware.b4a.BA;
import me.cheshmak.cheshmakplussdk.advertise.CheshmakRewardedAd;
import me.cheshmak.cheshmakplussdk.advertise.RewardedCallback;


@BA.ShortName("RewardedVideoAd")
@BA.ActivityObject
public class cheshmakRewardedAds {
    private CheshmakRewardedAd cheshmakRewardedAd;
    private String tag = "cheshmak_b4a";

    public cheshmakRewardedAds() {
    }

    public void Initialize(final BA ba) {
        final String eventName = "cheshmak_rewarded_on";
        cheshmakRewardedAd = new CheshmakRewardedAd(ba.activity, new RewardedCallback() {

            @Override
            public void onRewardedVideoAdLoaded() {
                ba.raiseEvent(this, eventName + "_loaded");
            }

            @Override
            public void onRewardedVideoAdOpened() {
                ba.raiseEventFromDifferentThread(cheshmakRewardedAd, null, 0, eventName + "_opened", false, null);
            }

            @Override
            public void onRewardedVideoAdFailedToLoad() {
                ba.raiseEvent(this, eventName + "_failed");
            }

            @Override
            public void onRewarded() {
                ba.raiseEventFromDifferentThread(cheshmakRewardedAd, null, 0, eventName + "_reward", false, null);
            }

            @Override
            public void onRewardedVideoAdClosed() {
                ba.raiseEventFromDifferentThread(cheshmakRewardedAd, null, 0, eventName + "_closed", false, null);

            }

        });


    }

    public Boolean isLoaded() {
        return cheshmakRewardedAd.isLoaded();
    }


    public void show(BA ba) {
        if (ba.activity != null) {
            ba.activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.d(tag, "From Ui Thread");
                        cheshmakRewardedAd.show();
                    } catch (Exception e) {
                        Log.d(tag, e.toString());
                    }

                }
            });
        } else {
            Log.d(tag, "Else not From Ui Thread");
            cheshmakRewardedAd.show();
        }
    }
}
