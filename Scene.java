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
	static Color bg_color = Color.white;
	Thread animator = new Thread(this);
	
	@Override
	public void init() {
		addKeyListener(this);
		int fps = 50;
		delay = fps > 0 ? 1000 / fps : 100;
		offscreen = createImage(this.getSize().width,this.getSize().height);
		bufferGraphics = offscreen.getGraphics();
	}

	@Override
	public void start() {
		animator.start();
	}
	
	@SuppressWarnings("deprecation")
	public void stop() {
		animator.stop();
	}

	@Override
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
	@Override
	public void paint(Graphics g_main) {
		window_width  = this.getSize().width;
		window_height = this.getSize().height;

		bufferGraphics.clearRect(0,0, window_width, window_height);

		bufferGraphics.setColor(bg_color);
		bufferGraphics.fillRect(0, 0, window_width, window_height);
		
		g_main.drawImage(offscreen,0, 0, this);
	}

	@Override
	public void update(Graphics g_main) {
		paint(g_main);
	}

	@Override
	public void keyPressed(KeyEvent ke) {
		keyState = ke.getKeyCode();
		//player.key_decide(keyState);
	}

	@Override
	public void keyReleased(KeyEvent ke) {}
	@Override
	public void keyTyped(KeyEvent ke) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {}

	public static void main (String[] args) {
		Applet applet = new Scene();
		Frame frame = new Frame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		applet.setPreferredSize(new Dimension(BOARD_WIDTH,BOARD_HEIGHT));

		frame.add(applet);
		frame.pack();

		applet.init();
		applet.start();

		frame.setVisible(true);
	}

}