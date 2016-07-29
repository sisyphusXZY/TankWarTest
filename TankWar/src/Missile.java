import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Missile
{
	public static final int X_SPEED = 10;
	public static final int Y_SPEED = 10;

	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	
	int x, y;
	Tank.Direction dir;

	private TankWarClient tc;
	
	private boolean Live = true;
	
	public boolean isLive()
	{
		return Live;
	}

	public Missile(int x, int y, Tank.Direction dir)
	{ 
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	public Missile(int x, int y, Tank.Direction dir, TankWarClient tc)
	{
		this(x, y, dir);
		this.tc = tc;
	}
	
	public void draw(Graphics g)
	{
		if(!Live)
		{
			tc.missiles.remove(this);
			return ;
		}
		Color c = g.getColor();
		g.setColor(Color.BLACK);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		
		move();
	}

	private void move()
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
		}
		
		if(x < 0 || y < 0 || x > TankWarClient.GAME_WIDTH || y > TankWarClient.GAME_HEIGHT)
		{
			Live = false;
		}
	}
	
	public Rectangle getRect()
	{
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	
	public boolean hitTank(Tank t)
	{
		if(this.getRect().intersects(t.getRect()) && t.isLive())
		{
			t.setLive(false);
			this.Live = false;
			return true;
			
		}
		return false;
	}
}












