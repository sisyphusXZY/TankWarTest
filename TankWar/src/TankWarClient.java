import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;



public class TankWarClient extends Frame 
{
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	
	int x = 50, y = 50;
	
	Image offScreenImage = null;
	
	public void paint(Graphics g)
	{
		//Color c = g.getColor();
		g.setColor(Color.blue);
		g.fillOval(x, y, 30, 30);
		//g.setColor(c);
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
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}
	}
		
	private class KeyMonitor extends KeyAdapter 
	{
		public void keyPressed(KeyEvent e)
		{
			int key = e.getKeyCode();
			switch(key)
			{
			case KeyEvent.VK_RIGHT:
				x += 5;
				break;
			case KeyEvent.VK_LEFT:
				x -= 5;
				break;
			case KeyEvent.VK_UP:
				y -= 5;
				break;
			case KeyEvent.VK_DOWN:
				y += 5;
				break;
			}
		}
		
	}
	
}







	