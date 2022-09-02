/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author HP
 */

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;


public class Invoice_Line_Table_Model extends AbstractTableModel {
    
   private String[] columns = {"Item", "Price", "Count", "Total"};
    private ArrayList<InvoiceLine> lines;

    public Invoice_Line_Table_Model(ArrayList<InvoiceLine> lines) {
        this.lines = lines;
    }

    public Invoice_Line_Table_Model() {
        
    }
    
   @Override
    public int getRowCount() {
        return lines.size();
    }

   @Override
    public int getColumnCount() {
        return columns.length;
    }

   @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceLine line = lines.get(rowIndex);
        
        switch (columnIndex) {
            case 0: return line.getName();
            case 1: return line.getPrice();
            case 2: return line.getCount();
            case 3: return line.getTotal();
        }
        
        return "";
    }

   @Override
    public String getColumnName(int column) {
        return columns[column];
    }
    
    
}
