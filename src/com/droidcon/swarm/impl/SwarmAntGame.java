package com.droidcon.swarm.impl;

import java.util.List;
import java.util.Random;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.droidcon.swarm.api.Game;
import com.droidcon.swarm.api.GameEventListener;
import com.droidcon.swarm.api.Player;
import com.droidcon.swarm.api.Team;
import com.dsi.ant.AntService;
import com.dsi.ant.channel.AntChannel;
import com.dsi.ant.channel.AntChannelProvider;
import com.dsi.ant.channel.Capabilities;
import com.dsi.ant.channel.ChannelNotAvailableException;
import com.dsi.ant.channel.IAntChannelMessageHandler;
import com.dsi.ant.channel.PredefinedNetwork;
import com.dsi.ant.message.ChannelId;
import com.dsi.ant.message.ChannelType;
import com.dsi.ant.message.ExtendedAssignment;
import com.dsi.ant.message.LibConfig;
import com.dsi.ant.message.LowPrioritySearchTimeout;
import com.dsi.ant.message.fromant.AntMessageFromAnt;

public class SwarmAntGame implements Game, IAntChannelMessageHandler {
	
	private AntService antService;
	private AntChannelProvider channelProvider;
	private AntChannel beaconChannel;
	private AntChannel listenChannel;
	private Context context;
	private Player me;
	private Random rnd = new Random();
	private int channelId;
	
	public SwarmAntGame(Context ctx, String playerName) {
		this.context = ctx;
		this.channelId = playerName.charAt(0) + 127;
		me = new Player();
		me.name = playerName;
		me.units = 100;
		switch (rnd.nextInt(4)) {
		case 0:
			me.team = Team.BLUE;
			break;
		case 1:
			me.team = Team.GREEN;
			break;
		case 2:
			me.team = Team.RED;
			break;
		default:
			me.team = Team.NONE;
			me.units = 0;
		}
	}
	
	private ServiceConnection antConnection = new ServiceConnection() {
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			antService = new AntService(service);
			try {
                channelProvider = antService.getChannelProvider();
                beaconChannel = channelProvider.acquireChannel(context, PredefinedNetwork.PUBLIC, new Capabilities(false, true, true, false));
                beaconChannel.assign(ChannelType.BIDIRECTIONAL_MASTER, new ExtendedAssignment(true, false));
                beaconChannel.setAdapterWideLibConfig(new LibConfig(true, true, false));
                beaconChannel.setChannelId(new ChannelId(channelId, false, 42, 1));
                beaconChannel.open();
                listenChannel = channelProvider.acquireChannel(context, PredefinedNetwork.PUBLIC, new Capabilities(false, true, true, false));
                listenChannel.assign(ChannelType.BIDIRECTIONAL_SLAVE);
                listenChannel.setChannelId(new ChannelId(0, false, 42, 1));
                listenChannel.setMessageHandler(SwarmAntGame.this);
                listenChannel.setAdapterWideLibConfig(new LibConfig(true, true, false));
                listenChannel.open();
            } catch (Exception e) {
                e.printStackTrace();
            }
		}
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			antService = null;
			channelProvider = null;
		}
		
	};

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
		return this.me;
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleMessage(AntMessageFromAnt arg0) {
		Log.i("SwarmAntGame", arg0.getMessageIdString() + ": " + arg0.getMessageContentString());
	}

	@Override
	public void onChannelDeath() {
		try {
			listenChannel.close();
			listenChannel.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
