import javax.swing.JPanel;
import javax.swing.JFrame;
import java.lang.Thread;
import java.lang.Runnable;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Font;

public class Panel extends JPanel implements Runnable,KeyListener,MouseListener{
	public static final int WIDTH = 240;
	public static final int HEIGHT = 200;
	private boolean running;
	private Thread thread;
	private BufferedImage stage;
	private Graphics2D g;
	private GameStateManager gsm;
	private JFrame frame;

	public Panel(JFrame frame){
		super();
		this.frame = frame;
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		setFocusable(true);
		requestFocus();
	}
	public void addNotify(){
		super.addNotify();
		if(thread == null){	
			addKeyListener(this);
			addMouseListener(this);
			thread = new Thread(this);
			thread.start();
		}
	}

	private void init(){
		stage = new BufferedImage(WIDTH+20,HEIGHT+20,BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) stage.getGraphics();
		g.setBackground(new Color(68,80,128));
		g.setFont(new Font("Arial",Font.BOLD, 24));
		g.setColor(Color.BLACK);
		gsm = new GameStateManager(frame);
		running = true;
	}

	public void run(){
		init();
		long now = 0;
		long updateLenght = 0;
		int target_fps = 60;
		long optimal_time = 1000000000/target_fps;
		long lastLoopTime = System.nanoTime();
		double delta = 0;

		while(running){
			now = System.nanoTime();
			updateLenght = now - lastLoopTime;
			lastLoopTime = now;
			delta = updateLenght/(double) optimal_time;

			gsm.update(delta);
			gsm.draw(g);
			Graphics g2 = getGraphics();
			g2.drawImage(stage,0,0,null);
			g2.dispose();

			try{
				Thread.sleep((lastLoopTime - System.nanoTime() + optimal_time) / 1000000);
			}catch(Exception e){}
		}
		System.exit(0);
	}

	public void keyPressed(KeyEvent e) {
		gsm.keyPressed(e.getKeyCode());
	}
	public void keyReleased(KeyEvent e) {
		gsm.keyReleased(e.getKeyCode());
	}
	public void keyTyped(KeyEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
	}	
	
	public void mousePressed(MouseEvent e) {
		gsm.mousePressed(e);
	}
	public void mouseReleased(MouseEvent e) {
		gsm.mouseReleased(e);
	}
	
	public void mouseEntered(MouseEvent e) {
		
	}
	public void mouseExited(MouseEvent e) {
		
	}
}