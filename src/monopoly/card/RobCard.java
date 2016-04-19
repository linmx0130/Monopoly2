package monopoly.card;

import message.FaultMessage;
import message.SuccessMessage;
import monopoly.Kernel;
import monopoly.Player;
import monopoly.cell.AbstractCell;

import java.util.Random;

/**
 * Created by Mengxiao Lin on 2016/4/19.
 */
public class RobCard extends AbstractCard {
    public RobCard() {
        this.name = "掠夺卡";
        this.description="随机将五步内的对手的一张卡牌据为己有，如果对方没有卡牌则什么都得不到。";
    }

    @Override
    public boolean useCard(Player subject, Player object) {
        this.subject = subject;
        this.object = object;
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
        if (object.getCards().size()==0){
            FaultMessage msg = (FaultMessage)Kernel.getInstance().getMessageFactory().createMessage("FaultMessage");
            msg.setDescription("玩家"+object.getName()+"没有道具卡，掠夺卡使用完毕，掠夺失败。");
            Kernel.getInstance().getMessagePipe().onMessageArrived(msg);
            return ;
        }
        int choose = (new Random()).nextInt(object.getCards().size());
        AbstractCard card = object.getCards().get(choose);
        object.getCards().remove(choose);
        subject.addCard(card);
        SuccessMessage msg = (SuccessMessage) Kernel.getInstance().getMessageFactory().createMessage("SuccessMessage");
        msg.setDescription("成功掠夺玩家"+object.getName()+"的道具卡"+card.getName()+"！");
        Kernel.getInstance().getMessagePipe().onMessageArrived(msg);
    }

    @Override
    public boolean canBeUse(Player subject, Player object) {
        if (subject==object) return false;
        return Kernel.getInstance().getGameMap().getDistanceBetweenPlayers(subject,object)<=5;
    }
}
