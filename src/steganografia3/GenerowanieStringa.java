
package steganografia3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GenerowanieStringa {
    public String tekst;
    private File plik;
    
    public GenerowanieStringa()
    {
        Scanner odczyt = null;
        File plik = new File(".\\tekst.txt");
        try {
            odczyt = new Scanner(plik);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GenerowanieStringa.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tekst = odczyt.nextLine();
    }
    
    public String getTekst()
    {
        return tekst;
    }
    
}
