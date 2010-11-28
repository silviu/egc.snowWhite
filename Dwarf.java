import java.awt.Color;
import java.awt.Graphics;

public class Dwarf extends SnowWhite {
	double angle;
	SnowWhite snow;
	double dist;
	
	public Dwarf(SnowWhite snow, Color fill, Color contur, double angle) {
		super(snow.x + 50, snow.y, snow.z, fill, contur, 0.5);
		this.angle = angle;
		this.snow = snow;
		dist = Math.abs((Scene.OGLINDA_X + 190 - snow.x)/2);

	}
	
	public void animate() {
		double dx = this.x - snow.x;
		double dy = this.y - snow.y;
		update_position1(-dx, -dy, 3);
		
		
		double new_dx = Math.cos(angle)*dist;
		double new_dy = Math.sin(angle)*dist;
		update_position1(new_dx, new_dy, 3);
		
		angle += Math.PI/40;
		if (angle >= 2 * Math.PI)
			angle -= 2*Math.PI;
	}
	
	public void update_position(double dx, double dy, double dz) {
		super.update_position(dx, dy, dz);
		dist -= dx/2;
	}
	
	public void update_position1(double dx, double dy, double dz) {
		super.update_position(dx, dy, dz);
	}
}
