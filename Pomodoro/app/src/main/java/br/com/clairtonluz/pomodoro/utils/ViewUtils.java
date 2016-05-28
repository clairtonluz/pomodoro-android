package br.com.clairtonluz.pomodoro.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.TextView;

import br.com.clairtonluz.pomodoro.R;

/**
 * Created by clairton on 28/05/16.
 */
public abstract class ViewUtils {

    public static final int NOTIFICATION_ID = 1;

    public static boolean isFilled(TextView... values) {
        boolean valido = true;

        for (TextView textView : values) {
            String valor = textView.getText().toString();
            if (valor == null || valor.isEmpty()) {
                valido = false;
                textView.setError("Campo obrigatório");
            }
        }

        return valido;
    }


    public static void notificar(String title, String message, Context context, Class<?> activity, NotificationManager notificationManager) {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setSound(alarmSound)
                        .setContentText(message);
// Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, activity);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(activity);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);

// mId allows you to update the notification later on.
        notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
