import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;


public class Mirror {
	int OFFSET = 10;
	int OUTER_WIDTH  = 200;
	int OUTER_HEIGHT = 100;
	int INNER_WIDTH  = OUTER_WIDTH - 2 * OFFSET;
	int INNER_HEIGHT = OUTER_HEIGHT - 2 * OFFSET;
	
	Shape outer_shape, inner_shape;
	
	public Mirror(int x, int y) {
		y = y+200;
		Polygon outer_frame, inner_frame;
		outer_frame = new Polygon();
		inner_frame = new Polygon();
		
		outer_frame.addPoint(0, 0);
		outer_frame.addPoint(OUTER_WIDTH, 0);
		outer_frame.addPoint(OUTER_WIDTH, OUTER_HEIGHT);
		outer_frame.addPoint(0, OUTER_HEIGHT);
		
		inner_frame.addPoint(0, 0);
		inner_frame.addPoint(INNER_WIDTH, 0);
		inner_frame.addPoint(INNER_WIDTH, INNER_HEIGHT);
		inner_frame.addPoint(0, INNER_HEIGHT);
		
		inner_frame.translate(OFFSET, OFFSET);
		outer_frame.translate(x, y);
		inner_frame.translate(x, y);
		AffineTransform af = new AffineTransform();
		af.setToShear(.1, -1);
		outer_shape = af.createTransformedShape(outer_frame);
		inner_shape = af.createTransformedShape(inner_frame);
	}
	
	public void draw(Graphics g_) {
		Graphics2D g = (Graphics2D)g_;
		g.setColor(Color.PINK);
		g.fill(outer_shape);
		g.setColor(Color.black);
		g.draw(outer_shape);
		g.draw(inner_shape);
		g.setColor(Scene.SKY_COLOR);
		g.fill(inner_shape);
	}
}
