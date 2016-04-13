package message;

import monopoly.Player;

import java.util.HashMap;

/**
 * Created by Mengxiao Lin on 2016/4/13.
 */
public abstract class NewsMessage implements Message {
    private String description;
    private Player player;
    public interface NewsEffect{
        void action(NewsMessage message, Player player);
    }
    protected NewsEffect effect;

    public NewsMessage(){
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setEffect(NewsEffect effect) {
        this.effect = effect;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
