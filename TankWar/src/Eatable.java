import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;


public class Eatable
{
	int x, y, w = 20, h = 20;

	TankWarClient tc;
	private int step  = r.nextInt(12) + 3;
	
	private boolean live = true;
	
	public boolean isLive()
	{
		return live;
	}

	public void setLive(boolean live)
	{
		this.live = live;
	}

	private boolean bL = false, bU = false, bR = false, bD = false;
	enum Direction {L, LU, U, RU, R, RD, D, LD, STOP};
	public static final int X_SPEED = 5;
	public static final int Y_SPEED = 5;
	
	private Direction dir = Direction.STOP;
	
	private static Random r = new Random();
	
	public Eatable(int x, int y, Direction dir, boolean live, TankWarClient tc)
	{
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.live = true;
		this.tc = tc;
	}
	
	public void draw(Graphics g)
	{
		if(!live) return;
		Color c = g.getColor();
		g.setColor(Color.magenta);
		g.fillRect(x, y, w, h);
		g.setColor(c);	
		move();
	}
	
	public Rectangle getRect()
	{
		return new Rectangle(x, y, w, h);
	}

	void move()
	{
		switch(dir)
		{
		case L:
			x -= X_SPEED;
			break;
		case LU:
			x -= X_SPEED;
			y -= Y_SPEED;
			break;
		case R:
			x += X_SPEED;
			break;
		case U:
			y -= Y_SPEED;
			break;
		case RU:
			x += X_SPEED;
			y -= Y_SPEED;
			break;
		case RD:
			x += X_SPEED;
			y += Y_SPEED;
			break;
		case D:
			y += Y_SPEED;
			break;
		case LD:
			x -= X_SPEED;
			y += Y_SPEED;
			break;
		case STOP:
			break;
		}

		if(x < 0) x = 0;
		if(y < 30) y = 30;
		if(x + Tank.WIDTH > TankWarClient.GAME_WIDTH) x = TankWarClient.GAME_WIDTH - Tank.WIDTH;
		if(y + Tank.HEIGHT > TankWarClient.GAME_HEIGHT) y = TankWarClient.GAME_HEIGHT - Tank.HEIGHT;

		Direction[] dirs = Direction.values();
		if(step == 0)
		{
			step = r.nextInt(12) + 3;
			int rn = r.nextInt(dirs.length);
			dir = dirs[rn];
		}
		step--;
	}
	
	void locateDirection()
	{
		if(bL && !bU && !bR && !bD) dir = Direction.L;
		else if(bL && bU && !bR && !bD) dir = Direction.LU;
		else if(!bL && bU && !bR && !bD) dir = Direction.U;
		else if(!bL && bU && bR && !bD) dir = Direction.RU;
		else if(!bL && !bU && bR && !bD) dir = Direction.R;
		else if(!bL && !bU && bR && bD) dir = Direction.RD;
		else if(!bL && !bU && !bR && bD) dir = Direction.D;
		else if(bL && !bU && !bR && bD) dir = Direction.LD;
		else if(!bL && !bU && !bR && !bD) dir = Direction.STOP;
	}
	

}












