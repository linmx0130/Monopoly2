package monopoly.card;

import message.RoadBlockCardQuestion;
import message.SuccessMessage;
import monopoly.Kernel;
import monopoly.Player;
import monopoly.cell.AbstractCell;

/**
 * Created by Mengxiao Lin on 2016/4/17.
 */
public class RoadBlockCard extends AbstractCard {
    private int leftTurn;
    private AbstractCell objectCell;

    public RoadBlockCard() {
        this.name = "路障卡";
        this.description="可以在前后8步之内安放一个路障，任意玩家经过路障时会停在路障所在位置不能前行。";
    }

    @Override
    public boolean useCard(Player subject, Player object) {
        this.subject =subject;
        RoadBlockCardQuestion question = (RoadBlockCardQuestion)Kernel.getInstance().getMessageFactory().createMessage("RoadBlockCardQuestion");
        question.setSubject(subject);
        Kernel.getInstance().getMessagePipe().onMessageArrived(question);
        objectCell = Kernel.getInstance().getGameMap().getPlayerPosition(subject);
        if (Math.abs(question.getChoose())> 8) return false;
        if (question.getChoose() < 0){
            for (int choose = -question.getChoose(); choose >0; --choose){
                objectCell = objectCell.getPreviousCell();
            }
        }else{
            for (int choose = question.getChoose(); choose >0; --choose){
                objectCell = objectCell.getNextCell();
            }
        }
        objectCell.addARoadBlock();
        leftTurn = 8;
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
    public void cardEffect() {
        if (--leftTurn == 0){
            objectCell.removeARoadBlock();
            SuccessMessage msg = (SuccessMessage) Kernel.getInstance().getMessageFactory().createMessage("SuccessMessage");
            msg.setDescription(subject.getName()+"发动的路障卡失效，"+objectCell.getName()+"路障减少了一个。");
            Kernel.getInstance().getMessagePipe().onMessageArrived(msg);
        }
    }

    @Override
    public boolean isEffectFinished() {
        return leftTurn <= 0;
    }

    @Override
    public boolean canBeUse(Player subject, Player object) {
        return true;
    }
}
