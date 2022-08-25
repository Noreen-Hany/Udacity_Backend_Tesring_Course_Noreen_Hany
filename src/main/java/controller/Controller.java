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
        if (selectedRow >= 0) {
            InvoiceHeader inv = frame.getInvoices().get(selectedRow);
            frame.getInvoiceNumberLabel5().setText(""+inv.getNumber());
            frame.getCustomerNameLabel6().setText(inv.getName());
            frame.getInvoiceDateLabel7().setText(salesInvoiceGeneratorFrame.sdf.format(inv.getDate()));
            //frame.getInvoiceDateLabel7().setText(salesInvoiceGeneratorFrame.sdf.format(inv.getDate()));
            frame.getInvoiceTotalLabel8().setText(""+inv.getTotal());
            
            frame.getInvoiceItemsTable().setModel(new Invoice_Line_Table_Model(inv.getLines()));
        }
    }

    private void createInvoice() {
        invoice_dialog = new Invoice_Dialog(frame);
        invoice_dialog.setVisible(true);
    }

    private void deleteInvoice() {
    }

    private void addItem() {
        line_dialog = new Line_Dialog(frame);
        line_dialog.setVisible(true);
    }

    private void deleteItem() {
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
                /*
                1,8/7/2020,Adam
                2,5/6/2022,Ilyas
                3,11/3/2022,Noah
                4,8/8/2022,Sofyan
                5,1/8/2022,Moaway
                6,3/7/2022,Yusuf
                7,6/7/2022,Yunis  */
                
                // collection streams
                List<String> headerLines = Files.lines(Paths.get(header_File.getAbsolutePath())).collect(Collectors.toList());
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
                
                List<String> lineLines = Files.lines(Paths.get(line_File.getAbsolutePath())).collect(Collectors.toList());
                
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
                System.out.println("Check point");
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
                System.out.println("Check point");
                frame.setHeader_Table_Model(new Invoice_Header_Table_Model(frame.getInvoices()));
                frame.getInvoicetable().setModel(frame.getHeader_Table_Model());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void saveData() {
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
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(frame, "Error in Date format", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void invoiceCreationCancel() {
        invoice_dialog.setVisible(false);
        invoice_dialog.dispose();
        invoice_dialog = null;
    }

    private void lineCreationDone() {
        
    }

    private void lineCreationCancel() {
        line_dialog.setVisible(false);
        line_dialog.dispose();
        line_dialog = null;
    }
   
}

/*

                
                for (String lineLine : lineLines) {
                    // 
                    
                    String[] parts = lineLine.split(",");
                    
                    //
                     
                    int num = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    double price = Double.parseDouble(parts[2]);
                    int count = Integer.parseInt(parts[3]);
                    
                    InvoiceHeader inv = frame.getInvoiceByNum(num);
                    InvoiceLine line = new InvoiceLine(name, price, count, inv);
                    inv.getLines().add(line);
                }
                System.out.println("Check point");
                
                frame.setHeader_Table_Model(new Invoice_Header_Table_Model(frame.getInvoices()));
                frame.getInvoiceItemsTable().setModel(frame.getHeader_Table_Model());
            } 
                catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void deleteItem() {
        throw new UnsupportedOperationException("Not supported yet."); 
        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void addItem() {
        throw new UnsupportedOperationException("Not supported yet."); 
        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        
        line_dialog = new Line_Dialog(frame);
        line_dialog.setVisible(true);
        
    }
   
    private void deleteInvoice() {
        throw new UnsupportedOperationException("Not supported yet."); 
        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void createInvoice() {
        throw new UnsupportedOperationException("Not supported yet."); 
        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        
        invoice_dialog = new Invoice_Dialog(frame);
        invoice_dialog.setVisible(true);
        
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
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(frame, "Error in Date format", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void invoiceCreationCancel() {
        invoice_dialog.setVisible(false);
        invoice_dialog.dispose();
        invoice_dialog = null;
    }
    
    
    private void lineCreationDone() {
        
    }

    private void lineCreationCancel() {
        line_dialog.setVisible(false);
        line_dialog.dispose();
        line_dialog = null;
    }
*/
