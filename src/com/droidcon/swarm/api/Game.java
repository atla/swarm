package com.droidcon.swarm.api;

import java.util.List;

public interface Game {

	public void registerListener(GameEventListener listener);

	public void unregisterListener(GameEventListener listener);

	public void sendUnits(Player target, int units);
	
	public List<Player> getPlayers();
	
	public Player getPlayer();

}
