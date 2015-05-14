import javax.swing.JFrame;
import javax.swing.JPanel;
import java.lang.Thread;

public class MyGame{
	public static void main(String [] args){
		JFrame frame = new JFrame("Tetris");
		frame.setContentPane(new Panel(frame));
		frame.setVisible(true);	
		frame.setResizable(false);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
