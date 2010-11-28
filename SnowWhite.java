import java.awt.Color;
import java.awt.event.KeyEvent;


public class SnowWhite extends Queen{
	public SnowWhite(double x, double y, double z, Color fill, Color contur) {
		super(x, y, z, fill, contur);
	}
	public void key_decide(int key) {
		double dx = 0, dy = 0,dz = 0;
		switch(key) {
		case KeyEvent.VK_W:
			if (n_moves_z == 18)
				break;
			dz = -1;
			n_moves_z++;
			break;
		case KeyEvent.VK_S:
			if (n_moves_z == -39)
				break;
			dz = 1;
			n_moves_z--;
			break;
			
		case KeyEvent.VK_A:
			if (n_moves_x == 0)
				break;
			dx = 1;
			dy = 1;
			n_moves_x--;
			break;
		
		case KeyEvent.VK_D:
			if (n_moves_x == 90)
				break;
			dx = -1;
			dy = -1;
			n_moves_x++;
			break;
		}
		update_position(dx, dy, dz);
	}
}
