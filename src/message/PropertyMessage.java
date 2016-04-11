package message;

import monopoly.Player;
import monopoly.cell.PropertyCell;

/**
 * Created by Mengxiao Lin on 2016/4/11.
 */
public abstract class PropertyMessage implements Message{
    private Player player;
    private PropertyCell cell;
    private double money;
    public enum ActionType{
        BUYING, PASSING, LEVEL_UP
    };
    ActionType actionType;
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public PropertyCell getCell() {
        return cell;
    }

    public void setCell(PropertyCell cell) {
        this.cell = cell;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    @Override
    public abstract void action() ;

}
