package gui.message.human;

import gui.BankDialog;
import message.BankMessage;

import javax.swing.*;

/**
 * Created by Mengxiao Lin on 2016/6/11.
 */
public class BankMessageImpl extends BankMessage {
    @Override
    public void action() {
        BankDialog bankDialog = new BankDialog(getPlayer());
        bankDialog.setVisible(true);
    }
}
