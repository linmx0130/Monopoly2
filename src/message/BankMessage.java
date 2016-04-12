package message;

import monopoly.Player;

/**
 * Created by Mengxiao Lin on 2016/4/12.
 */
public abstract class BankMessage implements Message {
    private Player player;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
