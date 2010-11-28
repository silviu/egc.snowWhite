import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.util.ArrayList;

public class Scene extends Applet implements KeyListener, Runnable {
	private static final long serialVersionUID = 1L;

	static final int OFFSET_GRID = 10;
	static final int BOARD_WIDTH = 1000;
	static final int BOARD_HEIGHT = 600;
	public static final int OGLINDA_X = 200;

	Image offscreen;

	int delay, frame;

	Graphics g_main, bufferGraphics;
	public static int window_width, window_height;
	public final static Color SKY_COLOR = Color.LIGHT_GRAY;
	Thread animator = new Thread(this);

	Queen queen = new Queen(OGLINDA_X + 345, 350, 3, Color.black, Color.white);
	SnowWhite snow = new SnowWhite(OGLINDA_X + 150, 350, 3, Color.white,
			Color.black);
	
	ArrayList<Dwarf> dwarfs = new ArrayList<Dwarf>();
	Mirror mirror = new Mirror(OGLINDA_X, 270);

	Floor floor = new Floor();
	
	public void create_dwarfs() {
		double angle = 2 * Math.PI/7;
		
		for (int i = 0; i < 7; i++) {
			dwarfs.add(new Dwarf(snow, Color.GREEN, Color.black, angle*i));
		}
	}

	public void init() {
		addKeyListener(this);
		int fps = 50;
		delay = fps > 0 ? 1000 / fps : 100;
		offscreen = createImage(this.getSize().width, this.getSize().height);
		bufferGraphics = offscreen.getGraphics();
		create_dwarfs();
	}

	public void start() {
		animator.start();
	}

	@SuppressWarnings("deprecation")
	public void stop() {
		animator.stop();
	}

	public void run() {
		// Remember the starting time
		long tm = System.currentTimeMillis();
		while (true) {
			// Display the next frame of animation.
			repaint();
			// Delay depending on how far we are behind.
			try {
				tm += delay;
				Thread.sleep(Math.max(0, tm - System.currentTimeMillis()));
			} catch (InterruptedException e) {
				break;
			}
			// Advance the frame
			frame++;
		}
	}

	public void paint(Graphics g_main) {
		window_width = this.getSize().width;
		window_height = this.getSize().height;

		bufferGraphics.clearRect(0, 0, window_width, window_height);

		bufferGraphics.setColor(SKY_COLOR);
		bufferGraphics.fillRect(0, 0, window_width, window_height);
		floor.draw(bufferGraphics);
		mirror.draw(bufferGraphics);
		
		Graphics2D g2 = (Graphics2D) bufferGraphics;
		g2.clip(mirror.inner_shape);
		for (Dwarf dwarf : dwarfs) {
			if (dwarf.angle >= 8*Math.PI/7)
				dwarf.draw(bufferGraphics);
		}
		snow.draw(bufferGraphics);
		
		for (Dwarf dwarf : dwarfs) {
			if (dwarf.angle < 8*Math.PI/7)
				dwarf.draw(bufferGraphics);
		}
		
		g2.setClip(null);

		for (Dwarf dwarf : dwarfs) {
			dwarf.animate();
		}
		
		queen.draw(bufferGraphics);

		g_main.drawImage(offscreen, 0, 0, this);
	}

	public void update(Graphics g_main) {
		paint(g_main);
	}

	public void keyPressed(KeyEvent ke) {
		int keyState = ke.getKeyCode();
		queen.key_decide(keyState);
		snow.key_decide(keyState);
		for (Dwarf dwarf : dwarfs)
			dwarf.key_decide(keyState);
	}

	public void keyReleased(KeyEvent ke) {
	}

	public void keyTyped(KeyEvent ke) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
	}

	public static void main(String[] args) {
		Applet applet = new Scene();
		Frame frame = new Frame();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		applet.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));

		frame.add(applet);
		frame.pack();

		applet.init();
		applet.start();

		frame.setVisible(true);
	}

}