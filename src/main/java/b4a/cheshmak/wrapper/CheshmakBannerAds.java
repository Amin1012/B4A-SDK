package b4a.cheshmak.wrapper;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.ViewWrapper;
import me.cheshmak.cheshmakplussdk.advertise.BannerCallback;
import me.cheshmak.cheshmakplussdk.advertise.CheshmakBannerAd;



@BA.ShortName(value = "CheshmakBannerAds")
@BA.ActivityObject
public class CheshmakBannerAds extends ViewWrapper<CheshmakBannerAd> {
    public CheshmakBannerAds() {
    }

    public void Initialize(final BA ba) {
        final CheshmakBannerAd ad = new CheshmakBannerAd(ba.activity);
        this.setObject(ad);
        final String eventName = "cheshmak_banner_on";
        super.Initialize(ba, eventName);
        ad.setCallback(new BannerCallback() {

            @Override
            public void onAdLoaded() {
                ba.raiseEvent(CheshmakBannerAds.this.getObject(), eventName + "_loaded");
            }

            @Override
            public void onAdOpened() {
                ba.raiseEventFromDifferentThread (ad, null,0, eventName + "_opened" ,false,null);

            }

            @Override
            public void onAdFailedToLoad() {
                ba.raiseEvent(CheshmakBannerAds.this.getObject(), eventName + "_failed");
            }


            @Override
            public void onAdClosed() {
                ba.raiseEventFromDifferentThread (ad, null,0, eventName + "_closed" ,false,null);
            }

        });
    }
}
