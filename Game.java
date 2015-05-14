import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Game extends GameState{
	private final int LITTLE = 0, BIG = 1;

	private BufferedImage [] ballImage;
	private BufferedImage [] paddleImage;

	public Game(GameStateManager gsm, Loader loader){
		super(gsm,loader);
		init(loader);
	}

	public void init(Loader l){
		Image i = new Image();
		ballImage = new BufferedImage[2];
		paddleImage = new BufferedImage[2];

		ballImage[LITTLE] = l.image("bolap.png");
		ballImage[BIG] = l.image("bolab.png");

		paddleImage[LITTLE] = l.image("paddlep");
		paddleImage[BIG] = l.image("paddleb");


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