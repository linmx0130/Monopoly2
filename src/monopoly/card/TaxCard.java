package monopoly.card;

import message.FaultMessage;
import message.SuccessMessage;
import monopoly.Kernel;
import monopoly.Player;

import java.util.Random;

/**
 * Created by Mengxiao Lin on 2016/4/19.
 */
public class TaxCard extends AbstractCard {
    public TaxCard() {
        this.name = "查税卡";
        this.description="随机对五步内的一位对手进行查税，使其30%的存款交税。";
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
        double value = Kernel.getInstance().getBank().getDeposit(object);
        value *= 0.3;
        Kernel.getInstance().getBank().modifyMoney(object, -value);
        SuccessMessage msg = (SuccessMessage) Kernel.getInstance().getMessageFactory().createMessage("SuccessMessage");
        msg.setDescription("成功对玩家"+object.getName()+"查税，政府收取了他存款的30%，共"+value+"元！");
        Kernel.getInstance().getMessagePipe().onMessageArrived(msg);
    }

    @Override
    public boolean canBeUse(Player subject, Player object) {
        if (subject==object) return false;
        return Kernel.getInstance().getGameMap().getDistanceBetweenPlayers(subject,object)<=5;
    }
}
