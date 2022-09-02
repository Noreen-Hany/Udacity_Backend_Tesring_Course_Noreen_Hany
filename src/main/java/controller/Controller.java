/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.InvoiceHeader;
import model.InvoiceLine;
import model.Invoice_Header_Table_Model;
import model.Invoice_Line_Table_Model;

import view.salesInvoiceGeneratorFrame;
import view.Invoice_Dialog;
import view.Line_Dialog;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.text.ParseException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import java.awt.*;
import javax.swing.table.DefaultTableModel;

/**
 * 
 * @author HP
 */
public class Controller implements ActionListener, ListSelectionListener {

     
    private Invoice_Dialog invoice_dialog;
    
    private Line_Dialog line_dialog;
    
    private salesInvoiceGeneratorFrame frame;
    
    public Controller(salesInvoiceGeneratorFrame frame){
        this.frame = frame;
    }
   

    @Override
     public void actionPerformed(ActionEvent e) {
         
        switch (e.getActionCommand()) {
            
            case "Create Invoice":
                  createInvoice();
                break;
                
            case "Delete Invoice":
                  deleteInvoice();
                break;
                
            case "Add item":
                  addItem();
                break;
                
            case "Delete Item":
                  deleteItem();
                break;
                
            case "Add File":
                  addFile(null, null);
                break;
                
            case "Save Data":
                  saveData();
                break;
                
            case "invoiceCreationDone":
                  invoiceCreationDone();
                break;
                
            case "invoiceCreationCancel":
                  invoiceCreationCancel();
                break;
                
            case "lineCreationDone":
                  lineCreationDone();
                break;
                
            case "lineCreationCancel":
                  lineCreationCancel();
                break;
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedRow = frame.getInvoicetable().getSelectedRow();
        if (selectedRow != -1) {
            InvoiceHeader inv = frame.getInvoices().get(selectedRow);
            frame.getInvoiceNumberLabel5().setText(""+inv.getNumber());
            frame.getCustomerNameLabel6().setText(inv.getName());
            frame.getInvoiceDateLabel7().setText(salesInvoiceGeneratorFrame.sdf.format(inv.getDate()));
            frame.getInvoiceTotalLabel8().setText(""+inv.getTotal());
            
            
            Invoice_Line_Table_Model line_Table_Model = new Invoice_Line_Table_Model(inv.getLines());
            frame.getInvoiceItemsTable().setModel(line_Table_Model);
            frame.setLine_Table_Model(line_Table_Model);
            
            
            frame.getInvoiceItemsTable().setModel(new Invoice_Line_Table_Model(inv.getLines()));
        }
        /* else{
            frame.getInvoiceNumberLabel5().setText("");
            frame.getCustomerNameLabel6().setText("");
            frame.getInvoiceDateLabel7().setText("");
            frame.getInvoiceTotalLabel8().setText("");
            
            Invoice_Line_Table_Model line_Table_Model = new Invoice_Line_Table_Model();
            frame.getInvoiceItemsTable().setModel(line_Table_Model);
            frame.setLine_Table_Model(line_Table_Model);
            
            } */
    }

    private void createInvoice() {
        
        invoice_dialog = new Invoice_Dialog(frame);
        invoice_dialog.setVisible(true);
    }

    private void deleteInvoice() {
        
        
       int selectedInvoice = frame.getInvoicetable().getSelectedRow();
       if ( selectedInvoice != -1 ) {
           frame.getInvoices().remove(selectedInvoice);
           frame.getHeader_Table_Model().fireTableDataChanged();
           //frame.getInvoicetable().setRowSelectionInterval(selectedInvoice, selectedInvoice);
        }
        
    }

    private void addItem() {
              
        if ( frame.getInvoicetable().getSelectedRow() != -1 ) {
            line_dialog = new Line_Dialog(frame);
            line_dialog.setVisible(true);
        }  
    }

    private void deleteItem() {
            
        int selectedItem = frame.getInvoiceItemsTable().getSelectedRow();
        int selectedInvoice = frame.getInvoicetable().getSelectedRow();
        
       
        if( selectedInvoice != -1 && selectedItem != -1 ){
            frame.getInvoices().get(selectedInvoice).getLines().remove(selectedItem);
            frame.getHeader_Table_Model().fireTableDataChanged();
            frame.getInvoicetable().setRowSelectionInterval(selectedItem, selectedItem);
            
        }else{
            System.out.println("ERROR OCCUR DURING DELETE PROCESS");
        }
       
        
        
    }

    public void addFile(String headrPath, String linePath) {
        File header_File = null;
        File line_File = null;
        if (headrPath == null && linePath == null) {
            JFileChooser fc = new JFileChooser();
            int result = fc.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                header_File = fc.getSelectedFile();
                result = fc.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    line_File = fc.getSelectedFile();
                }
            }
        } else {
            header_File = new File(headrPath);
            line_File = new File(linePath);
        }

        if (header_File != null && line_File != null) {
            try {
                
                // collection streams
                List<String> headerLines = Files.lines(Paths.get(header_File.getAbsolutePath())).collect(Collectors.toList());
                /*
                1,8/7/2020,Adam
                2,5/6/2022,Ilyas
                3,11/3/2022,Noah
                4,8/8/2022,Sofyan
                5,1/8/2022,Moaway
                6,3/7/2022,Yusuf
                7,6/7/2022,Yunis  */
                
                
                List<String> lineLines = Files.lines(Paths.get(line_File.getAbsolutePath())).collect(Collectors.toList());                
                /*
                1,mobile,5000,2
                1,airpods,2400,5
                2,ipad,6300,3
                4,Laptop,7000,2
                6,Airpods,2500,4
                3,Smart Watch,3500,2
                5,PSC,8000,1
                3,Smart Watch,3500,1
                7,Charger,500,4        */
                
                
                //ArrayList<Invoice> invoices = new ArrayList<>();
                frame.getInvoices().clear();
                for (String headerLine : headerLines) {
                    
                    String[] parts = headerLine.split(",");  
                    // "1,8/7/2020,Adam"  ==>  ["1", "08/07/2020", "Adam"]
                    
                    String numString = parts[0];
                    String dateString = parts[1];
                    String name = parts[2];
                    
                    int num = Integer.parseInt(numString);
                    Date date = frame.sdf.parse(dateString);
                    
                    InvoiceHeader inv = new InvoiceHeader(num, date, name);
                    
                    //invoices.add(inv);
                    frame.getInvoices().add(inv);
                }
                System.out.println("Invoices headers Added");
                
                for (String lineLine : lineLines) {
                    
                    // lineLine = "1,mobile,5000,2"
                    
                    String[] parts = lineLine.split(",");
                    // parts = ["1", "mobile", "5000", "2"]
                     
                    
                    int num = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    double price = Double.parseDouble(parts[2]);
                    int count = Integer.parseInt(parts[3]);
                    InvoiceHeader inv = frame.getInvoiceByNumber(num);
                    InvoiceLine line = new InvoiceLine(name, price, count, inv);
                    inv.getLines().add(line);
                }
                System.out.println("Invoices lines added");
                frame.setHeader_Table_Model(new Invoice_Header_Table_Model(frame.getInvoices()));
                frame.getInvoicetable().setModel(frame.getHeader_Table_Model());
            } catch (Exception ex) {
            }
        }
    }

    private void saveData() {
        
        JFileChooser jfc = new JFileChooser();
        int result = jfc.showSaveDialog(frame);
        
        File header_File = null;
        File line_File = null;
        
        if ( result == JFileChooser.APPROVE_OPTION){
            header_File = jfc.getSelectedFile();
            result = jfc.showSaveDialog(frame);
            
            if( result == JFileChooser.APPROVE_OPTION){
                line_File = jfc.getSelectedFile();
                
                if ( header_File != null && line_File != null){
                String header_Data = "";
                String line_Data = "";
                
                for (InvoiceHeader header : frame.getInvoices()) {
                    header_Data += header.getAsCSV();
                    //header_Data += header.getAsCSV();
                    header_Data += "\n";
                    
                    for ( InvoiceLine line : header.getLines()){
                        line_Data += line.getAsCSV();
                        line_Data += "\n";
                    }
                }
                
                header_Data = header_Data.substring(0, header_Data.length()-1);
                line_Data = line_Data.substring(0, line_Data.length()-1);
                
                try {
                    FileWriter header_fw = new FileWriter(header_File);
                    FileWriter line_fw = new FileWriter(line_File);
                    
                    header_fw.write(header_Data);
                    line_fw.write(line_Data);
                    
                    header_fw.flush();
                    line_fw.flush();
                    
                    header_fw.close();
                    line_fw.close();
                    
                } 
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            }
        }
    }

    
    private void invoiceCreationDone() {
        String dateStr = invoice_dialog.getInvoiceDateField().getText();
        String name = invoice_dialog.getCustomerNameField().getText();
        
        invoice_dialog.setVisible(false);
        invoice_dialog.dispose();
        invoice_dialog = null;
        try {
            Date date = salesInvoiceGeneratorFrame.sdf.parse(dateStr);
            int num = frame.getNextInvoiceNumber();
            InvoiceHeader header = new InvoiceHeader(num, date, name);
            
            frame.getInvoices().add(header);
            frame.getHeader_Table_Model().fireTableDataChanged();
        } 
        
        catch (ParseException ex) {
            JOptionPane.showMessageDialog(frame, "Error in Date format", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void invoiceCreationCancel() {
        
        invoice_dialog.setVisible(false);
        invoice_dialog.dispose();
        invoice_dialog = null;
    }

    private void lineCreationDone() {
        
        int selectedInvoiceIndex = frame.getInvoicetable().getSelectedRow();
        InvoiceHeader invoice = frame.getInvoices().get(selectedInvoiceIndex);
        
        String name = line_dialog.getItemNameField().getText();
        String string_count = line_dialog.getItemCountField().getText();
        String string_price = line_dialog.getItemPriceField().getText();
        
        lineCreationCancel();
               
        int count = Integer.parseInt(string_count);
        double price = Double.parseDouble(string_price);
        
        InvoiceLine line = new InvoiceLine(name, price, count, invoice);
        invoice.getLines().add(line);
        frame.getLine_Table_Model().fireTableDataChanged();
        
        //((AbstractTableModel)frame.getInvoiceItemsTable().getModel()).fireTableDataChanged();
        
        frame.getHeader_Table_Model().fireTableDataChanged();
        frame.getInvoicetable().setRowSelectionInterval(selectedInvoiceIndex, selectedInvoiceIndex);
        
        
        
    }

    private void lineCreationCancel() {
        
        line_dialog.setVisible(false);
        line_dialog.dispose();
        line_dialog = null;
    }
   
}

