import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.sql.Time;
import java.util.*;

public class Tank
{
	public static final int X_SPEED = 5;
	public static final int Y_SPEED = 5;
	public static final int WIDTH = 30;
	public static final int HEIGHT = 30;
	private int x ,y;
	

	private static Random r = new Random();
	
	private boolean good;
	private boolean boss;
	public boolean isBoss()
	{
		return boss;
	}

	private boolean live = true;
	
	private BloodBar bb = new BloodBar();
	private BloodBar bossbb = new BloodBar();
	
	private int life = 100;
	
	public void setTank(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	public int getLife()
	{
		return life;
	}

	public void setLife(int life)
	{
		this.life = life;
	}

	public boolean isLive()
	{
		return live;
	}

	public void setLive(boolean live)
	{
		this.live = live;
	}

	TankWarClient tc;
	
	private boolean bL = false, bU = false, bR = false, bD = false;
	enum Direction {L, LU, U, RU, R, RD, D, LD, STOP};
	
	private Direction dir = Direction.STOP; 
	private Direction ptDir = Direction.R;
	
	private int step  = r.nextInt(12) + 3;
	
	public Tank(int x, int y, boolean good)
	{
		this.x = x;
		this.y = y;
		this.good = good;
	}
	
	public Tank(int x, int y, boolean good, boolean boss, Direction dir, TankWarClient tc)
	{
		this(x, y, good);
		this.boss = boss;
		this.dir = dir;
		this.tc = tc;
	}
	
	public void draw(Graphics g)
	{
		
		if(!live) 
		{
			if( ! boss)
			{
				if(!good)	
				{
					tc.tanks.remove(this);
					tc.count+=10;
				}
				else 
				{
					Color c = g.getColor();
					Font f1 = new Font("¿¬Ìå", Font.BOLD, 110);
					Font f2 = new Font("¿¬Ìå", Font.BOLD, 50);
					g.setFont(f1);
					g.setColor(Color.red);
					g.drawString("GAME OVER", 130, 320);
					g.setFont(f2);
					g.drawString("Ha~Ha~Ha~(~£þ¨Œ£þ)~", 140, 370);
					g.setColor(c);
				}
				return;
			}
			else return;
		}

		Color c = g.getColor();
	 	if(good) 
	 	{
	 		g.setColor(Color.BLUE);
	 		bb.draw(g);
	 	}
	 	else if(boss)
	 	{
	 		g.setColor(Color.black);
	 		bossbb.draw(g);
	 	}
	 	else g.setColor(Color.YELLOW);
	 	g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
	
		
		
		switch(ptDir)
		{
		case L:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x, y + Tank.HEIGHT/2);
			break;
		case LU:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x, y);
			break;
		case U:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH/2, y);
			break;
		case RU:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH, y);
			break;
		case R:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH, y + Tank.HEIGHT/2);
			break;
		case RD:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH, y + Tank.HEIGHT);
			break;
		case D:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH/2, y + Tank.HEIGHT);
			break;
		case LD:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x, y + Tank.HEIGHT);
			break;
		default:
			break;
		}
	
		
		move();
	}
	
	public boolean isGood()
	{
		return good;
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
		if(this.dir != Direction.STOP)
		{
			this.ptDir = this.dir;
		}
		
		if(x < 0) x = 0;
		if(y < 30) y = 30;
		if(x + Tank.WIDTH > TankWarClient.GAME_WIDTH) x = TankWarClient.GAME_WIDTH - Tank.WIDTH;
		if(y + Tank.HEIGHT > TankWarClient.GAME_HEIGHT) y = TankWarClient.GAME_HEIGHT - Tank.HEIGHT;
	
		if(!good || boss)
		{
			Direction[] dirs = Direction.values();
			if(step == 0)
			{
				step = r.nextInt(12) + 3;
				int rn = r.nextInt(dirs.length);
				dir = dirs[rn];
			}
			step--;
			if(r.nextInt(33) > 30 && !boss) this.fire();
			else if(r.nextInt(32) > 30 && boss) this.superFire();
		}
		
	}
	
	void move2()
	{
		switch(dir)
		{
		case L:
			x -= 3*X_SPEED;
			break;
		case R:
			x += 3*X_SPEED;
			break;
		case U:
			y -= 3*Y_SPEED;
			break;
		case D:
			y += 3*Y_SPEED;
			break;
		}
		if(this.dir != Direction.STOP)
		{
			this.ptDir = this.dir;
		}
		
		if(x < 0) x = 0;
		if(y < 30) y = 30;
		if(x + Tank.WIDTH > TankWarClient.GAME_WIDTH) x = TankWarClient.GAME_WIDTH - Tank.WIDTH;
		if(y + Tank.HEIGHT > TankWarClient.GAME_HEIGHT) y = TankWarClient.GAME_HEIGHT - Tank.HEIGHT;
	
		if(!good)
		{
			Direction[] dirs = Direction.values();
			if(step == 0)
			{
				step = r.nextInt(12) + 3;
				int rn = r.nextInt(dirs.length);
				dir = dirs[rn];
			}
			step--;
			if(r.nextInt(33) > 30) this.fire();            
		}
		
	}
	
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		switch(key)
		{
		case KeyEvent.VK_RIGHT:
			bR = true;
			break;
		case KeyEvent.VK_LEFT:
			bL = true;
			break;
		case KeyEvent.VK_UP:
			bU = true;
			break;
		case KeyEvent.VK_DOWN:
			bD = true;
			break;
		}
		locateDirection();
	}
	
	public Missile fire()
	{
		if(!live) return null;
		int x = this.x + Tank.WIDTH/2 - Missile.WIDTH/2;
		int y = this.y + Tank.HEIGHT/2 - Missile.HEIGHT/2;
		Missile m = new Missile(x, y, good, ptDir, this.tc);
		tc.missiles.add(m);
		return m;
		
	}
	
	public Missile fire(Direction dir)
	{
		if(!live) return null;
		int x = this.x + Tank.WIDTH/2 - Missile.WIDTH/2;
		int y = this.y + Tank.HEIGHT/2 - Missile.HEIGHT/2;
		Missile m = new Missile(x, y, good, dir, this.tc);
		tc.missiles.add(m);
		return m;
		
	}
	
	public void superFire()
	{
		Direction[] dirs  = Direction.values();
		for(int i=0; i<8; i++)
		{
			fire(dirs[i]);
		}
	}

	public void superFire2()
	{
		for(int i=0; i<tc.tanks.size(); i++)
		{
			tc.tanks.get(i).setLive(false);
			Explode e1 = new Explode(tc.tanks.get(i).x, tc.tanks.get(i).y, tc);
			tc.explodes.add(e1);
			try
			{
				Thread.sleep(20);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			
			tc.missiles.get(i).setLive(false);
			Explode e2 = new Explode(tc.tanks.get(i).x, tc.tanks.get(i).y, tc);
			tc.explodes.add(e2);
		}
	}
	
	public void keyReleased(KeyEvent e)
	{
		int key = e.getKeyCode();
		switch(key)
		{
		case KeyEvent.VK_F2:
			tc.myTank.live = true;
			tc.myTank.life = 100;
			tc.stage = 1;
			tc.count = 0;
			tc.missileNum = 0;
			tc.missileNum2 = 0;
			tc.myTank.x = 50;
			tc.myTank.y = 50;
			break;
		case KeyEvent.VK_B:
			if(tc.missileNum2 >= 1) 
			{
				tc.missileNum2 --;
				superFire();
			}
			break;	
			//²âÊÔÓÃ
		case KeyEvent.VK_M:
			
			superFire2();
			break;
		case KeyEvent.VK_N:
			
			superFire();
			break;
		case KeyEvent.VK_V:
			if(tc.missileNum >= 1) 
			{
				tc.missileNum --;
				superFire2();
			}
			break;
		case KeyEvent.VK_SPACE:
			fire();
			break;
		case KeyEvent.VK_RIGHT:
			bR = false;
			break;
		case KeyEvent.VK_LEFT:
			bL = false;
			break;
		case KeyEvent.VK_UP:
			bU = false;
			break;
		case KeyEvent.VK_DOWN:
			bD = false;
			break;
		}
		locateDirection();
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

	public Rectangle getRect()
	{
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}

	public boolean collidesWithWall(Wall w)
	{
		if(this.live && this.getRect().intersects(w.getRect()))
		{
			if(this.isGood())
			{
				this.setLife(this.getLife()-20);
				if(this.getLife() <= 0) this.setLive(false);
			}
			if(this.dir == Direction.L) 
			{
				dir = Direction.R;
				move2();
				step = 0;
			}
			else if(this.dir == Direction.LU) 
			{
				dir = Direction.RD;
				move2();
				step = 0;
			}
			else if(this.dir == Direction.U) 
			{
				dir = Direction.D;
				move2();
				step = 0;
			}
			else if(this.dir == Direction.RU) 
			{
				dir = Direction.LD;
				move2();
				step = 0;
			}
			else if(this.dir == Direction.R) 
			{
				dir = Direction.L;
				move2();
				step = 0;
			}
			else if(this.dir == Direction.RD) 
			{
				dir = Direction.LU;
				move2();
				step = 0;
			}
			else if(this.dir == Direction.D) 
			{
				dir = Direction.U;
				move2();
				step = 0;
			}
			else if(this.dir == Direction.LD) 
			{
				dir = Direction.RU;
				move2();
				step = 0;
			}
			return true;
		}
		else return false;
	}
	
	public boolean collidesWistTanks(java.util.List<Tank> tanks)
	{
		for(int i=0; i<tanks.size(); i++)
		{
			Tank t = tanks.get(i);
			if(this != t && this.live && this.getRect().intersects(t.getRect()))
			{
				if(this.dir == Direction.L) 
				{
					dir = Direction.R;
					move2();
					step = 0;
				}
				else if(this.dir == Direction.LU) 
				{
					dir = Direction.RD;
					move2();
					step = 0;
				}
				else if(this.dir == Direction.U) 
				{
					dir = Direction.D;
					move2();
					step = 0;
				}
				else if(this.dir == Direction.RU) 
				{
					dir = Direction.LD;
					move2();
					step = 0;
				}
				else if(this.dir == Direction.R) 
				{
					dir = Direction.L;
					move2();
					step = 0;
				}
				else if(this.dir == Direction.RD) 
				{
					dir = Direction.LU;
					move2();
					step = 0;
				}
				else if(this.dir == Direction.D) 
				{
					dir = Direction.U;
					move2();
					step = 0;
				}
				else if(this.dir == Direction.LD) 
				{
					dir = Direction.RU;
					move2();
					step = 0;
				}
				return true;
			}
		}
		return false;
	}
	
	private class BloodBar
	{
		public void draw(Graphics g)
		{
			 int i=0;
			Color c = g.getColor();
			g.setColor(Color.red);
			if(life == 150) i = 1;
			if(i == 1) g.drawRect(x-15, y-12, WIDTH * 150/100 *2, 10);
			else g.drawRect(x-15, y-12, WIDTH*2, 10);
			int w = WIDTH * life/100 *2;
			g.fillRect(x-15, y-12, w, 10);
			g.setColor(c);		
		}
	
	}
	
	public void TankEat(Eatable b)
	{
		if(b.type == 1 && this.getRect().intersects(b.getRect()) && this.isGood() && this.getLife() < 100)
		{
				this.setLife(this.getLife()+5);
				b.setLive(false);
				tc.count += 50;
		}
		else if(b.type == 2 && this.getRect().intersects(b.getRect()) && this.isGood())
		{
			b.setLive(false);
			tc.missileNum  = 1;
			tc.count += 50;
			
		}
		else if(b.type == 3 && this.getRect().intersects(b.getRect()) && this.isGood())
		{
			b.setLive(false);
			tc.missileNum2  ++;
			tc.count += 50;
			
		}
		return ;
	}	
}























