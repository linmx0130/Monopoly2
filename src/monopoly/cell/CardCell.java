package monopoly.cell;

import message.SuccessMessage;
import monopoly.Kernel;
import monopoly.Player;
import monopoly.card.AbstractCard;

/**
 * Created by Mengxiao Lin on 2016/4/16.
 */
public class CardCell extends AbstractCell {
    public CardCell(int id) {
        super(id, "CardCell", "赠送各种道具卡。");
    }

    @Override
    public void arrivedEffect(Player player) {
        AbstractCard card = Kernel.getInstance().getCardFactory().generateRandomCard();
        player.addCard(card);
        SuccessMessage msg = (SuccessMessage) Kernel.getInstance().getMessageFactory().createMessage("SuccessMessage");
        msg.setDescription(player.getName()+"幸运地获得了"+card.getName()+"一张！");
        Kernel.getInstance().getMessagePipe().onMessageArrived(msg);
    }

}
