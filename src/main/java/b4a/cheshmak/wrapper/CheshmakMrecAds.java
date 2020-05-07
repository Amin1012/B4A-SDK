package b4a.cheshmak.wrapper;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.ViewWrapper;
import me.cheshmak.cheshmakplussdk.advertise.CheshmakMrecAd;
import me.cheshmak.cheshmakplussdk.advertise.MrecCallback;


@BA.ShortName(value = "CheshmakMrecAds")
@BA.ActivityObject
public class CheshmakMrecAds extends ViewWrapper<CheshmakMrecAd> {
    public CheshmakMrecAds() {
    }

    public void Initialize(final BA ba) {

        final CheshmakMrecAd ad = new CheshmakMrecAd(ba.activity);
        this.setObject(ad);
        final String eventName = "cheshmak_mrec_on";
        super.Initialize(ba, eventName);

        ad.setCallback(new MrecCallback() {

            @Override
            public void onAdLoaded() {
                ba.raiseEvent(CheshmakMrecAds.this.getObject(), eventName + "_loaded");
            }

            @Override
            public void onAdOpened() {
                ba.raiseEventFromDifferentThread (ad, null,0, eventName + "_opened" ,false,null);

            }

            @Override
            public void onAdFailedToLoad() {
                ba.raiseEvent(CheshmakMrecAds.this.getObject(), eventName + "_failed");
            }



            @Override
            public void onAdClosed() {
                ba.raiseEventFromDifferentThread (ad, null,0, eventName + "_closed" ,false,null);
            }


        });
    }

    public int getAdHeight() {
       return this.getHeight();
    }
}
