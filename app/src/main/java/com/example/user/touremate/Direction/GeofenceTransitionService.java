package com.example.user.touremate.Direction;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.example.user.touremate.R;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.ArrayList;
import java.util.List;


public class GeofenceTransitionService extends IntentService {


    public GeofenceTransitionService() {
        super("GeofenceTransitionService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        GeofencingEvent geofencingEvent= GeofencingEvent.fromIntent(intent);
        int transitionType=geofencingEvent.getGeofenceTransition();
        List<Geofence> trigeringGeofences=geofencingEvent.getTriggeringGeofences();

        ArrayList<String> geofencingIds=new ArrayList<>();
        for(Geofence g: trigeringGeofences){
            geofencingIds.add(g.getRequestId());
        }
        String transitionString="";


        switch (transitionType){
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                transitionString="Enterd : ";
                break;
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                transitionString="Exited : ";
                break;
        }
        String notificationString=transitionString+ TextUtils.join(", ",geofencingIds);

        NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle(notificationString);
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        builder.setAutoCancel(true);

        NotificationManager manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(500,builder.build());
    }

}
