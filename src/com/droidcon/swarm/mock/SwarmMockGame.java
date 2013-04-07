package com.droidcon.swarm.mock;

import java.util.LinkedList;
import java.util.List;

import com.droidcon.swarm.api.Game;
import com.droidcon.swarm.api.GameEventListener;
import com.droidcon.swarm.api.Player;
import com.droidcon.swarm.api.Team;

public class SwarmMockGame implements Game {

	private List<Player> players = new LinkedList<Player>();
	private GameEventListener listener;
	private Player player;

	public SwarmMockGame() {

		
		player = createPlayer ("atla", Team.GREEN, 100);
		
		players.add(createPlayer("thungsten", Team.GREEN, 100));
		players.add(createPlayer("bob", Team.RED, 100));
		players.add(createPlayer("joe", Team.NONE, 100));
		players.add(createPlayer("emma", Team.BLUE, 100));

	}

	private Player createPlayer(String string, Team green, int i) {
		Player p = new Player();

		return p;
	}

	@Override
	public void registerListener(GameEventListener listener) {
		this.listener = listener;
	}

	@Override
	public void unregisterListener(GameEventListener listener) {
		this.listener = null;
	}

	@Override
	public void sendUnits(Player target, int units) {
		//TODO: implement
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
