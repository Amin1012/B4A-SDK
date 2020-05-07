package b4a.cheshmak.wrapper;


import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.os.Looper;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import me.cheshmak.android.sdk.core.Cheshmak;
import me.cheshmak.android.sdk.core.push.CheshmakFirebaseMessagingService;

public class CheshmakMessagingService extends FirebaseMessagingService {
    private CheshmakFirebaseMessagingService cheshmakService;
    private String tag = "cheshmak_b4a";
    private Boolean isPusheExist;


    public CheshmakMessagingService() {
        cheshmakService = new CheshmakFirebaseMessagingService();
        isPusheExist = isPusheExists();


    }


    public void onMessageReceived(final RemoteMessage remoteMessage) {
        try {
            Log.d(tag, "onMessageReceived: ");

            final boolean isCheshmakMessage = cheshmakService.isCheshmakMessage(remoteMessage);
            final String customData = remoteMessage.getData().get("customData");
            final String packageName = this.getPackageName();
            if (isCheshmakMessage) {
                try {
                    cheshmakService.onMessageReceived(remoteMessage);
                    if (customData != null && !("{}".equals(customData))) {
                        Intent service = new Intent(this.getApplicationContext(), Class.forName(packageName + ".cheshmakservice"));
                        service.setPackage(packageName);
                        service.putExtra("me.cheshmak.data", customData);
                        startService(service);
                    }

                } catch (Exception e) {
                    Log.d(tag, e.toString());
                }

            } else {


                final CheshmakMessagingService serviceContext = this;
                if (isPusheExist) {
                    com.pushpole.sdk.PushPole.getFcmHandler(serviceContext).onMessageReceived(remoteMessage);
                } else {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Cheshmak.with(serviceContext.getApplicationContext()); //inside looper
                                Thread.sleep(100);
                                if (customData != null && !("{}".equals(customData))) {
                                    Intent service = new Intent(serviceContext.getApplicationContext(), Class.forName(packageName + ".cheshmakservice"));
                                    service.setPackage(packageName);
                                    service.putExtra("me.cheshmak.data", customData);
                                    startService(service);
                                }

                                cheshmakService.onMessageReceived(remoteMessage);

                            } catch (Exception e) {
                                Log.d(tag, e.toString());
                            }
                        }
                    });
                }


            }//else


        } catch (Exception e) {
            Log.d(tag, "error ############ ");
            Log.d(tag, e.toString());
        }

    }


    @Override
    public void onNewToken(String token) {
        try {
            Log.d(tag, "onNewToken");
            super.onNewToken(token);
            cheshmakService.onNewToken(token);
            if (isPusheExist) {
                com.pushpole.sdk.PushPole.getFcmHandler(this).onNewToken(token);
            }
        } catch (Exception e) {
            Log.d(tag, e.toString());
        }

    }

    @Override
    public void onSendError(String error, Exception e) {
        try {
            super.onSendError(error, e);
            if (isPusheExist) {
                com.pushpole.sdk.PushPole.getFcmHandler(cheshmakService).onSendError(error, e);
            }
        } catch (Exception exp) {
            Log.d(tag, exp.toString());
        }
    }

    @Override
    public void onMessageSent(String message) {
        try {
            super.onMessageSent(message);
            if (isPusheExist) {
                com.pushpole.sdk.PushPole.getFcmHandler(this).onMessageSent(message);
            }
        } catch (Exception e) {
            Log.d(tag, e.toString());
        }
    }

    @Override
    public void onDeletedMessages() {
        try {
            super.onDeletedMessages();
            if (isPusheExist) {
                com.pushpole.sdk.PushPole.getFcmHandler(this).onDeletedMessages();
            }
        } catch (Exception e) {
            Log.d(tag, e.toString());
        }
    }

    private boolean isPusheExists() {
        try {
            Class.forName("com.pushpole.sdk.PushPole");
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}








