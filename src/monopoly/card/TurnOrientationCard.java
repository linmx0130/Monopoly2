package monopoly.card;

import monopoly.Kernel;
import monopoly.Player;

/**
 * Created by Mengxiao Lin on 2016/4/13.
 */
public class TurnOrientationCard extends AbstractCard {
    public TurnOrientationCard() {
        name = "转向卡";
        description="使得目标调转方向，只能对5步内的玩家或者自己使用。";
    }

    @Override
    public boolean useCard(Player subject, Player object) {
        this.subject= subject;
        this.object = object;
        if (Kernel.getInstance().getGameMap().getDistanceBetweenPlayers(subject,object) >5) {
            throw new RuntimeException("Error use TurnOrientationCard!");
        }
        return true;
    }

    @Override
    public boolean isLongTermCard() {
        return false;
    }

    @Override
    public boolean needObject() {
        return true;
    }

    @Override
    public void cardEffect() {
        object.setOrientation(-object.getOrientation());
    }

    @Override
    public boolean canBeUse(Player subject, Player object) {
        return (Kernel.getInstance().getGameMap().getDistanceBetweenPlayers(subject,object) <= 5);
    }
}
