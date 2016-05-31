package com.malefiz.game;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import controllers.MyMalefiz;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new MyMalefiz(new ActionResolverAndroid(getApplicationContext())), config);
	}

	public void toast(final String t) {
		handler.post(new Runnable()
		{

			@Override
			public void run() {
				//System.out.println("toatsing in launcher run");
				Context context = getApplicationContext();
				int duration = Toast.LENGTH_SHORT;

				Toast toast = Toast.makeText(context, t, duration);
				toast.show();

			}

		});

	}
}
