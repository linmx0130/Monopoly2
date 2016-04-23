package message;

import monopoly.Player;

/**
 * Created by Mengxiao Lin on 2016/4/24.
 */
public abstract class LotteryQuestion implements Question {
    private Player player;
    private int choose;
    private double value;
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getChoose() {
        return choose;
    }

    public void setChoose(int choose) {
        this.choose = choose;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
