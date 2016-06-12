package gui.message.human;

import message.SuccessMessage;

import javax.swing.*;

/**
 * Created by Mengxiao Lin on 2016/6/12.
 */
public class SuccessMessageImpl extends SuccessMessage {
    @Override
    public void action() {
        JOptionPane.showConfirmDialog(null, getDescription(), "Monopoly 2", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
    }
}
