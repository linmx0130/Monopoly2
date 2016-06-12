package gui.message.human;

import gui.LotteryDialog;
import message.LotteryQuestion;

/**
 * Created by Mengxiao Lin on 2016/6/12.
 */
public class LotteryQuestionImpl extends LotteryQuestion {
    @Override
    public void action() {
        LotteryDialog dialog = new LotteryDialog(getPlayer());
        dialog.setVisible(true);
    }
}
