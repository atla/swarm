package com.droidcon.swarm;

import java.util.List;

import com.droidcon.swarm.api.Game;
import com.droidcon.swarm.api.GameEventListener;
import com.droidcon.swarm.api.Player;
import com.droidcon.swarm.mock.SwarmMockGame;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Swarm extends Activity {

	public static Game GAME = new Game() {

		@Override
		public void registerListener(GameEventListener listener) {
		}

		@Override
		public void unregisterListener(GameEventListener listener) {
		}

		@Override
		public void sendUnits(Player target, int units) {
		}

		@Override
		public List<Player> getPlayers() {
			return null;
		}

		@Override
		public Player getPlayer() {
			return null;
		}

		@Override
		public void resume() {
			// TODO Auto-generated method stub

		}

		@Override
		public void shutdown() {
			// TODO Auto-generated method stub

		}

	};

	private GameView gameView;

	private GameViewEventListener gameEventListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_swarm);

		this.gameView = (GameView) this.findViewById(R.id.game_view);

		GAME = new SwarmMockGame(this);

	}

	@Override
	protected void onResume() {
		super.onResume();

		if (gameView != null) {
			this.gameEventListener = new GameViewEventListener(GAME, gameView);
		}

		GAME.resume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		GAME.shutdown();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.swarm, menu);
		return true;
	}

}
