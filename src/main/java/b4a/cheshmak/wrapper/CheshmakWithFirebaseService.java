package b4a.cheshmak.wrapper;


import android.content.Intent;
import android.util.Log;
import anywheresoftware.b4a.objects.FirebaseNotificationsService;
import com.google.firebase.messaging.RemoteMessage;
import me.cheshmak.android.sdk.core.push.CheshmakFirebaseMessagingService;


public class CheshmakWithFirebaseService extends FirebaseNotificationsService {
    private CheshmakFirebaseMessagingService cheshmakService ;
    private String tag = "cheshmak_b4a_firebase";
    private Boolean isPusheExist ;
    private Boolean isB4aFirebaseExists = false;


    public CheshmakWithFirebaseService() {
        cheshmakService = new CheshmakFirebaseMessagingService();
        isPusheExist = isPusheExists();


    }


    public void onMessageReceived(final RemoteMessage remoteMessage) {
        try {
            Log.d(tag, "onMessageReceived: ");
            if(!isB4aFirebaseExists){
                isB4aFirebaseExists = isFirebaseExists();
            }
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
            } else if (isPusheExist && (com.pushpole.sdk.PushPole.getFcmHandler(this).onMessageReceived(remoteMessage))) {

            } else {
                if (isB4aFirebaseExists) {
                    Log.d(tag,  "isB4aFirebaseExists ############## ");
                    super.onMessageReceived(remoteMessage);

                }else{
                    Log.e(tag,  "Plz add firebase library ");
                }
            }
        } catch (Exception e) {
            Log.d(tag,  "error ############ ");
            Log.d(tag,  e.toString());
        }

    }

    @Override
    public void onNewToken(String token) {
        try {
            Log.d(tag,  "onNewToken");
            super.onNewToken(token);
            cheshmakService.onNewToken(token);
            if (isPusheExist) {
                com.pushpole.sdk.PushPole.getFcmHandler(this).onNewToken(token);
            }
        } catch (Exception e) {
            Log.d(tag,  e.toString());
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
            Log.d(tag,  exp.toString());
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
            Log.d(tag,  e.toString());
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
            Log.d(tag,  e.toString());
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
    private boolean isFirebaseExists() {
        try {
            Class.forName(this.getPackageName() + ".firebasemessaging");
            Class.forName(this.getPackageName() + ".firebasemessaging$firebasemessaging_BR");
            return true;
        } catch (Exception e) {
            Log.d(tag,  e.toString());
            return false;
        }
    }


}



