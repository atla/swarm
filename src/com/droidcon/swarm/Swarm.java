package com.droidcon.swarm;

import com.droidcon.swarm.api.Game;
import com.droidcon.swarm.mock.SwarmMockGame;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Swarm extends Activity {

	
	public static final Game GAME = new SwarmMockGame();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_swarm);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.swarm, menu);
		return true;
	}

}
