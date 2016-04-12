package monopoly.cell;

import message.BankMessage;
import monopoly.Kernel;
import monopoly.Player;

/**
 * Created by Mengxiao Lin on 2016/4/12.
 */
public class BankCell extends AbstractCell{
    public BankCell(int id){
        super(id, "Bank", "Bank");
    }
    @Override
    public void arrivedEffect(Player player) {
        BankMessage bankMessage = (BankMessage) Kernel.getInstance().getMessageFactory().createMessage("BankMessage");
        bankMessage.setPlayer(player);
        Kernel.getInstance().getMessagePipe().onMessageArrived(bankMessage);
    }
}
