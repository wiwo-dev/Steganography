package steganografia3;

import java.awt.image.WritableRaster;

public class Tekst {

    public String tekst;//zwykly tekst
    public String tekst2;//konwertowany na duze litery i ascii 0-32
    public byte[] twoBitBytes;//wiadomosc w tabeli po dwa bity
    public byte[] msgBytes;//wiadomosc jako bajty
    public WritableRaster wr;//writable raster
    public int imgRows, imgCols;
    public int[][][] threeDPix;
    public int[][][] temp3D;
    public WritableRaster wrResult;
    public int insertionPoint;
    public int skipValue;

    //odczytywanie
    byte[] extractedTwoBitBytes;
    StringBuffer extractedMsg;

    //do zapisu
    public Tekst(String tekst, WritableRaster wr, int rows, int cels) {
        this.tekst = tekst;
        this.wr = wr;
        this.imgCols = cels;
        this.imgRows = rows;
    }

    //do odczytu podczas dzialania
    public Tekst(WritableRaster wr, int rows, int cols) {
        this.wr = wr;
        this.imgCols = cols;
        this.imgRows = rows;
        this.insertionPoint = 0;
    }

    //do odczytu z osobnego pliku
    public Tekst(LadujObraz img) {
        this.wr = img.wr;
        this.imgCols = img.imgCols;
        this.imgRows = img.imgRows;
        this.insertionPoint = 0;
    }

    //w celu zamiany na duze litery i żeby zmieścić się w 32 bitach
    //dzieki temu mieszcze litere na jednym pixelu (po 2 LSB z R G i B)
    public void konwersja() {
        tekst = tekst + "_";//dodaje podkreslenie jako koniec tekstu
        String nowy = tekst.toUpperCase();
        StringBuffer sixBitMsg = new StringBuffer();
        char temp;
        for (int i = 0; i < nowy.length(); i++) {
            temp = (char) (nowy.charAt(i) - 32);
            sixBitMsg.append(temp);

        }
        tekst2 = new String(sixBitMsg);
        System.out.println("tekst do zapisania (tutaj wyswietla sie krzaki): " + tekst2);
        System.out.print("imgROws: " + imgRows + " ");
        System.out.println("imgCols: " + imgCols);
    }

    public void dalej1() {
        threeDPix = new int[imgRows][imgCols][3];

        //wyciagam informacje na temat kolorow na poszczegolnych pixelach
        for (int row = 0; row < imgRows; row++) {
            for (int col = 0; col < imgCols; col++) {
                threeDPix[row][col][0] = wr.getSample(col, row, 0);
                threeDPix[row][col][1] = wr.getSample(col, row, 1);
                threeDPix[row][col][2] = wr.getSample(col, row, 2);
            }
        }

        //kopiuje calosc do nowej tablicy
        temp3D = new int[imgRows][imgCols][3];
        for (int row = 0; row < imgRows; row++) {
            for (int col = 0; col < imgCols; col++) {
                temp3D[row][col][0] = threeDPix[row][col][0];
                temp3D[row][col][1] = threeDPix[row][col][1];
                temp3D[row][col][2] = threeDPix[row][col][2];
            }
        }
    }

    public void dalej2() {
        msgBytes = tekst2.getBytes();
        System.out.println("Dlugosc wiadomosci: " + msgBytes.length);

        twoBitBytes = new byte[3 * msgBytes.length];
        System.out.println("Dlugosc wiadomosci dwubitowej: " + twoBitBytes.length);

        //zamieniam na tablice gdzie w kazdej komorce mam tylko dwa bity liter - wiadomosci
        int twoBitByteCnt = 0;
        for (int i = 0; i < msgBytes.length; i++) {
            twoBitBytes[twoBitByteCnt++] = (byte) (msgBytes[i] & 0x03);
            twoBitBytes[twoBitByteCnt++] = (byte) ((msgBytes[i] >> 2) & 0x03);
            twoBitBytes[twoBitByteCnt++] = (byte) ((msgBytes[i] >> 4) & 0x03);
        }

    }

    public void dalej3() {
        //za pomoca petli dolaczam po dwa bity danej litery do
        //kolory czerwonego, zielonego i czerownego kazdego pixela
        int twoBitByteCnt = 0;
        for (int row = 0; row < imgRows; row++) {
            for (int col = 0; col < imgCols; col++) {
                if (twoBitByteCnt < twoBitBytes.length) {
                    temp3D[row][col][0]
                            = (temp3D[row][col][0] & 0xFC)
                            | twoBitBytes[twoBitByteCnt++];
                    temp3D[row][col][1]
                            = (temp3D[row][col][1] & 0xFC)
                            | twoBitBytes[twoBitByteCnt++];
                    temp3D[row][col][2]
                            = (temp3D[row][col][2] & 0xFC)
                            | twoBitBytes[twoBitByteCnt++];
                }
            }
        }
    }

    //zapisuje zmienione juz kolory wraz z fragmentami znakow
    public void zapiszTekst() {
        wrResult = wr;
        for (int row = 0; row < imgRows; row++) {
            for (int col = 0; col < imgCols; col++) {

                wrResult.setSample(col, row, 0, temp3D[row][col][0]);
                wrResult.setSample(col, row, 1, temp3D[row][col][1]);
                wrResult.setSample(col, row, 2, temp3D[row][col][2]);
            }
        }
    }

    public void odczyt1() {
        wrResult = wr;
        extractedTwoBitBytes = new byte[(imgRows * imgCols) * 3];
        threeDPix = new int[imgRows][imgCols][3];
        for (int row = 0; row < imgRows; row++) {
            for (int col = 0; col < imgCols; col++) {
                threeDPix[row][col][0] = wrResult.getSample(col, row, 0);
                threeDPix[row][col][1] = wrResult.getSample(col, row, 1);
                threeDPix[row][col][2] = wrResult.getSample(col, row, 2);
            }
        }
        temp3D = new int[imgRows][imgCols][3];
        for (int row = 0; row < imgRows; row++) {
            for (int col = 0; col < imgCols; col++) {
                temp3D[row][col][0]
                        = threeDPix[row][col][0];
                temp3D[row][col][1]
                        = threeDPix[row][col][1];
                temp3D[row][col][2]
                        = threeDPix[row][col][2];

            }
        }

        int twoBitByteCnt = 0;
        
        //za pomoca petli odczytuje informacje z dwoch ostatnich bitow
        for (int row = 0; row < imgRows; row++) {
            for (int col = 0; col < imgCols; col++) {

                extractedTwoBitBytes[twoBitByteCnt++]
                        = (byte) (temp3D[row][col][0] & 0x03);
                extractedTwoBitBytes[twoBitByteCnt++]
                        = (byte) (temp3D[row][col][1] & 0x03);
                extractedTwoBitBytes[twoBitByteCnt++]
                        = (byte) (temp3D[row][col][2] & 0x03);
            }
        }

    }

    
    //'wciagam' zapiane w pixelach znaki
    public void odczyt2() {
        int licznik=0;
        extractedMsg = new StringBuffer();
        int twoBitByteCnt = 0;
        byte element;
        for (int i = 0; i <= extractedTwoBitBytes.length / 3; i++) {
            element = (byte) (extractedTwoBitBytes[twoBitByteCnt++]);
            element = (byte) (element | (extractedTwoBitBytes[twoBitByteCnt++] << 2));
            element = (byte) (element | (extractedTwoBitBytes[twoBitByteCnt++] << 4));
            //przerywa dzialanie jesli napotka znak konca wiadomosci - '_'
            if ((char) (element + 32) == '_') {
                break;
            }
            extractedMsg.append((char) (element + 32));
            licznik++;
            
        }
        System.out.println("Odczytanych zankow: " + licznik);
    }

    
    //wypisuje na ekran odczytana wiadomosc
    public void wyswietl() {
        
        System.out.println("Odczytana wiadomość: "
                + extractedMsg.substring(3));
    }

}
