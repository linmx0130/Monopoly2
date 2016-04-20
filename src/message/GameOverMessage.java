package message;

import monopoly.Player;

/**
 * Created by Mengxiao Lin on 2016/4/20.
 */
public abstract class GameOverMessage implements Message {
    private Player winner;

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }
}
