import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.lang.Math;

public class Queen {
	public final static int NORMAL_BODY_H = 30;
	public final static int NORMAL_BODY_D = 7;
	public final static int NORMAL_BODY_W = 10;

	public final static int NORMAL_HEAD_SIZE = 15;
	double body_w, body_h, body_d;
	double head_size;
	double x, y, z;
	int n_moves_z = 0;
	int n_moves_x = 0;

	Color fill_color;
	Color contur_color;

	Cub body;
	Cub head;

	public Queen(double x, double y, double z, Color fill, Color contur,
			double scale) {
		this(x, y, z, fill, contur, NORMAL_BODY_H * scale, NORMAL_BODY_D
				* scale, NORMAL_BODY_W * scale, NORMAL_HEAD_SIZE * scale);
	}

	public Queen(double x, double y, double z, Color fill, Color contur) {
		this(x, y, z, fill, contur, 1);
	}

	public Queen(double x, double y, double z, Color fill, Color contur,
			double body_h, double body_d, double body_w, double head_size) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.body_d = body_d;
		this.body_w = body_w;
		this.body_h = body_h;
		this.head_size = head_size;
		this.fill_color = fill;
		this.contur_color = contur;
		double angle = (3 * Math.PI / 4) - Math.toRadians(35);
		body = new Cub(x, y - body_h / 2, z, body_w, body_h, body_d,
				fill_color, contur_color);
		head = new Cub(x, y - body_h - (head_size / 2), z, head_size,
				head_size, head_size, fill_color, contur_color);

		body.rotate_by_y(angle, x, y, z);
		head.rotate_by_y(angle, x, y, z);

		angle = -0.04;

		body.rotate_by_z(angle, x, y, z);
		head.rotate_by_z(angle, x, y, z);

		angle = 0.170;

		body.rotate_by_x(angle, x, y, z);
		head.rotate_by_x(angle, x, y, z);
	}

	public boolean intersects(double x, double y){
		return head.intersects(x, y) || body.intersects(x, y);
	}

	public void draw(Graphics g) {
		body.paint(g);
		head.paint(g);
	}

	public void update_position(double dx, double dy, double dz) {
		dx *= 5;
		dy *= 5;
		dz *= 5;
		dx -= dz;
		dy += dz;
		head.translate(dx, dy, dz);
		body.translate(dx, dy, dz);
		x += dx;
		z += dz;
		y += dy;
	}

	public void key_decide(int key) {
		double dx = 0, dy = 0, dz = 0;
		switch (key) {
		case KeyEvent.VK_W:
			if (n_moves_z == 15)
				break;
			dz = -1;
			n_moves_z++;
			break;
		case KeyEvent.VK_A:
			if (n_moves_x == 0)
				break;
			dx = -1;
			n_moves_x--;
			break;
		case KeyEvent.VK_S:
			if (n_moves_z == -43)
				break;
			dz = 1;
			n_moves_z--;
			break;
		case KeyEvent.VK_D:
			if (n_moves_x == 73)
				break;
			dx = 1;
			n_moves_x++;
			break;
		}
		update_position(dx, dy, dz);
	}
}