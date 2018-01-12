package main;
import java.io.*;

public class Apriori {
  
public static String[][] prepare() throws IOException, FileNotFoundException, UnsupportedEncodingException {
        // z pliku do tabeli o wymiarze [5,w]
        // wczytujemy dane z pliku, pierwsza linijka to ilość wierszy z danymi, reszta to dane
        // wczytane elementy wpisujemy do tablicy dwuwymiarowej na której będziemy działać
        File input = new File("plain.txt");
        String tekst, amount;
        String[] parts;
        Reader reader;
        reader = new InputStreamReader(new FileInputStream(input),"ASCII");
        BufferedReader fin = new BufferedReader(reader);
        amount = fin.readLine();
        int lines = Integer.parseInt(amount), line = 0;
        String[][] tablica = new String[5][lines];
        tekst = fin.readLine();
        while(tekst != null) {
                parts = tekst.split(";");
                for (int i = 0; i < 5; i++ ) {
                        tablica[i][line] = parts[i];
                }
                line += 1;
                tekst = fin.readLine();
        }
        fin.close();
        return tablica;
}
}
