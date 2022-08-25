/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.salesinvoicegenerator_noreen_hany;

import view.salesInvoiceGeneratorFrame;
import controller.Controller;

/**
 *
 * @author HP
 */
public class SalesInvoiceGenerator_Noreen_Hany {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new salesInvoiceGeneratorFrame().setVisible(true);
                
                //new salesInvoiceGeneratorFrame().setVisible(true);
                salesInvoiceGeneratorFrame frame = new salesInvoiceGeneratorFrame();
                frame.setVisible(true);
                frame.controller.addFile("InvoiceHeader.csv", "InvoiceLines.csv");
            }
        });
        
    }
}
