package com.droidcon.swarm;

import com.droidcon.swarm.api.Game;
import com.droidcon.swarm.mock.SwarmMockGame;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Swarm extends Activity {

	public static final Game GAME = new SwarmMockGame();

	private GameView gameView;

	private GameViewEventListener gameEventListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_swarm);

		this.gameView = (GameView) this.findViewById(R.id.game_view);

		
		

	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		if (gameView != null){
			this.gameEventListener = new GameViewEventListener(GAME, gameView);	
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.swarm, menu);
		return true;
	}

}
