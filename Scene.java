import java.awt.*;
import java.awt.event.*;
import java.applet.*;


public class Scene extends Applet implements KeyListener, Runnable
{
	private static final long serialVersionUID = 1L;

	static final int OFFSET_GRID   = 10;
	static final int BOARD_WIDTH   = 1000;
	static final int BOARD_HEIGHT  = 600;

	Image offscreen;

	int delay, frame;

	Graphics g_main, bufferGraphics;
	public static int window_width, window_height;
	public static int keyState;
	public final static Color SKY_COLOR = Color.LIGHT_GRAY;
	Thread animator = new Thread(this);
	public boolean first_time = true;
	
	Queen queen = new Queen(600, 300, 3);
	Mirror mirror = new Mirror(100, 100);
	
	Floor floor = new Floor();

	public void init() {
		addKeyListener(this);
		int fps = 50;
		delay = fps > 0 ? 1000 / fps : 100;
		offscreen = createImage(this.getSize().width,this.getSize().height);
		bufferGraphics = offscreen.getGraphics();
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
			} catch (InterruptedException e) {break;}
			// Advance the frame
			frame++;
		}
	}

	public void paint(Graphics g_main) {
		window_width  = this.getSize().width;
		window_height = this.getSize().height;

		bufferGraphics.clearRect(0,0, window_width, window_height);

		bufferGraphics.setColor(SKY_COLOR);
		bufferGraphics.fillRect(0, 0, window_width, window_height);
		floor.draw(bufferGraphics);
		
		queen.draw(bufferGraphics);
		mirror.draw(bufferGraphics);
		
		g_main.drawImage(offscreen, 0, 0, this);
	}


	public void update(Graphics g_main) {
		paint(g_main);
	}

	public void keyPressed(KeyEvent ke) {
		keyState = ke.getKeyCode();
		queen.key_decide(keyState);
	}

	public void keyReleased(KeyEvent ke) {}
	public void keyTyped(KeyEvent ke) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {}


	public static void main (String[] args) {
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