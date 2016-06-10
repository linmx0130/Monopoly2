package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Mengxiao Lin on 2016/6/10.
 */
public class FormItemTextField extends JPanel {
    private JTextField textField;
    public FormItemTextField(String label){
        setLayout(new BorderLayout());
        textField = new JTextField();
        add(new JLabel(label), BorderLayout.WEST);
        add(textField);
    }

    public JTextField getTextField() {
        return textField;
    }
    public void setText(String text){
        textField.setText(text);
    }
    @Override
    public void setEnabled(boolean f){
        textField.setEnabled(f);
        textField.setDisabledTextColor(Color.BLACK);
    }
}
