package com.droidcon.swarm;

import android.util.Log;

import com.droidcon.swarm.api.Game;
import com.droidcon.swarm.api.GameEventListener;
import com.droidcon.swarm.api.events.PlayerAddedEvent;
import com.droidcon.swarm.api.events.PlayerRemovedEvent;
import com.droidcon.swarm.api.events.PlayerUpdateEvent;

public class GameViewEventListener implements GameEventListener {

	private static final String TAG = GameViewEventListener.class.getSimpleName();
	private GameView gameView;
	private Game game;

	public GameViewEventListener(Game game, GameView gameView) {
		this.game = game;
		this.gameView = gameView;

		this.game.registerListener(this);
	}

	@Override
	public void onEvent(Object event) {

		if (event instanceof PlayerUpdateEvent) {
			onPlayerUpdateEvent((PlayerUpdateEvent) event);
		} else if (event instanceof PlayerRemovedEvent) {
			onPlayerRemovedEvent((PlayerRemovedEvent) event);
		} else if (event instanceof PlayerAddedEvent) {
			onPlayerAddedEvent((PlayerAddedEvent) event);
		}

	}

	private void onPlayerAddedEvent(PlayerAddedEvent event) {
		Log.d(TAG, "player added event received: " + event.player.name);

		gameView.update(game);

	}

	private void onPlayerRemovedEvent(PlayerRemovedEvent event) {

		Log.d(TAG, "player removed event received: " + event.player.name);

		gameView.update(game);
	}

	private void onPlayerUpdateEvent(PlayerUpdateEvent event) {
		Log.d(TAG, "player update event received: " + event.player.name);
		gameView.update(game);
	}

}
