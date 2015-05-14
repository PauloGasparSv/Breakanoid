import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Game extends GameState{
	private final int LITTLE = 0, BIG = 1;

	private BufferedImage [] ball;

	public Game(GameStateManager gsm, Loader loader){
		super(gsm,loader);
		init(loader);
	}

	public void init(Loader l){
		ball = new BufferedImage[2];
		ball[LITTLE] = l.image("bolap.png");
		ball[BIG] = l.image("bolab.png");
	}

	public void update(double delta){

	}

	public void draw(Graphics2D g){

	}

	public void keyPressed(int k){

	}

	public void keyReleased(int k){

	}
	public void mousePressed(int e){

	}

	public void mouseReleased(int e){
		
	}


}