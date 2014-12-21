
package steganografia3;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class LadujObraz {
    public BufferedImage img;
    public WritableRaster wr;
    public int[][][] temp3d;
    public int[][][] threeDPix;
    int imgRows;
    int imgCols;
    
    public LadujObraz(String path)
    {
        try {
            img = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        imgRows = img.getHeight();
        imgCols = img.getWidth();
        wr = img.getRaster();

        System.out.println("Width = " + imgCols);
        System.out.println("Height = " + imgRows);
   
    } 
}
