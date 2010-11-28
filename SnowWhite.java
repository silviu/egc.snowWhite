import java.awt.Color;
import java.awt.event.KeyEvent;

public class SnowWhite extends Queen {
	public SnowWhite(double x, double y, double z, Color fill, Color contur) {
		super(x, y, z, fill, contur);
	}

	public SnowWhite(double x, double y, double z, Color fill, Color contur, double scale) {
		super(x, y, z, fill, contur, scale);
	}

	public void key_decide(int key) {
		double dx = 0, dy = 0, dz = 0;
		switch (key) {
		case KeyEvent.VK_W:
			if (n_moves_z == 15)
				break;
			dz = -1;

			dx *= 5;
			dy *= 5;
			dz *= 5;
			n_moves_z++;
			break;
		case KeyEvent.VK_S:
			if (n_moves_z == -43)
				break;
			dz = 1;

			dx *= 5;
			dy *= 5;
			dz *= 5;
			n_moves_z--;
			break;

		case KeyEvent.VK_A:
			if (n_moves_x == 0)
				break;
			dx = 1;
			dy = 1;

			dx *= 3;
			dy *= 3;
			dz *= 3;
			n_moves_x--;
			break;

		case KeyEvent.VK_D:
			if (n_moves_x == 73)
				break;
			dx = -1;
			dy = -1;

			dx *= 3;
			dy *= 3;
			dz *= 3;

			n_moves_x++;
			break;
		}
		update_position(dx, dy, dz);
	}

	public void update_position(double dx, double dy, double dz) {
		dx -= dz;
		dy += dz;
		head.translate(dx, dy, dz);
		body.translate(dx, dy, dz);
		x += dx;
		z += dz;
		y += dy;
	}
}
