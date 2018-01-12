/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
/**
 *
 * @author marcin
 */
public class Main {
  public static void main(String[] args) {
      SwingUtilities.invokeLater(new Runnable() {
          @Override
          public void run() {
              main.Widok interfejs = new main.Widok();
             
              Widok okno = new main.Widok();

              okno.setVisible(true);
          }
      });
              }

}
