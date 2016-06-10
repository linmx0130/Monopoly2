package monopoly.cell;

import message.BankMessage;
import monopoly.Kernel;
import monopoly.Player;

/**
 * Created by Mengxiao Lin on 2016/4/12.
 */
public class BankCell extends AbstractCell{
    public BankCell(int id){
        super(id, "银行", "闷声发财的人存钱的地方。");
    }
    @Override
    public void arrivedEffect(Player player) {
        BankMessage bankMessage = (BankMessage) Kernel.getInstance().getMessageFactory().createMessage("BankMessage");
        bankMessage.setPlayer(player);
        Kernel.getInstance().getMessagePipe().onMessageArrived(bankMessage);
    }

    @Override
    public void moveOverEffect(Player player) {
        arrivedEffect(player);
    }
}
