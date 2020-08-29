package b4a.cheshmak.wrapper;

import android.app.Application;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.util.Log;
import me.cheshmak.android.sdk.core.Cheshmak;
import me.cheshmak.android.sdk.core.network.CheshmakCallback;


public class CheshmakApplication extends Application {
    private String tag = "cheshmak_b4a";

    @Override
    public void onCreate() {
        super.onCreate();
        if(Build.VERSION.SDK_INT < 21) return;
        Log.d(tag, "onCreate()");
        try {

            final CheshmakApplication context = this;
            final String packageName = context.getPackageName();
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(packageName, 128);


            try {
                Class.forName("me.cheshmak.cheshmakplussdk.core.CheshmakPlus");
                me.cheshmak.cheshmakplussdk.core.CheshmakPlus.with(context);
                Log.d(tag, "CheshmakPlus is running");
            } catch (Exception e) {
                Log.d(tag, "CheshmakPlus not included! ");
            }

            if (appInfo.metaData != null) {
                String appKey = appInfo.metaData.getString("AppKey");
                Cheshmak.with(this);
                Cheshmak.initTracker(appKey, new CheshmakCallback() {
                    @Override
                    public void onCheshmakIdReceived(String cheshmakID) {
                        Log.d("CheshmakID", "CheshmakID = " + cheshmakID);
                        Log.d(tag, "CheshmakID = " + cheshmakID);
                        if (cheshmakID != null) {

                            Intent service = null;
                            try {
                                service = new Intent(context, Class.forName(packageName + ".cheshmakservice"));
                                service.setPackage(packageName);
                                service.putExtra("me.cheshmak.CheshmakID", cheshmakID);
                                startService(service);
                            } catch (ClassNotFoundException e) {
                                Log.d(tag, "Error = " + e.toString());
                            }

                        }
                    }
                });


            }


        } catch (Exception e) {
            Log.d(tag, e.toString());

        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        Log.d(tag, "attachBaseContext()");
        super.attachBaseContext(base);
//        try {
//            Log.d(tag, "Build.VERSION.SDK_INT: " + Build.VERSION.SDK_INT);
//            if (Build.VERSION.SDK_INT < 21) {
//                Class.forName("androidx.multidex.MultiDex");
//                androidx.multidex.MultiDex.install(this);
//                Log.d(tag, "androidx.multidex.MultiDex.install finish");
//                return;
//            }
//        } catch (Exception e) {
//            Log.d(tag, "Error in androidx.multidex attachBaseContext()");
//            Log.d(tag, e.toString());
//
//        }
        //
        try {
            Log.d(tag, "Build.VERSION.SDK_INT: " + Build.VERSION.SDK_INT);
            if (Build.VERSION.SDK_INT < 21) {
                Class.forName("android.support.multidex.MultiDex");
                android.support.multidex.MultiDex.install(this);
                Log.d(tag, "android.support.multidex.MultiDex.install finish");
            }
        } catch (Exception e) {
            Log.d(tag, "Error in support.multidex attachBaseContext()");
            Log.d(tag, e.toString());

        }
    }
}
