
package steganografia3;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Steganografia3 {
    public static void main(String[] args) {
        //skrocona funkcja do zapisywania danych w obrazku
        //wiadomosc i sciezke do zdjecia mozna ustawic w funkcji w tej klasie
       //zapisuj();
        
        
        //skrocona funk do odczytywania danych zapisanych w obrazku
        //sciezke do obrazka mozna ustawic w ciele funkcji w tej klasie
        odczytuj();
    }
    
    public static void zapisuj()
    {
        //generuje dlugi string z pliku tekst.txt
        GenerowanieStringa gs = new GenerowanieStringa();
        
        LadujObraz img = new LadujObraz(".\\images\\steg.bmp");
        Tekst msg = new Tekst(gs.getTekst(), img.wr, img.imgRows, img.imgCols);
        msg.konwersja();
        msg.dalej1();
        msg.dalej2();
        msg.dalej3();
        msg.zapiszTekst();
       
        System.out.println("r: " + msg.imgRows + " c: " + msg.imgCols);
        ZapiszObraz.zapisz(img.img, ".\\images\\obrazek_nowy.bmp", 
                msg.wrResult, msg.imgRows, msg.imgCols);
    }
    
    
    public static void odczytuj()
    {
        LadujObraz img2 = new LadujObraz(".\\images\\obrazek_nowy.bmp");
        Tekst msg3 = new Tekst(img2);
        msg3.odczyt1();
        msg3.odczyt2();
        msg3.wyswietl();
    }

}
