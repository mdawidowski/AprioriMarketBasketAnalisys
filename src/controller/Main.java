/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import javax.swing.SwingUtilities;
import view.Widok;
/**
 *
 * @author marcin
 */
public class Main {
  public static void main(String[] args) {
      SwingUtilities.invokeLater(() -> {
            view.Widok interfejs = new view.Widok();

            Widok okno = new view.Widok();


            okno.setVisible(true);
            });  
              }

}
