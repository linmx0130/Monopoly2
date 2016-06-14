package gui.message.human;

import message.GameOverMessage;

import javax.swing.*;

/**
 * Created by Mengxiao Lin on 2016/6/14.
 */
public class GameOverMessageImpl extends GameOverMessage {
    @Override
    public void action() {
        JOptionPane.showConfirmDialog(null,
                "恭喜玩家"+getWinner().getName()+"最终取得了胜利！游戏结束！","Monopoly 2",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
        //TODO: close GameFrame
    }
}
