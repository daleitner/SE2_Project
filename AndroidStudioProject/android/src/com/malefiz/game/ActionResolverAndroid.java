package com.malefiz.game;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;


import interfaces.ActionResolver;

/**
 * Created by Dan on 01.06.2016.
 */
public class ActionResolverAndroid implements ActionResolver {
    Handler handler;
    Context context;

    public ActionResolverAndroid(Context context) {
        handler = new Handler();
        this.context = context;
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
}
