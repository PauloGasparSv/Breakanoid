import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Image {
	
	
	public  BufferedImage [] split(BufferedImage img,int numFrames){
		BufferedImage temp [] = new BufferedImage[numFrames];
		for(int i=0;i<numFrames;i++)
			temp[i] = img.getSubimage(i*img.getWidth()/numFrames, 0, img.getWidth()/numFrames, img.getHeight());
		return temp;
	}
	
	public  BufferedImage [] cakeCut(BufferedImage img,int numCols,int numLines){
		int current = 0;
		int w = img.getWidth()/numCols;
		int h = img.getHeight()/numLines;
		BufferedImage temp [] = new BufferedImage[numCols*numLines];
		for(int y=0;y<numLines;y++){
			for(int x=0;x<numCols;x++){
				temp[current] = img.getSubimage(x*w, y*h, w, h);
				current++;
			}
		}
		return temp;
	}
	
	public  BufferedImage flip(BufferedImage image){
		 AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
		    tx.translate(-image.getWidth(null), 0);
		    AffineTransformOp op = new AffineTransformOp(tx,
		        AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		    image = op.filter(image, null);
		    return image;
	}
	
	public  BufferedImage [] reverseArray(BufferedImage array[]){
		BufferedImage [] newArray = new BufferedImage[array.length];
		int current = 0;
		for(int i = array.length-1; i>-1; i--){
			newArray[current] = array[i];
			current++;			
		}
		return newArray;
	}
	
}
