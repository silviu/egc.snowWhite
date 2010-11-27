import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;


public class Floor {
	
	public final static int WIDTH = 700;
	public final static int HEIGHT = 300;
	public static final int OFFSET_X = 400;
	public static final int OFFSET_Y = 100;
	
	int x, y;
	
	public void portView(Graphics2D g2) {
		AffineTransform atrans = null;

	    atrans = AffineTransform.getShearInstance(-1.0, 0);

	    if (atrans != null)
	      g2.setTransform(atrans);
	}
	
	public void draw(Graphics g_) {
		Graphics2D g = (Graphics2D) g_;
		portView(g);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(OFFSET_X, OFFSET_Y, WIDTH, HEIGHT);
		g.setColor(Color.black);
		g.drawRect(OFFSET_X, OFFSET_Y, WIDTH, HEIGHT);
		g.setTransform(new AffineTransform());
		
	}
}
