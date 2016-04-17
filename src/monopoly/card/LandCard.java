package monopoly.card;

import message.SuccessMessage;
import monopoly.Kernel;
import monopoly.Player;
import monopoly.cell.AbstractCell;
import monopoly.cell.PropertyCell;

/**
 * Created by Mengxiao Lin on 2016/4/16.
 */
public class LandCard extends AbstractCard {
    private int leftTurn;
    private Player ownerBefore;
    private PropertyCell land ;

    public LandCard() {
        this.name="土地卡";
        this.description = "可以霸占别人或者尚无主人的地产，持续5个回合，获得这段时间内这块地的收入。";
    }

    @Override
    public boolean useCard(Player subject, Player object) {
        this.subject = subject;
        land = (PropertyCell)Kernel.getInstance().getGameMap().getPlayerPosition(subject);
        ownerBefore = land.getOwner();
        land.setOwner(subject);
        SuccessMessage msg = (SuccessMessage)Kernel.getInstance().getMessageFactory().createMessage("SuccessMessage");
        msg.setDescription(subject.getName()+"发动了土地卡，霸占了"+ land.getName()+"，维持5个回合");
        Kernel.getInstance().getMessagePipe().onMessageArrived(msg);
        this.leftTurn = 5;
        return true;
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
    public boolean isEffectFinished() {
        return leftTurn <= 0;
    }

    @Override
    public void cardEffect() {
        if (--leftTurn == 0){
            land.setOwner(ownerBefore);
            SuccessMessage msg = (SuccessMessage)Kernel.getInstance().getMessageFactory().createMessage("SuccessMessage");
            msg.setDescription(subject.getName()+"的土地卡失效！"+land.getName()+"物归原主。");
            Kernel.getInstance().getMessagePipe().onMessageArrived(msg);
        }
    }

    @Override
    public boolean canBeUse(Player subject, Player object) {
        AbstractCell land = Kernel.getInstance().getGameMap().getPlayerPosition(subject);
        return land instanceof PropertyCell;
    }
}
