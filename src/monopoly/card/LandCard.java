package monopoly.card;

import message.SuccessMessage;
import monopoly.Kernel;
import monopoly.Player;
import monopoly.cell.PropertyCell;

/**
 * Created by Mengxiao Lin on 2016/4/16.
 */
public class LandCard extends AbstractCard {
    private int leftTurn;
    private Player ownerBefore;
    private PropertyCell land ;

    @Override
    public void useCard(Player subject, Player object) {
        this.subject = subject;
        land = (PropertyCell)Kernel.getInstance().getGameMap().getPlayerPosition(subject);
        ownerBefore = land.getOwner();
        land.setOwner(subject);
        SuccessMessage msg = (SuccessMessage)Kernel.getInstance().getMessageFactory().createMessage("SuccessMessage");
        msg.setDescription(subject.getName()+"发动了土地卡，霸占了"+ land.getName()+"，维持5个回合");
        Kernel.getInstance().getMessagePipe().onMessageArrived(msg);
        this.leftTurn = 5;
    }

    @Override
    public boolean isLongTermCard() {
        return true;
    }

    @Override
    public boolean needObject() {
        return false;
    }

    @Override
    public void cardEffect() {
        if (--leftTurn == 0){
            land.setOwner(ownerBefore);
        }
    }

}
