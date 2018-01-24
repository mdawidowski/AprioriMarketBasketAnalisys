package funkcje;
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
           tekscik = tekst.substring(0,57);
           tekscik = tekst.replace(" ;", ";");
           tekscik = tekst.replace(" 00:00:00", "");
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

public static int getlinesnumber() throws FileNotFoundException, UnsupportedEncodingException, IOException{
   File input = new File("plain.txt");
   Reader reader;
   reader = new InputStreamReader(new FileInputStream(input),"ASCII");
   BufferedReader fin = new BufferedReader(reader);
   String number = fin.readLine();
   fin.close();
   int liczba = Integer.parseInt(number);
   return liczba;
}

}
