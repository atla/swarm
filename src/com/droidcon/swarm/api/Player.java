package com.droidcon.swarm.api;

public class Player {

	public final static int MAX_NAME_LENGTH = 12;

	public String name;
	public int distance;
	public int units;
	public Team team;

	public static Player create(String name, int distance) {

		Player player = new Player();
		player.name = name;
		player.units = 100;
		player.distance = distance;
		
		return player;

	}

}
