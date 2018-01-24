/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;


/**
 *
 * @author marcin
 */
public class Funkcje {
    public static Baza b = new Baza();
    public static Baza c = new Baza();

    public static void prepare() throws IOException, FileNotFoundException, UnsupportedEncodingException, SQLException {
        // z pliku do tabeli o wymiarze [5,w]
        // wczytujemy dane z pliku, pierwsza linijka to ilość wierszy z danymi, reszta to dane
        // wczytane elementy wpisujemy do tablicy dwuwymiarowej na której będziemy działać
        String tekst, amount;
        String[] parts;
        File input = new File("plain.txt");
        Reader reader;
        reader = new InputStreamReader(new FileInputStream(input),"ASCII");
        BufferedReader fin = new BufferedReader(reader);
        amount = fin.readLine();
        int lines = Integer.parseInt(amount), line = 0;
        String[][] tablica = new String[6][lines];
        tekst = fin.readLine();
        while(tekst != null) {
                parts = tekst.split(";");
                for (int i = 0; i < 6; i++ ) {
                        tablica[i][line] = parts[i];
                }
                PreparedStatement zadanie = b.conn.prepareStatement("insert into Transakcje values (?, ?, ?, ?, ?, ?);");
                zadanie.setString(1, tablica[0][line]);
                zadanie.setString(2, tablica[1][line]);
                zadanie.setString(3, tablica[2][line]);
                zadanie.setString(4, tablica[3][line]);
                zadanie.setString(5, tablica[4][line]);
                zadanie.setString(6, tablica[5][line]);
                zadanie.execute();
                line += 1;
                tekst = fin.readLine();
        }
        fin.close();
}

	 public static void countSupport(String id, int lines) throws SQLException{
             DecimalFormat df = new DecimalFormat("#.##########");
             id="130207";
             String query = "SELECT count(*) FROM Transakcje WHERE produktkid like '" + id + "%'";
             Statement stmt = c.conn.createStatement();
             ResultSet rs = stmt.executeQuery(query);
             String value;
             value = rs.getString(1);
             float select = Float.parseFloat(value);
             select = select / lines;
             System.out.println("MB2 in Decimal Notation: " + df.format(select));
             System.out.println(select);
	 }
}
