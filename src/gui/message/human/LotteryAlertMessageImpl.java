package gui.message.human;

import message.LotteryAlertMessage;

import javax.swing.*;

/**
 * Created by Mengxiao Lin on 2016/6/18.
 */
public class LotteryAlertMessageImpl extends LotteryAlertMessage {
    @Override
    public void action() {
        if (isHit()){
            JOptionPane.showConfirmDialog(null, getDescription(),"有人中奖啦！",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showConfirmDialog(null, getDescription(),"彩票月底开奖~",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
