package message;

import monopoly.Player;

import java.util.Map;

/**
 * Created by Mengxiao Lin on 2016/4/17.
 */
public abstract class CardShopQuestion implements Question {
    private String cardName;
    private Player player;
    private Map<String, Integer> valueMap;
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public Map<String, Integer> getValueMap() {
        return valueMap;
    }

    public void setValueMap(Map<String, Integer> valueMap) {
        this.valueMap = valueMap;
    }
}
