package gui.message.human;

import message.YesOrNoQuestion;

import javax.swing.*;

/**
 * Created by Mengxiao Lin on 2016/6/11.
 */
public class YesOrNoQuestionImpl extends YesOrNoQuestion {
    public YesOrNoQuestionImpl() {
        super("");
    }

    @Override
    public void action() {
        int ret = JOptionPane.showConfirmDialog(null, this.getDescription(), "Monopoly 2", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        setAnswer(ret == JOptionPane.YES_OPTION);
    }
}
