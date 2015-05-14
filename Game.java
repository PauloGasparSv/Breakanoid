import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;
import java.awt.Rectangle;

public class Game extends GameState{
	private final int LITTLE = 0, BIG = 1, WARNING = 7, LINE =11, RED = 0, LIGHTGREEN = 1, BROWN = 2, GREEN = 3, 
	PINK = 4, GRAY = 5, LIGHTGRAY = 6, BLUE = 8, SKIN = 9, YELLOW = 10, X=0, Y=1;

	private BufferedImage [] ballImage;
	private BufferedImage [] paddleImage;
	private BufferedImage [] blocksImage;

	private int [][] blocks;
	private float [] position;
	private int[] width,height;
	private float speed;

	private boolean right,left,space;
	private int currentPaddle;
	private int currentBall;

	private float [] ball_position;
	private float ball_speedX, ball_speedY;

	private boolean start;

	public Game(GameStateManager gsm, Loader loader){
		super(gsm,loader);
		init(loader);
	}

	public void init(Loader l){
		Image i = new Image();
		ballImage = new BufferedImage[2];
		paddleImage = new BufferedImage[2];
		blocksImage = new BufferedImage[12];
		blocks = new int [15][15];
		position = new float[2];
		width = new int[2];
		height = new int[2];
		ball_position = new float[2];

		for(int line = 0; line < 15; line++){
			for(int col = 0; col < 15; col++){
				blocks[line][col] = 0;
			}
		}

		ballImage[LITTLE] = l.image("bolap.png");
		ballImage[BIG] = l.image("bolab.png");

		paddleImage[LITTLE] = l.image("paddlep.png");
		paddleImage[BIG] = l.image("paddleb.png");

		BufferedImage temp = l.image("bricks+warn+line.png");
		blocksImage = i.cakeCut(temp,4,3);

		position[X] = 100;
		position[Y] = 160;
		width[LITTLE] = 20;
		width[BIG] = 28;
		height[LITTLE] = height[BIG] = 6;
		speed = 1f;
		currentPaddle = 0;
		ball_position[X] = 108;
		ball_position[Y] = 150;
		ball_speedX = 1;
		ball_speedY = -1;
		currentBall = 0;
		start = false;

	}

	public void update(double delta){
		
		if(new Rectangle((int)(ball_position[X] + delta*ball_speedX) ,(int)(ball_position[Y] + delta*ball_speedY),8,8).intersects(
			new Rectangle((int)position[X]-1, (int)position[Y], 2,7))){
				if(ball_speedX > 0)
					ball_speedX = -ball_speedX;
				ball_position[X] += delta*ball_speedX;
		}
		else if(new Rectangle((int)(ball_position[X] + delta*ball_speedX) ,(int)(ball_position[Y] + delta*ball_speedY),8,8).intersects(
			new Rectangle((int)position[X]+width[currentPaddle]-1, (int)position[Y], 2,7))){
				if(ball_speedX < 0)
					ball_speedX = -ball_speedX;
				ball_position[X] += delta*ball_speedX;
		}
		
		if(new Rectangle((int)(ball_position[X] + delta*ball_speedX) ,(int)(ball_position[Y] + delta*ball_speedY),8,8).intersects(
			new Rectangle((int)position[X], (int)position[Y], width[currentPaddle], 1)) ){
				//ball_speedX = -ball_speedX;
				ball_speedY = -ball_speedY;
				ball_position[Y] += delta*ball_speedY;
		}
		else if(new Rectangle((int)(ball_position[X] + delta*ball_speedX) ,(int)(ball_position[Y] + delta*ball_speedY),8,8).intersects(
			new Rectangle((int)position[X], (int)position[Y]+6, width[currentPaddle], 1))){
				ball_speedY = -ball_speedY;
				ball_position[Y] += delta*ball_speedY;
		}


		if(right && start){
			if(position[X] + delta + speed*delta < 262 -  width[currentPaddle])
				position[X] += speed*delta;
		}
		else if(left && start){
			if(position[X] - delta - speed*delta > 0)
				position[X] -=  speed*delta;
		}

		if(!start && space)
			start = true;

		


		for(int line = 0; line < 15; line++){
			for(int col = 0; col < 15; col++){
				if(blocks[line][col] != -1){
					if(new Rectangle((int)(ball_position[X] + delta*ball_speedX) ,(int)(ball_position[Y] + delta*ball_speedY),8,8).intersects(
						new Rectangle(10 + col * 16, 5 + line * 8, 16 , 1))){
							//ball_speedX = -ball_speedX;
							ball_speedY = -ball_speedY;
					}
					if(new Rectangle((int)(ball_position[X] + delta*ball_speedX) ,(int)(ball_position[Y] + delta*ball_speedY),8,8).intersects(
						new Rectangle(10 + col * 16, 13 + line * 8, 16 , 1))){
							//ball_speedX = -ball_speedX;
							ball_speedY = -ball_speedY;
					}
					if(new Rectangle((int)(ball_position[X] + delta*ball_speedX) ,(int)(ball_position[Y] + delta*ball_speedY),8,8).intersects(
						new Rectangle(10 + col * 16, 5 + line * 8, 1 , 8))){
							ball_speedX = -ball_speedX;
							//ball_speedY = -ball_speedY;
					}
					if(new Rectangle((int)(ball_position[X] + delta*ball_speedX) ,(int)(ball_position[Y] + delta*ball_speedY),8,8).intersects(
						new Rectangle(26 + col * 16, 5 + line * 8, 1 , 8))){
							ball_speedX = -ball_speedX;
							//ball_speedY = -ball_speedY;
					}
				}
			}
		}



		if(start){
			if(ball_position[X] + delta*ball_speedX < 0 || ball_position[X] + delta*ball_speedX > 260)
				ball_speedX = -ball_speedX;
			if(ball_position[Y] + delta*ball_speedY < 0 || ball_position[Y] + delta*ball_speedY > 200)
				ball_speedY = -ball_speedY;
			ball_position[X] += delta*ball_speedX;
			ball_position[Y] += delta*ball_speedY;
		}


	}

	public void draw(Graphics2D g){	
		g.clearRect(0,0,Panel.WIDTH,Panel.HEIGHT);
	
		for(int line = 0; line < 15; line++){
			g.drawImage(blocksImage[7], line * 16,180,null);
			for(int col = 0; col < 15; col++)
				if(blocks[line][col] != -1)
					g.drawImage(blocksImage[2], 10+col * 16,5+line * 8,null);
		}
		g.drawImage(blocksImage[7], 15 * 16,180,null);
		g.drawImage(blocksImage[7], 16 * 16,180,null);

		g.drawImage(paddleImage[currentPaddle],(int)position[X],(int)position[Y],null);
		g.drawImage(ballImage[currentBall],(int)ball_position[X], (int)ball_position[Y],null);
			
		//g.fillRect((int)position[X]-1, (int)position[Y], 2,7);
		//g.fillRect((int)position[X]+width[currentPaddle]-1, (int)position[Y], 2,7);
		//g.fillRect((int)position[X], (int)position[Y], width[currentPaddle], 1);
		//g.fillRect((int)position[X], (int)position[Y]+6, width[currentPaddle], 1);
	}

	public void keyPressed(int k){
		if(k == KeyEvent.VK_RIGHT)
			right = true;
		else if(k == KeyEvent.VK_LEFT)
			left = true;
		if(k == KeyEvent.VK_SPACE)
			space = true;
	}

	public void keyReleased(int k){
		if(k == KeyEvent.VK_RIGHT)
			right = false;
		if(k == KeyEvent.VK_LEFT)
			left = false;
		if(k == KeyEvent.VK_SPACE)
			space = false;
	}
	public void mousePressed(int e){

	}

	public void mouseReleased(int e){
		
	}


}