import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.ArrayList;
 
public class TankWarClient extends Frame 
{
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	
	Tank myTank = new Tank(50, 50, true, this);
	Tank enemyTank = new Tank(80, 80, false, this);
	
	Explode e = new Explode(70, 70, this);
	
	List<Missile> missiles = new ArrayList<Missile>();
	
	Image offScreenImage = null;
	
	public void paint(Graphics g)
	{
		g.drawString("missles count:" + missiles.size(), 10, 50);
		for(int i=0; i < missiles.size(); i++)
		{
			Missile m = missiles.get(i);
			m.hitTank(enemyTank);
			m.draw(g);
		}
		myTank.draw(g);
		enemyTank.draw(g);
		e.draw(g);
	}

	//Ë«»º³åÏûÉÁË¸
	public void update(Graphics g)
	{
		if(offScreenImage == null)
		{
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		Graphics goffScreen = offScreenImage.getGraphics();
		Color c = goffScreen.getColor();
		goffScreen.setColor(Color.GREEN);
		goffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		goffScreen.setColor(c);
		paint(goffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}

	public void lauchFrame()
	{
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
		this.setBackground(Color.GREEN);
		
		this.addKeyListener(new KeyMonitor());
		
		setVisible(true);
		
		new Thread(new PaintThread()).start();
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







	