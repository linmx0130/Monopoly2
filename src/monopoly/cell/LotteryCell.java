package monopoly.cell;

import message.LotteryQuestion;
import monopoly.Kernel;
import monopoly.Player;

/**
 * Created by Mengxiao Lin on 2016/4/24.
 */
public class LotteryCell extends AbstractCell{
    public LotteryCell(int id) {
        super(id, "彩票站", "出售彩票的地方");
    }

    @Override
    public void arrivedEffect(Player player) {
        LotteryQuestion question = (LotteryQuestion) Kernel.getInstance().getMessageFactory().createMessage("LotteryQuestion");
        question.setPlayer(player);
        Kernel.getInstance().getMessagePipe().onMessageArrived(question);
    }

    @Override
    public void moveOverEffect(Player player) {
        arrivedEffect(player);
    }
}
