package com.example.tp5;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyBroadcastReceiver extends BroadcastReceiver {

       @Override
    public void onReceive(Context context, Intent intent)
    {

        String whichAction = intent.getAction();

        switch (whichAction)
        {

            case "test":
                //MainActivity.addTime();
                return;

        }

    }


}