/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author HP
 */

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JDialog;
import java.awt.GridLayout;




public class Invoice_Dialog extends JDialog {
    
    
    private JTextField customerNameField;
    private JTextField invoiceDateField;
    private JLabel customerNameLabel;
    private JLabel invoiceDateLabel;
    private JButton DoneBtn;
    private JButton CancelBtn;

    public Invoice_Dialog(salesInvoiceGeneratorFrame frame) {
        super(frame);
        
        customerNameLabel = new JLabel("Customer Name:");
        customerNameField = new JTextField(20);
        
        invoiceDateLabel = new JLabel("Invoice Date:");
        invoiceDateField = new JTextField(20);
        
        DoneBtn = new JButton("Done");
        CancelBtn = new JButton("Cancel");
        
        DoneBtn.setActionCommand("invoiceCreationDone");
        CancelBtn.setActionCommand("invoiceCreationCancel");
        
        DoneBtn.addActionListener(frame.getController());
        CancelBtn.addActionListener(frame.getController());
        setLayout(new GridLayout(4, 2));
        
        add(invoiceDateLabel);
        add(invoiceDateField);
        
        add(customerNameLabel);
        add(customerNameField);
        
        add(DoneBtn);
        add(CancelBtn);
        
        setModal(true);
        
        pack();
        
    }

    public JTextField getCustomerNameField() {
        return customerNameField;
    }

    public JTextField getInvoiceDateField() {
        return invoiceDateField;
    }
    
    
}
