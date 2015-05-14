import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Loader{
	public BufferedImage image(String path){
		try{
			return(ImageIO.read(getClass().getResource("/assets/"+path)));
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"Error, could not load image "+path);
			System.exit(0);
			return null;
		}
	}
	public Clip audio(String path){
		try{
			AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().getResource("/assets/"+path));
			Clip clip = AudioSystem.getClip();
			clip.open(ais);
			return clip;
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"Error, could not load image "+path);
			System.exit(0);
			return null;
		}
	}

	public String text(String path){
		try{
			File tempFile = new File(getClass().getResource("/assets/").getPath()+path);
			FileReader fr = new FileReader(tempFile);
			String temp = new BufferedReader(fr).readLine();
			return temp;
		}catch(FileNotFoundException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"Error, could not load image "+path);
			System.exit(0);
			return null;
		}catch(IOException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"Error, could not load image "+path);
			System.exit(0);
			return null;
		}
	}
}