package model;
import java.io.*;
public class ClearData{


public static void main(String[] args) {
  ClearData start = new ClearData();
  start.przygotuj();
}

public void przygotuj(){
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
         fin.readLine();
         tekst = fin.readLine();
         while(tekst!=null){
           tekscik = tekst.substring(20,57);
           sB.append(tekscik);
           sB.append("\n");
           tekst = fin.readLine();
         }
         all = sB.toString();
         fout.write(all);
         fin.close();
         fout.close();
       } catch (UnsupportedEncodingException e) {}
     } catch (FileNotFoundException e) {}
   } catch (IOException e) {}
 }
}
