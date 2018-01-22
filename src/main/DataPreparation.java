package main;
import java.io.*;

public class DataPreparation{
// Usuwamy niepotrzebne linie i je liczymy. Wynik zapisujemy do pliku
    
public static void przygotujplik(){
   File input = new File("D01.txt");
   File output = new File("plain.txt");
   String tekst, all = "";
   StringBuilder sB = new StringBuilder(all);

   try {
     try {
       try {
         Reader reader;
           reader = new InputStreamReader(new FileInputStream(input),"ASCII");
         BufferedReader fin = new BufferedReader(reader);
         Writer writer = new OutputStreamWriter(new FileOutputStream(output), "UTF-8");
         BufferedWriter fout = new BufferedWriter(writer);
         String tekscik;
         int counter = -1;
         fin.readLine();
         tekst = fin.readLine();
         while(tekst!=null){
           counter += 1;
           tekscik = tekst.substring(20,57);
           sB.append(tekscik);
           sB.append("\n");
           tekst = fin.readLine();
         }
         all = sB.toString();
         String string = String.valueOf(counter);
         fout.write(string + '\n');
         fout.write(all);
         fin.close();
         fout.close();
       } catch (UnsupportedEncodingException e) {}
     } catch (FileNotFoundException e) {}
   } catch (IOException e) {}
 }

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
