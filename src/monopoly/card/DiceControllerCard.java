package monopoly.card;

import message.DiceControllerQuestion;
import message.SuccessMessage;
import monopoly.Kernel;
import monopoly.Player;

/**
 * Created by Mengxiao Lin on 2016/4/21.
 */
public class DiceControllerCard extends AbstractCard {
    public DiceControllerCard() {
        this.name = "遥控骰子";
        this.description="控制骰子下一次的结果。";
    }

    @Override
    public boolean useCard(Player subject, Player object) {
        DiceControllerQuestion question = (DiceControllerQuestion) Kernel.getInstance().getMessageFactory().createMessage("DiceControllerQuestion");
        Kernel.getInstance().getMessagePipe().onMessageArrived(question);
        if (question.getChoose() == null) return false;
        Kernel.getInstance().setNextDiceValue(question.getChoose().intValue());
        SuccessMessage msg = (SuccessMessage) Kernel.getInstance().getMessageFactory().createMessage("SuccessMessage");
        msg.setDescription("玩家"+subject.getName()+"发动了骰子控制卡，下一次骰子结果为"+question.getChoose());
        Kernel.getInstance().getMessagePipe().onMessageArrived(msg);
        return true;
    }

    @Override
    public boolean isLongTermCard() {
        return false;
    }

    @Override
    public boolean needObject() {
        return false;
    }

    @Override
    public void cardEffect() {

    }

    @Override
    public boolean canBeUse(Player subject, Player object) {
        return true;
    }
}
