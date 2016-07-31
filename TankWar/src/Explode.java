import java.awt.Color;
import java.awt.Graphics;

public class Explode
{
	int x, y;
	private boolean live = true;
	
	private TankWarClient tc;
	
	int[] diameter = {4, 10, 11, 27, 38, 40, 35, 30, 20, 14};
	int step = 0;
	
	public Explode(int x, int y, TankWarClient tc)
	{
		this.x = x;
		this.y = y;
		this.tc = tc;
	}
	
	public void draw(Graphics g)
	{
		if(!live) return ; 
		
		if(step ==  diameter.length)
		{
			live = false;
			step = 0;
			return;
		}
		
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, diameter[step], diameter[step]);
		g.setColor(c);
		
		step++;
	}
}
