
package steganografia3;

import java.awt.image.*;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;


public class ZapiszObraz {
//    public WritableRaster wr;
//    public BufferedImage bi;
//    public BufferedImage newImg;
    
//    public ZapiszObraz(String sciezka, WritableRaster wr, int rows, int cols) 
//    {
//        //this.bi = bi;
//        this.wr = wr;
//        //newImg = new BufferedImage(cols, rows, BufferedImage.TYPE_INT_ARGB);
//        bi.setData(wr);
//        
//        
//        try {
//            ImageIO.write(bi, "BMP", new File(sciezka));
//            
//            //ImageIO.write(image, "BMP", new File("D:\\obrazek_nowy.bmp"));
//        } catch (IOException ex) {
//           
//        }
//    }
    
    public static void zapisz(BufferedImage oldBi, String sciezka, WritableRaster wr, int rows, int cols)
    {
        BufferedImage newImg = oldBi;
        newImg.setData(wr);
        
        try {
            ImageIO.write(newImg, "BMP", new File(sciezka));
        } catch (IOException ex) {
            //Logger.getLogger(ZapiszObraz.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        }
    }
    
    
}
