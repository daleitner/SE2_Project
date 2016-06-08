package com.malefiz.game;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Handler;
import android.widget.Toast;


import interfaces.ActionResolver;
import screens.GameScreen;


/**
 * Created by Dan on 01.06.2016.
 */
public class ActionResolverAndroid implements ActionResolver {
    Handler handler;
    Context context;
    AndroidLauncher androidLauncher;


    public ActionResolverAndroid(Context context, AndroidLauncher al) {
        handler = new Handler();
        this.context = context;
        this.androidLauncher = al;
    }

    @Override
    public void showToast(final CharSequence text) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void shiftGameScreen(GameScreen gs)
    {
        androidLauncher.setGameScreen(gs);
    }
}
