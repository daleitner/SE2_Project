package com.malefiz.game;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import controllers.GameController;
import controllers.MyMalefiz;
import interfaces.ActionResolver;
import screens.GameScreen;

public class AndroidLauncher extends AndroidApplication {
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private ShakeDetector mShakeDetector;
	private GameScreen gameScreen;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		ActionResolver ar = new ActionResolverAndroid(getApplicationContext(), this);
		initialize(new MyMalefiz(ar), config);

// ShakeDetector initialization
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mAccelerometer = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mShakeDetector = new ShakeDetector();
		mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

			@Override
			public void onShake(int count) {
				/*
				 * The following method, "handleShakeEvent(count):" is a stub //
				 * method you would use to setup whatever you want done once the
				 * device has been shook.
				 */
				if(gameScreen != null)
				{
					gameScreen.forceDice();
				}
			}
		});
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

	@Override
	public void onResume() {
		super.onResume();
		// Add the following line to register the Session Manager Listener onResume
		mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
	}

	@Override
	public void onPause() {
		// Add the following line to unregister the Sensor Manager onPause
		mSensorManager.unregisterListener(mShakeDetector);
		super.onPause();
	}

	public void setGameScreen(GameScreen gs)
	{
		gameScreen = gs;
	}
}
