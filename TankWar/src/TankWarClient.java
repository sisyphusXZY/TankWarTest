import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class TankWarClient extends Frame 
{
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	
	private int bloodType = 1;
	private int supermissileType = 2;
	private int supermissile2Type = 3;
	
	Tank myTank = new Tank(50, 50, true, false, Tank.Direction.STOP, this);
	Tank bossTank = new Tank(80, 100, false, true, Tank.Direction.STOP, this);
	
	Wall w1 = new Wall(100, 100, 20, 450, this), 
		w2 = new Wall(250, 150, 480, 20, this),
		w3 = new Wall(430, 230, 20, 300, this);
	
	Eatable blood = new Eatable(200, 400, Eatable.Direction.STOP, true, bloodType, this);
	Eatable superMissile = new Eatable(300, 600, Eatable.Direction.STOP, true, supermissileType, this);
	Eatable superMissile2 = new Eatable(400, 500, Eatable.Direction.STOP, true, supermissile2Type, this);
	
	private static Random r = new Random();
	List<Missile> missiles = new ArrayList<Missile>();
	List<Tank> tanks = new ArrayList<Tank>();	
	List<Explode> explodes = new ArrayList<Explode>();
	
	int count = 0;
	int stage = 1;
	int missileNum = 0;
	int missileNum2 = 0;
	Image offScreenImage = null;
	
	//boolean once = true;
	public void paint(Graphics g)
	{
		w1.draw(g);
		w2.draw(g);
		w3.draw(g);
		if(tanks.size() == 0)
		{
	//		if(count >= (1000*stage + 100)) once = true;
			for(int i=0; i<r.nextInt(15*stage) + 5; i++)
			tanks.add(new Tank(15 + r.nextInt(700) + 40*(i+1), r.nextInt(470) + 100, false, false, Tank.Direction.D, this));
		}
		if(count >= 1000*stage)
		{	
			if(count <= count + 100)
			{
				Color c = g.getColor();
				Font f1 = new Font("楷体", Font.BOLD, 110);
				Font f2 = new Font("楷体", Font.BOLD, 50);
				g.setFont(f1);
				g.setColor(Color.red);
				g.drawString("NO." + stage +"Stage", 60, 320);
				g.setFont(f2);
				g.drawString("KEEP GOING!(~￣▽￣)~", 120, 390);
				g.setColor(c);
			}
			stage ++;
		}
		if(count >= 1000 )
		{
			bossTank.draw(g);
		}
		if(count >= 2500 && count <= 2600)
		{
			bossTank.setLive(true);
			bossTank.setLife(150);
		}
		for(int i=0; i < missiles.size(); i++)
		{
			Missile m = missiles.get(i);
			m.hitTanks(tanks);
			m.hitTank(myTank);
			m.hitTank(bossTank);
			m.draw(g);
			m.hitWall(w1);
			m.hitWall(w2);
			m.hitWall(w3);
		}
		
		for(int i=0; i < explodes.size(); i++)
		{
			Explode e = explodes.get(i);
			e.draw(g);
		}
	
		for(int i=0; i < tanks.size(); i++)
		{
			Tank t = tanks.get(i);
			t.collidesWithWall(w1);
			t.collidesWithWall(w2);
			t.collidesWithWall(w3);
			t.draw(g);
			t.collidesWistTanks(tanks);
		}
		myTank.draw(g);
		myTank.collidesWithWall(w1);
		myTank.collidesWithWall(w2);
		
		blood.draw(g);
		superMissile.draw(g);
		superMissile2.draw(g);
		myTank.TankEat(blood);
		myTank.TankEat(superMissile);
		myTank.TankEat(superMissile2);

		//g.drawString("Missiles count:" + missiles.size(), 10, 50);
		Color c = g.getColor();
		Font f1 = new Font("楷体", Font.BOLD, 20);
		g.setFont(f1);
		g.setColor(Color.black);
		g.drawString("Your Score:" + count, 10, 50);
		g.drawString("tanks count:" + tanks.size(), 10, 70);
		g.drawString("Stage:" + stage, 10, 90);
		g.drawString("Doom[V]:" + missileNum, 10, 110);
		g.drawString("SuperMissile[B]:" + missileNum2, 10, 130);
		g.setColor(c);
	}

	//双缓冲消闪烁
	public void update(Graphics g)
	{
		if(offScreenImage == null)
		{
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		Graphics goffScreen = offScreenImage.getGraphics();
		Color c = goffScreen.getColor();
		goffScreen.setColor(Color.green);
		goffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		goffScreen.setColor(c);
		paint(goffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}

	public void lauchFrame()
	{
		for(int i=0; i<10; i++)
		{
			tanks.add(new Tank(50 + 40*(i+1), 400, false, false, Tank.Direction.D, this));
		}
		
		this.setLocation(200,100);
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		this.setTitle("TankWar");
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}		
		});
		this.setResizable(false);
		this.setBackground(Color.green);
		
		this.addKeyListener(new KeyMonitor());
		
		setVisible(true);
		
		new Thread(new PaintThread()).start();
		new Thread(new EatThread()).start();
	}
	 
	public static void main(String[] args) 
	{
		TankWarClient tc = new TankWarClient();
		tc.lauchFrame();
	}

	private class PaintThread implements Runnable 
	{
		public void run()
		{
			while(true)
			{
				repaint();
				try
				{
					Thread.sleep(50);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	private class EatThread implements Runnable 
	{
		public void run()
		{
			while(true)
			{
				if(!blood.isLive())
				{
					blood.setLive(true);
					blood.x = 15 + r.nextInt(600);
					blood.y = r.nextInt(400) + 50;
				}
				try
				{
					Thread.sleep(13000);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	/*		
	public void Stage(Graphics g)
	{
		if(count >= 1000*stage)
		{
		class TimeTask
			{
				public void string()
				{
					Color c = g.getColor();
					Font f1 = new Font("楷体", Font.BOLD, 110);
					Font f2 = new Font("楷体", Font.BOLD, 50);
					g.setFont(f1);
					g.setColor(Color.red);
					g.drawString("NO." + stage +"Stage", 60, 320);
					g.setFont(f2);
					g.drawString("KEEP GOING!(~￣▽￣)~", 120, 390);
					g.setColor(c);
				}
		
			//计时器到底怎么用的啊啊啊啊！！！！！！八嘎！！！ 7/31/2016 1:12
	
		}
	}
		}*/
	private class KeyMonitor extends KeyAdapter 
	{
		public void keyReleased(KeyEvent e)
		{
			myTank.keyReleased(e);
		}

		public void keyPressed(KeyEvent e)
		{
			myTank.keyPressed(e);
		}
		
	}
	
}







	