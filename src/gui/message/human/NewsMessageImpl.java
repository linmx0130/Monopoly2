package gui.message.human;

import message.NewsMessage;

import javax.swing.*;

/**
 * Created by Mengxiao Lin on 2016/6/13.
 */
public class NewsMessageImpl extends NewsMessage {
    @Override
    public void action() {
        JOptionPane.showConfirmDialog(null, getDescription(), "新闻点",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
    }
}
