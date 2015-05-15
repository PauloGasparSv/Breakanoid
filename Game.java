import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;
import java.awt.Rectangle;
import java.util.Random;

public class Game extends GameState{
	private final int LITTLE = 0, BIG = 1, WARNING = 7, LINE =11, RED = 0, LIGHTGREEN = 1, BROWN = 2, GREEN = 3, 
	PINK = 4, GRAY = 5, LIGHTGRAY = 6, BLUE = 8, SKIN = 9, YELLOW = 10, X=0, Y=1,LEFT = 0,RIGHT = 1, VERTICAL =2, HORIZONTAL = 3;

	private BufferedImage [] ballImage;
	private BufferedImage [] paddleImage;
	private BufferedImage [] blocksImage;
	private BufferedImage [] pipes;
	private BufferedImage back;
	private BufferedImage spaceImg, leftImg,rightImg;

	private int [][] blocks;
	private float [] position;
	private int[] width,height;
	private float speed;
	private float mod;

	private boolean right,left,space;
	private int currentPaddle;
	private int currentBall;

	private float [] ball_position;
	private float ball_speedX, ball_speedY;

	private int numBlocks;

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
		pipes = new BufferedImage [4];

		for(int line = 0; line < 15; line++){
			for(int col = 0; col < 15; col++){
				blocks[line][col] = line > 5 && line < 8 && col > 1 && col < 13? 1 : -1;
				blocks[line][col] = line > 3 && line < 6 &&  col > 1 && col < 13 ? 2 : blocks[line][col];
			}
		}

		ballImage[LITTLE] = l.image("bolap.png");
		ballImage[BIG] = l.image("bolab.png");

		paddleImage[LITTLE] = l.image("paddlep.png");
		paddleImage[BIG] = l.image("paddleb.png");

		BufferedImage temp = l.image("bricks+warn+line.png");
		blocksImage = i.cakeCut(temp,4,3);

		back = l.image("back.png");
		spaceImg = l.image("space.png");
		leftImg = l.image("left.png");
		rightImg = l.image("right.png");

		temp = l.image("c_pipe.png");
		pipes[0] = i.split(temp,2)[0];
		pipes[1] = i.split(temp,2)[1];
		pipes[2] = l.image("v_pipe.png");
		pipes[3] = l.image("h_pipe.png");

		position[X] = 115;
		position[Y] = 160;
		width[LITTLE] = 20;
		width[BIG] = 28;
		height[LITTLE] = height[BIG] = 6;
		speed = 1.4f;
		currentPaddle = 0;
		ball_position[X] = 34;
		ball_position[Y] = 110;
		ball_speedX = 1f;
		ball_speedY = -1f;
		currentBall = 0;
		start = false;
		mod = 0.025f;
		numBlocks = 33;
	}

	public void update(double delta){
		if(right && start){
			if(position[X] + delta + speed*delta < 253 -  width[currentPaddle])
				position[X] += speed*delta;
		}
		else if(left && start){
			if(position[X] - delta - speed*delta > 9)
				position[X] -=  speed*delta;
		}

		if(new Rectangle((int)(ball_position[X]) ,(int)(ball_position[Y]),6,5).intersects(
			new Rectangle((int)position[X]-1, (int)position[Y] , 2,7))){
			if(ball_speedX > 0)
				ball_speedX = -ball_speedX;
			ball_position[X] += delta*ball_speedX;
			if(ball_speedY > 0 && ball_position[Y] < position[Y]+3){
				ball_speedY = -ball_speedY;
				ball_position[Y] += delta*ball_speedY;
			}
		}
		else if(new Rectangle((int)(ball_position[X] ) ,(int)(ball_position[Y] ),6,6).intersects(
			new Rectangle((int)position[X]+width[currentPaddle]-1, (int)position[Y], 2,7))){
			if(ball_speedX < 0)
				ball_speedX = -ball_speedX;
			ball_position[X] += delta*ball_speedX;
			if(ball_speedY > 0 && ball_position[Y] < position[Y] + 3){
				ball_speedY = -ball_speedY;
				ball_position[Y] += delta*ball_speedY; 
			}
		}
		
		if(new Rectangle((int)(ball_position[X]) ,(int)(ball_position[Y] ),6,6).intersects(
			new Rectangle((int)position[X] + 1, (int)position[Y], width[currentPaddle]-2, 1)) ){
				ball_speedY = -ball_speedY;
		}
		else if(new Rectangle((int)(ball_position[X] ) ,(int)(ball_position[Y]),6,6).intersects(
			new Rectangle((int)position[X] + 1, (int)position[Y]+6, width[currentPaddle]-2, 1))){
				ball_speedY = -ball_speedY;
		}

		
		if(!start && space)
			start = true;

		


		for(int line = 0; line < 15; line++){
			for(int col = 0; col < 15; col++){
				if(blocks[line][col] != -1){
					boolean hitX,hitY;
					hitX = hitY = false;
					if(new Rectangle((int)(ball_position[X] ) ,(int)(ball_position[Y]),8,8).intersects(
						new Rectangle(9 + col * 16, 6 + line * 8, 2 , 6))){
							ball_speedX = -ball_speedX;
							blocks[line][col] --;
							if(blocks[line][col] == 7)
								blocks[line][col] = 6;
							hitX = true;
							acBall();
							
					}
					else if(new Rectangle((int)(ball_position[X] ) ,(int)(ball_position[Y]),8,8).intersects(
						new Rectangle(26 + col * 16, 6 + line * 8, 2 , 6))){
							ball_speedX = -ball_speedX;
							blocks[line][col] --;
							if(blocks[line][col] == 7)
								blocks[line][col] = 6;
							hitX = true;
							acBall();
					}

					if(new Rectangle((int)(ball_position[X] ) ,(int)(ball_position[Y]),8,8).intersects(
						new Rectangle(10 + col * 16, 5 + line * 8, 16 , 1))){
							//ball_speedX = -ball_speedX;
							ball_speedY = -ball_speedY;
							blocks[line][col] --;
							if(blocks[line][col] == 7)
								blocks[line][col] = 6;
							hitY = true;
							acBall();
					}
					else if(new Rectangle((int)(ball_position[X] ) ,(int)(ball_position[Y]),8,8).intersects(
						new Rectangle(10 + col * 16, 13 + line * 8, 16 , 1))){
							//ball_speedX = -ball_speedX;
							ball_speedY = -ball_speedY;
							blocks[line][col] --;
							if(blocks[line][col] == 7)
								blocks[line][col] = 6;
							hitY = true;
							acBall();
					}
					if(hitX)
						ball_position[X] += delta*ball_speedX;
					if(hitY)
						ball_position[Y] += delta*ball_speedY;
				}
			}
		}

		System.out.println(ball_speedX);

		if(ball_position[Y]  > 200 && start){
			start = false;
			ball_position[X] = 34;
			ball_position[Y] = 110;
			position[X] = 115;
			position[Y] = 160;
			ball_speedX = 1f;
			ball_speedY = -1f;
		}

		if(start){
			if(ball_position[X] < 10 || ball_position[X] > 250)
				ball_speedX = -ball_speedX;
			if(ball_position[Y] < 8)
				ball_speedY = -ball_speedY;
			ball_position[X] += delta*ball_speedX;
			ball_position[Y] += delta*ball_speedY;
		}
		


	}

	private void acBall(){
		if(ball_speedX != 2.8f && ball_speedX != -2.8f){
			if(ball_speedX > 0)
				ball_speedX += mod;
			else
				ball_speedX -= mod;
			if(ball_speedY > 0)
				ball_speedY += mod;
			else
				ball_speedY -= mod;
		}
		
		if(ball_speedX > 2.8f)
			ball_speedX = 2.8f;
		if(ball_speedX < -2.8f)
			ball_speedX = -2.8f;
		if(ball_speedY > 2.8f)
			ball_speedY = 2.8f;
		if(ball_speedY < -2.8f)
			ball_speedY = -2.8f;
				
	}

	public void draw(Graphics2D g){	
		g.clearRect(0,0,Panel.WIDTH,Panel.HEIGHT);
			
		for(int x=0;x<9;x++)
			for(int y=0;y<7;y++)
				g.drawImage(back,x * 32 - 15,y * 32 - 15,null);

		for(int line = 0; line < 15; line++){
			//if(line<7)
				g.drawImage(blocksImage[7], 10+line * 16,183,null);
			for(int col = 0; col < 15; col++)
				if(blocks[line][col] > -1)
					g.drawImage(blocksImage[blocks[line][col]], 10+col * 16,5+line * 8,null);
		}
	
		g.drawImage(paddleImage[currentPaddle],(int)position[X],(int)position[Y],null);
		g.drawImage(ballImage[currentBall],(int)ball_position[X], (int)ball_position[Y],null);
		
		g.drawImage(pipes[LEFT],4,1,null);
		g.drawImage(pipes[RIGHT],250,1,null);
		for(int i=0; i <10; i++){
			g.drawImage(pipes[HORIZONTAL], 10 + i*24,0,null);
			g.drawImage(pipes[VERTICAL],4,7+i*24,null);
			g.drawImage(pipes[VERTICAL],250,7+i*24,null);
		}

		if(!start){
			g.drawImage(spaceImg, 80,50,null);
			g.drawImage(leftImg, 76,146,35,30,null);
			g.drawImage(rightImg, 136,146,35,30,null);
		}

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