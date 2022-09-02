/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author HP
 */
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.GridLayout;
import javax.swing.JDialog;


public class Line_Dialog extends JDialog {
    
    private JTextField itemNameField;
    private JTextField itemPriceField;
    private JTextField itemCountField;
    
    private JLabel itemNameLbl;
     private JLabel itemPriceLbl;
    private JLabel itemCountLbl;
   
    private JButton DoneBtn;
    private JButton CancelBtn;
    
    
    
    public Line_Dialog(salesInvoiceGeneratorFrame frame) {
        super(frame);
        itemNameField = new JTextField(25);
        itemNameLbl = new JLabel("Item Name:");
        
        itemPriceField = new JTextField(25);
        itemPriceLbl = new JLabel("Item Price:");
        
        itemCountField = new JTextField(25);
        itemCountLbl = new JLabel("Item Count:");
        
        DoneBtn = new JButton("Done");
        CancelBtn = new JButton("Cancel");
        
        DoneBtn.setActionCommand("lineCreationDone");
        CancelBtn.setActionCommand("lineCreationCancel");
        
        DoneBtn.addActionListener(frame.getController());
        CancelBtn.addActionListener(frame.getController());
        
        setLayout(new GridLayout(4,2));
        
        add(itemNameLbl);
        add(itemNameField);
        
        add(itemCountLbl);
        add(itemCountField);
        
        add(itemPriceLbl);
        add(itemPriceField);
        
        add(DoneBtn);
        add(CancelBtn);
        
        setModal(true);
        pack();
    }

    public JTextField getItemNameField() {
        return itemNameField;
    }

    public JTextField getItemCountField() {
        return itemCountField;
    }

    public JTextField getItemPriceField() {
        return itemPriceField;
    }
    
    
}
