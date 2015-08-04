package com.example.patrimoniopessoal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class Splash extends Activity implements Runnable{
	
	final private int TEMPO_ESPERA = 500;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.splash);

		Handler handler = new Handler();
		handler.postDelayed(this, TEMPO_ESPERA);
	}

	public void run(){
		startActivity(new Intent(this, Login.class));
		finish();
	}

}
