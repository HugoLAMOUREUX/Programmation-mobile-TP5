package com.example.tp5;

import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {


    Button buttonId;
    Context context;
    int duration;
    CharSequence toastText;
    Toast toast;
    NotificationCompat.Builder builder;
    NotificationManagerCompat notificationManager;
    TextInputEditText input;
    Intent intent;
    PendingIntent pendingIntent;
    public int time;
    NotificationManager noti;
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=getApplicationContext();

        noti=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        input=findViewById(R.id.inputId);

        //creation du channel
        createNotificationChannel();

        // Create an explicit intent for an Activity in your app
        intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);



        buttonId=findViewById(R.id.buttonId);
        buttonId.setOnClickListener((view)->{
            toastText="Timer Launched !!!!";
            duration = Toast.LENGTH_SHORT;
            toast=Toast.makeText(context,toastText,duration);
            toast.show();

            //INTENT AJOUT 20 SECONDES
            //Intent addIntent = new Intent(this, MyBroadcastReceiver.class);
            //addIntent.setAction("test");
            //PendingIntent addPendingIntent =PendingIntent.getBroadcast(this, 0, addIntent, 0);





            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            time=parseInt(input.getText().toString())*1000;
                            builder=new NotificationCompat.Builder(context,CONSTANTES.CHANNEL_ID).setSmallIcon(R.drawable.play).setContentTitle(CONSTANTES.NotificationTitle).setPriority(NotificationCompat.PRIORITY_DEFAULT).setContentIntent(pendingIntent).setAutoCancel(true).setVibrate(null).setOnlyAlertOnce(true);
                            //builder=builder.addAction(R.drawable.add,"Add 20s",addPendingIntent);
                            notificationManager=NotificationManagerCompat.from(context);

                            builder.setProgress(time, 0, false);
                            id=(int)System.currentTimeMillis();
                            int incr;
                            int timenotif=time;
                            for (incr = 0; incr <= timenotif/1000; incr+=1) {
                                int idnotif=id;
                                builder.setProgress(timenotif/1000, incr, false).setContentText(incr+"");
                                noti.notify(idnotif, builder.build());
                                try {
                                    Thread.sleep(1*1000);
                                } catch (InterruptedException e) {
                                    Log.d("TAG", "sleep failure");
                                }
                            }
                            noti.cancel(id);
                        }
                    }
            ).start();
            Intent intentSecond=new Intent(context,ActivitySecond.class);
            startActivity(intentSecond);
        });
    }

    //CHANNEL DE NOTIFICATION
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CONSTANTES.CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

            channel.setVibrationPattern(new long[]{0});
            channel.enableVibration(false);
        }
    }

    public void addTime(){
        time=time+20;
    }
}

