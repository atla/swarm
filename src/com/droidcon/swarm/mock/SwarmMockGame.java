package com.droidcon.swarm.mock;

import java.util.LinkedList;
import java.util.List;

import com.droidcon.swarm.api.Game;
import com.droidcon.swarm.api.GameEventListener;
import com.droidcon.swarm.api.Player;
import com.droidcon.swarm.api.Team;
import com.droidcon.swarm.api.events.PlayerUpdateEvent;

public class SwarmMockGame implements Game {

	private List<Player> players = new LinkedList<Player>();
	private GameEventListener listener;
	private Player player;

	public SwarmMockGame() {

		player = createPlayer("atla", Team.GREEN, 100);

		players.add(createPlayer("thungsten", Team.GREEN, 100, 20));
		players.add(createPlayer("bob", Team.RED, 100, 30));
		players.add(createPlayer("joe", Team.NONE, 100, 50));
		players.add(createPlayer("emma", Team.BLUE, 100, 60));

	}

	private Player createPlayer(String string, Team green, int i) {
		Player p = new Player();

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

//		for (Player p : players) {

			PlayerUpdateEvent event = new PlayerUpdateEvent();
			event.player = player;
			this.listener.onEvent(event);
//		}

	}

	@Override
	public void unregisterListener(GameEventListener listener) {
		this.listener = null;
	}

	@Override
	public void sendUnits(Player target, int units) {
		// TODO: implement
	}

	@Override
	public List<Player> getPlayers() {
		return this.players;
	}

	@Override
	public Player getPlayer() {
		return this.player;
	}

}
