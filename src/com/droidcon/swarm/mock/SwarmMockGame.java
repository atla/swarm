package com.droidcon.swarm.mock;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;

import com.droidcon.swarm.api.Game;
import com.droidcon.swarm.api.GameEventListener;
import com.droidcon.swarm.api.Player;
import com.droidcon.swarm.api.Team;
import com.droidcon.swarm.api.events.PlayerAddedEvent;
import com.droidcon.swarm.api.events.PlayerRemovedEvent;
import com.droidcon.swarm.api.events.PlayerUpdateEvent;

public class SwarmMockGame implements Game {

	public class DummyEventTask extends TimerTask {

		private SwarmMockGame mock;
		private Random random;

		public DummyEventTask(SwarmMockGame swarmMockGame) {
			this.mock = swarmMockGame;
			this.random = new Random();
		}

		@Override
		public void run() {

			int eventType = random.nextInt(1000);

			if (eventType < 50) {
				createShotEvent();
			} else if (eventType < 150) {
				createRemovedEvent();
			} else if (eventType > 800) {
				if (mock.getPlayers().size() < 6) {
					createAddedEvent();
				}

			}

			for (Player p : mock.getPlayers()) {

				PlayerUpdateEvent event = new PlayerUpdateEvent();

				p.distance = p.distance - 10 + random.nextInt(20);
				p.distance = p.distance % 150;

				event.player = p;

				mock.sendEvent(event);

			}
		}

		private void createAddedEvent() {

			Player p = new Player();
			p.distance = 50 + random.nextInt(100);
			p.name = "Player" + random.nextInt(5);
			p.units = 100;

			mock.addPlayer(p);

		}

		private void createRemovedEvent() {

			Player p = getRandomPlayer(mock.getPlayers());

			if (p != null) {
				PlayerRemovedEvent event = new PlayerRemovedEvent();
				event.player = p;

				mock.sendEvent(event);
			}

		}

		private Player getRandomPlayer(List<Player> players) {

			if (players.size() == 0)
				return null;

			int r = random.nextInt(players.size());

			return players.get(r);

		}

		private void createShotEvent() {

		}

	}

	private List<Player> players = new LinkedList<Player>();
	private GameEventListener listener;
	private Player player;
	private Timer timer;
	private Activity ctx;

	public SwarmMockGame(Activity ctx) {

		player = createPlayer("atla", Team.GREEN, 100);
		this.ctx = ctx;
	}

	public void addPlayer(Player p) {

		this.players.add(p);

		PlayerAddedEvent event = new PlayerAddedEvent();
		event.player = p;
		sendEvent(event);

	}

	private Player createPlayer(String string, Team green, int i) {
		Player p = new Player();
		p.name = string;
		p.team = green;
		p.distance = i;
		return p;
	}

	private Player createPlayer(String string, Team green, int i, int distance) {
		Player p = new Player();
		p.distance = distance;

		return p;
	}

	@Override
	public void registerListener(GameEventListener listener) {
		this.listener = listener;
	}

	public void sendEvent(final Object event) {
		if (this.listener != null) {
			ctx.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					listener.onEvent(event);
				}

			});

		}

	}

	@Override
	public void unregisterListener(GameEventListener listener) {
		this.listener = null;
	}

	@Override
	public void sendUnits(Player target, int units) {

		target.units -= units;

		PlayerUpdateEvent event = new PlayerUpdateEvent();
		event.player = target;

		sendEvent(event);

	}

	@Override
	public List<Player> getPlayers() {
		return this.players;
	}

	@Override
	public Player getPlayer() {
		return this.player;
	}

	@Override
	public void resume() {
		timer = new Timer();
		timer.scheduleAtFixedRate(new DummyEventTask(this), 100, 1000);
	}

	@Override
	public void shutdown() {
		this.timer.cancel();
	}

}
