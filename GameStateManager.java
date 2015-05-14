import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;

public class GameStateManager{
	public static final int MENU = 0, GAME = 1;
	private final int numStates = 2;
	private int currentState;
	
	private GameState gameState;
	private Loader loader;
	public JFrame frame;

	public GameStateManager(JFrame frame){
		this.frame = frame;
		loader = new Loader();
		setState(GAME);
	}

	public void update(double delta){
		if(gameState != null)
			gameState.update(delta);
	}
	
	public void draw(Graphics2D g){
		if(gameState != null)
			gameState.draw(g);
	}

	public void loadState(){
		switch(currentState){
			//case MENU: gameState = new Menu(this,loader); break;
			case GAME: gameState = new Game(this,loader); break;
		}
	}

	public void setState(int state){
		if(state >= 0 && state < numStates){
			currentState = state;
			loadState();
		}
	}

	public void keyPressed(int k){
		if(gameState != null)
			gameState.keyPressed(k);
	}

	public void keyReleased(int k){
		if(gameState != null)
			gameState.keyReleased(k);
	}

	public void mousePressed(MouseEvent e){
		if(gameState != null)
			gameState.mousePressed(e.getButton());
	}

	public void mouseReleased(MouseEvent e){
		if(gameState != null)
			gameState.mouseReleased(e.getButton());
	}
}