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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author marcin
 */
public class Funkcje {
public static Baza b = new Baza();
public static Baza c = new Baza();
public static Baza d = new Baza();
public static Baza e = new Baza();

public static void insertToDB() throws IOException, FileNotFoundException, UnsupportedEncodingException, SQLException {
        // z pliku do tabeli o wymiarze [5,w]
        // wczytujemy dane z pliku, pierwsza linijka to ilość wierszy z danymi, reszta to dane
        // wczytane elementy wpisujemy do bazy danych
        String tekst, amount;
        String[] parts;
        File input = new File("plain.txt");
        Reader reader;
        reader = new InputStreamReader(new FileInputStream(input),"ASCII");
        BufferedReader fin = new BufferedReader(reader);
        amount = fin.readLine();
        int lines = Integer.parseInt(amount), line = 0;
        String[][] tablica = new String[7][lines];
        tekst = fin.readLine();
        while(tekst != null) {
                parts = tekst.split(";");
                for (int i = 0; i < 7; i++ ) {
                        tablica[i][line] = parts[i];
                }
                PreparedStatement zadanie = b.conn.prepareStatement("insert into Transakcje values (?, ?, ?, ?, ?, ?, ?);");
                zadanie.setString(1, tablica[0][line]);
                zadanie.setString(2, tablica[1][line]);
                zadanie.setString(3, tablica[2][line]);
                zadanie.setString(4, tablica[3][line]);
                zadanie.setString(5, tablica[4][line]);
                zadanie.setString(6, tablica[5][line]);
                zadanie.setString(7, tablica[6][line]);
                zadanie.execute();
                line += 1;
                tekst = fin.readLine();
        }
        fin.close();
}

public static void insertTransactionIdtoDB() throws SQLException {
        // nadawanie numeru transakcji dla każdego wiersza
        String query = "SELECT DISTINCT data,klient FROM Transakcje;";
        Statement stmt = d.conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        List rowValues = new ArrayList();
        List colValues = new ArrayList();
        while (rs.next()) {
                rowValues.add(rs.getString(1));
                colValues.add(rs.getString(2));
        }
        String[] ListDate,ListKlient;
        ListDate = (String[])rowValues.toArray(new String[rowValues.size()]);
        ListKlient = (String[])colValues.toArray(new String[colValues.size()]);

        String INSERT_RECORD = "UPDATE Transakcje SET transakcjaid=? WHERE (data=? and klient like ? );";
        PreparedStatement pstmt = d.conn.prepareStatement(INSERT_RECORD);
        for(int i=1; i<ListDate.length; i++) {
                pstmt.setInt(1, i);
                pstmt.setString(2, ListDate[i]);
                pstmt.setString(3, ListKlient[i]);
                pstmt.executeUpdate();
        }
}
public static int countNumberofTransactions() throws SQLException {
        String query = "SELECT COUNT(DISTINCT transakcjaid) FROM Transakcje;";
        Statement stmt = c.conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        String value;
        value = rs.getString(1);
        int select = Integer.parseInt(value);
        return select;
}

public static String[] getProducts() throws SQLException {
        // pobierz id wszystkich produktów bez powtarzania
        String query = "SELECT DISTINCT produktid FROM Transakcje;";
        Statement stmt = d.conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        List produktValues = new ArrayList();
        while (rs.next()) {
                produktValues.add(rs.getString(1));
        }
        String[] ProduktList;
        ProduktList = (String[])produktValues.toArray(new String[produktValues.size()]);
        return ProduktList;
}
public static String[] getListOfTransactions(String element) throws SQLException {
        // pobierz listę transakcji w których występuje produkt
        String query = "SELECT transakcjaid FROM Transakcje WHERE produktid LIKE '" + element + "%';";
        Statement stmt = e.conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        List produktValues = new ArrayList();
        while (rs.next()) {
                produktValues.add(rs.getString(1));
        }
        String[] ProduktList;
        ProduktList = (String[])produktValues.toArray(new String[produktValues.size()]);
        return ProduktList;
}

public static String[] getList(String transactionnumber) throws SQLException {
// pobierz listę produktów dla danej transakcji
        String query = "SELECT produktid FROM Transakcje WHERE transakcjaid=" + transactionnumber + ";";
        Statement stmt = e.conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        List produktValues = new ArrayList();
        while (rs.next()) {
                produktValues.add(rs.getString(1));
        }
        String[] list = (String[])produktValues.toArray(new String[produktValues.size()]);
        return list;
}

public static String getElementOfList(String[] list, String firstelement, int lastelemnumber){
// pobierz pierwszy element z listy różny od wybranego elementu
        int i = lastelemnumber;
        while(firstelement.equals(list[i])) {
                if(firstelement.equals(list[i])) {
                        i += 1;
                }
        }
        String element = list[i];
        return element;
}

public static int compareTransactions(String[] transactionlist, String[] transactionlisttocheck){
// porównaj listy i wypisz ilość wspólnych wystąpień
        int numberoftransactions = 0;
        for (int i = 0; i < transactionlist.length; i++) {
                for (int j = 0; j < transactionlisttocheck.length; j++ ) {
                        if (transactionlist[i].equals(transactionlisttocheck[j])) {
                                numberoftransactions += 1;
                        }
                }
        }
        return numberoftransactions;
}
public static String[] mergeProducts(String[] arr1, String[] arr2){
        String[] merged = new String[arr1.length + arr2.length];
        System.arraycopy(arr1, 0, merged, 0, arr1.length);
        System.arraycopy(arr2, 0, merged, arr1.length, arr2.length);
        Set<String> nodupes = new HashSet<>();
        nodupes.addAll(Arrays.asList(merged));
        String[] nodupesarray = new String[nodupes.size()];
        int i = 0;
        Iterator<String> it = nodupes.iterator();
        while(it.hasNext()) {
                nodupesarray[i] = it.next();
                i++;
        }
        return nodupesarray;
}

public static int countTransactionList(String element) throws SQLException {
        String[] transactionlist = getListOfTransactions(element);
        int count = transactionlist.length;
        return count;
}
public static String[] countHowManyTimes(String element) throws SQLException {
        String[] transactionlist = getListOfTransactions(element);               // pobieram listę transakcji w których występuje element
        String[] lista = getList(transactionlist[0]);                            // pobieram listę produktów z pierwszej transakcji w której występuje element
        String[] newlista = mergeProducts(lista,lista);                               // merge tabeli
        for (int i = 0; i < transactionlist.length-1; i+=2 ) {
                lista = getList(transactionlist[i]);
                String[] lista1 = getList(transactionlist[i+1]);
                newlista = mergeProducts(lista,lista1);                          // merge tabeli
        }
        int i = 0;
        String[] data = new String[newlista.length*2];
        String nextelement = getElementOfList(newlista, element,i);              // z listy produktów pobieram element różny od wybranego elementu
        String[] transactionlisttocheck = getListOfTransactions(nextelement);    // pobieram listę transakcji w których występuje nowy element
        int numberoftransactions = compareTransactions(transactionlist, transactionlisttocheck);  // porównuję obie transakcje czego wynikiem jest liczba wspólnych wystąpień
        data[0] = nextelement;
        String numbers = String.valueOf(numberoftransactions);
        data[1] = numbers;                                                    // dodaję wartości do listy
        for (i = 2; i < newlista.length; i+=2) {
                nextelement = getElementOfList(newlista, element,i);
                transactionlisttocheck = getListOfTransactions(nextelement);     // pobieram listę transakcji w których występuje nowy element
                numberoftransactions = compareTransactions(transactionlist, transactionlisttocheck); // porównuję obie transakcje czego wynikiem jest liczba wspólnych wystąpień
                data[i] = nextelement;
                numbers = String.valueOf(numberoftransactions);
                data[i+1] = numbers;                                            // dodaję wartości do listy
        }
        return data;
}

public static float countSupport(int howmanytimes, int lines) {
        // obliczanie supportu
        float wynik = (float) howmanytimes/lines;
        return wynik;
}

public static float countConfidence(float support, float support2) {
        //obliczanie confidence
        float wynik = support/support2;
        return wynik;
}
public static float countLift(float confidence, float supportB){
        float wynik = confidence/supportB;
        return wynik;
}
public static float countConviction(float confidence, float supportB){
        float wynik = supportB/confidence;
        return wynik;
}

}
