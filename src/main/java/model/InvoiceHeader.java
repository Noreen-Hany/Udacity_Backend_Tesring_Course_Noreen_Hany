/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author HP
 */

import java.util.Date;
import java.util.ArrayList;
import view.salesInvoiceGeneratorFrame;


public class InvoiceHeader {
    
    private int number;
    private Date date;
    private String name;
    
    private ArrayList<InvoiceLine> lines;

    public InvoiceHeader(int number, Date date, String name) {
        this.number = number;
        this.date = date;
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    public ArrayList<InvoiceLine> getLines() {
        // Lazy Loading
        if (lines == null)
            lines = new ArrayList<>();
        return lines;
    }
    
     public double getTotal() {
        double total = 0.0;
        
        for (InvoiceLine line : getLines()) {
            total += line.getTotal();
        }
        
        return total;
    }
     
    @Override
     public String toString(){
         return "InvoiceHeader{" + "number=" + number + 
                ", date=" + date + ", name=" + name + '}';
     }
     
     public String getAsCSV(){
         return number + "," + salesInvoiceGeneratorFrame.sdf.format(date) + "," + name;
     }
    
}
