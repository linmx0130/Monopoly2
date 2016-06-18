package gui.message.human;

import message.FaultMessage;

import javax.swing.*;

/**
 * Created by Mengxiao Lin on 2016/6/18.
 */
public class FaultMessageImpl extends FaultMessage {
    @Override
    public void action() {
        JOptionPane.showConfirmDialog(null, getDescription(),"Monopoly 2", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
    }
}
