import java.awt.*;

public class Wall
{
	int x, y, w, h;
	TankWarClient tc;
	
	public Wall(int x, int y, int w, int h, TankWarClient tc)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.tc = tc;
	}
	
	public void draw(Graphics g)
	{
		Color c = g.getColor();
		if(x == 430) g.setColor(Color.blue);
		else g.setColor(Color.orange);
		g.fillRect(x, y, w, h);
		g.setColor(c);
	}
	
	public Rectangle getRect()
	{ 
		return new Rectangle(x, y, w, h);
	}
	
}
