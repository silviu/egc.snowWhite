import java.awt.Color;
import java.awt.Graphics;

public class Dwarf extends SnowWhite {
	double angle;
	SnowWhite snow;
	public Dwarf(SnowWhite snow, Color fill, Color contur, double angle) {
		super(snow.x + 50, snow.y, snow.z, fill, contur, 0.5);
		this.angle = angle;
		this.snow = snow;
	}
	
	public void animate() {
		double dist = Math.abs((Scene.OGLINDA_X + 190 - snow.x)/2);
		
		double dx = this.x - snow.x;
		double dy = this.y - snow.y;
		update_position(-dx, -dy, 3);
		
		
		double new_dx = Math.cos(angle)*dist;
		double new_dy = Math.sin(angle)*dist;
		update_position(new_dx, new_dy, 3);
		angle += Math.PI/40;
	}
	
	public void draw(Graphics g) {
		super.draw(g);
		animate();
		
	}
}
