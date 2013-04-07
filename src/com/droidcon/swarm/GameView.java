package com.droidcon.swarm;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.droidcon.swarm.api.Game;
import com.droidcon.swarm.api.Player;

public class GameView extends View {

	public class PlayerObject {

		public String name = "";
		public int x = 0;
		public int y = 0;
		public int angle = 0;
	}

	public class ShotObject {
		public int x = 0;
		public int y = 0;
		public int rotation = 0;
	}

	private Paint playerPaint;

	private List<PlayerObject> players = new LinkedList<PlayerObject>();
	private List<ShotObject> shots = new LinkedList<ShotObject>();

	private Random random;

	private Paint shotPaint;

	public void update(Game game) {

		List<PlayerObject> newPlayers = new LinkedList<PlayerObject>();
		List<ShotObject> newShots = new LinkedList<ShotObject>();

		newPlayers.add(convertToPlayerObject(game.getPlayer()));

		for (Player p : game.getPlayers()) {
			newPlayers.add(convertToPlayerObject(p));
		}

		this.players = newPlayers;
		this.shots = newShots;

	}

	private ShotObject newShot(int distance) {

		int angle = random.nextInt(360);
		ShotObject shot = new ShotObject();
		shot.x = (int) (0 + distance * Math.cos(angle));
		shot.y = (int) (0 + distance * Math.sin(angle));

		return shot;

	}

	private PlayerObject convertToPlayerObject(Player player) {

		PlayerObject po = new PlayerObject();
		po.x = player.distance;
		po.y = player.distance;

		return po;
	}

	public GameView(Context context) {
		super(context);

		init();
	}

	private void init() {
		playerPaint = new Paint();
		playerPaint.setColor(Color.parseColor("#99cc00"));
		playerPaint.setStrokeWidth(5);
		playerPaint.setStyle(Paint.Style.STROKE);
		playerPaint.setAntiAlias(true);
		
		
		shotPaint = new Paint();
		shotPaint.setColor(Color.parseColor("#99cc00"));
		shotPaint.setStrokeWidth(2);
		shotPaint.setStyle(Paint.Style.STROKE);
		shotPaint.setAntiAlias(true);

		random = new Random();

	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		canvas.translate(this.getWidth()/2, this.getHeight()/2);

		for (PlayerObject po : players) {
			renderPlayer(po, canvas);
		}

		for (ShotObject so : shots) {
			renderShot(so, canvas);
		}

	}

	private void renderShot(ShotObject so, Canvas c) {

		Path path = new Path();
		
		path.moveTo(so.x - 10, so.y - 10);
		path.lineTo(so.x, so.y + 30);
		
		path.moveTo(so.x, so.y + 30);
		path.lineTo(so.x + 10, so.y - 10);
		
		path.moveTo(so.x - 10, so.y - 10);
		path.lineTo(so.x + 10, so.y - 10);

		path.close();

		c.drawPath(path, playerPaint);
	}

	private void renderPlayer(PlayerObject po, Canvas c) {
		c.drawCircle(po.x, po.y, 40, playerPaint);
	}
}
