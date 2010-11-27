import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;


public class Paralleliped extends Path2D.Float {
	
	private static final long serialVersionUID = 1L;
	int width, height;
	int x, y;
	
	public Paralleliped (float x, float y, float w, float h) {
		super();
		this.append(new Rectangle2D.Float(-w/2, -h/2, w/2, h/2), true);
		this.append(new Rectangle2D.Float(-w/2, -h/2, w/2, h/2), true);
		
	}
}
