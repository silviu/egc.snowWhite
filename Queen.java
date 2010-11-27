import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.lang.Math;

public class Queen {
	double body_w = 100;
	double body_h = 200;
	double body_d = 40;
	double head_size = 120;
	double x, y, z;

	public static final Color FILL_COLOR   = Color.BLACK;
	public static final Color CONTUR_COLOR = Color.WHITE;

	Cub body;
	Cub head;

	public Queen(double x, double y, double z) {
		y+=88;
		x+=80;
		this.x = x;
		this.y = y;
		this.z = z;
		double angle = (3*Math.PI/4) - Math.toRadians(35);
		body = new Cub(x, y - body_h/2, z, body_w, body_h, body_d, FILL_COLOR, CONTUR_COLOR);
		head = new Cub(x, y - body_h - (head_size/2), z, head_size, head_size, head_size, FILL_COLOR, CONTUR_COLOR);
		body.rotate_by_y(angle, x, y, z);
		head.rotate_by_y(angle, x, y, z);

		angle = - 0.04;

		body.rotate_by_z(angle, x, y, z);
		head.rotate_by_z(angle, x, y, z);

		angle = 0.205;

		body.rotate_by_x(angle, x, y, z);
		head.rotate_by_x(angle, x, y, z);
	}

	public void draw(Graphics g) {
		body.paint(g);
		head.paint(g);
	}
	
	

	public void key_decide(int key) {
		double dx = 0, dy = 0,dz = 0;
		switch(key) {
		case KeyEvent.VK_W:
			dz = -1;
			break;
		case KeyEvent.VK_A:
			dx = -1;
			break;
		case KeyEvent.VK_S:
			dz = 1;
			break;
		case KeyEvent.VK_D:
			dx = 1;
			break;
		}
		dx *= 5; dy *= 5; dz *= 5;
		dx -= dz; dy += dz;
		head.translate(dx, dy, dz);
		body.translate(dx, dy, dz);
		x += dx;
		z += dz;
		y += dy;
	}
}