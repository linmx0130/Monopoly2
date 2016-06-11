package gui.message.human;

import message.BankMessage;

import javax.swing.*;

/**
 * Created by Mengxiao Lin on 2016/6/11.
 */
public class BankMessageImpl extends BankMessage {
    @Override
    public void action() {
        //TODO: make the bank
        JOptionPane.showConfirmDialog(null, "BANK!","BANK!",JOptionPane.DEFAULT_OPTION);
    }
}
