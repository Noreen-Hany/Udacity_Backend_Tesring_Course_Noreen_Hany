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
import view.salesInvoiceGeneratorFrame;

public class Invoice_Header_Table_Model extends AbstractTableModel{
    
    private String[] columns = {"Invoice Number", "Customer Name", "Invoice Date", "Total"};
    private ArrayList<InvoiceHeader> invoices;
    
    public Invoice_Header_Table_Model(ArrayList<InvoiceHeader> invoices) {
        this.invoices = invoices;
    }
    
    @Override
    public int getRowCount() {
        return invoices.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceHeader inv = invoices.get(rowIndex);
        switch (columnIndex) {
            case 0: return inv.getNumber();
            case 1: return inv.getName();
            case 2: return salesInvoiceGeneratorFrame.sdf.format(inv.getDate());
            case 3: return inv.getTotal();
        }
        return "";
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
    
    
}
