package ui.message;

import message.GameOverMessage;

import java.util.Scanner;

/**
 * Created by Mengxiao Lin on 2016/4/20.
 */
public class GameOverMessageImpl extends GameOverMessage {
    @Override
    public void action() {
        System.out.println("恭喜玩家"+getWinner().getName()+"最终取得了胜利！游戏结束！");
        Scanner cin = new Scanner (System.in);
        cin.nextLine();
        System.exit(0);
    }
}
