import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

public class Mirror {
	final static int OFFSET = 10;
	final static int OUTER_WIDTH = 270;
	final static int OUTER_HEIGHT = 300;
	final static int INNER_WIDTH = OUTER_WIDTH - 2 * OFFSET;
	final static int INNER_HEIGHT = OUTER_HEIGHT - 2 * OFFSET;
	int x, y;

	public Shape outer_shape, inner_shape;

	public Mirror(int x, int y) {
		this.x = x;
		this.y = y;
		Polygon outer_frame = new Polygon();
		Polygon inner_frame = new Polygon();

		outer_frame.addPoint(0, 0);
		outer_frame.addPoint(OUTER_WIDTH, 0);
		outer_frame.addPoint(OUTER_WIDTH, OUTER_HEIGHT);
		outer_frame.addPoint(0, OUTER_HEIGHT);

		inner_frame.addPoint(0, 0);
		inner_frame.addPoint(INNER_WIDTH, 0);
		inner_frame.addPoint(INNER_WIDTH, INNER_HEIGHT);
		inner_frame.addPoint(0, INNER_HEIGHT);

		inner_frame.translate(OFFSET, OFFSET);
		outer_shape = outer_frame;
		inner_shape = inner_frame;
		AffineTransform af = new AffineTransform();
		af.setToShear(.1, -1);

		outer_shape = af.createTransformedShape(outer_shape);
		inner_shape = af.createTransformedShape(inner_shape);

		af = new AffineTransform();
		af.translate(x, y);
		outer_shape = af.createTransformedShape(outer_shape);
		inner_shape = af.createTransformedShape(inner_shape);
	}

	public void draw(Graphics g_) {
		Graphics2D g = (Graphics2D) g_;
		g.setColor(Color.PINK);
		g.fill(outer_shape);
		g.setColor(Color.black);
		g.draw(outer_shape);
		g.draw(inner_shape);
		g.setColor(Scene.SKY_COLOR);
		g.fill(inner_shape);
	}
}
