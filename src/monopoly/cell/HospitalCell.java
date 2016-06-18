package monopoly.cell;

import message.FaultMessage;
import monopoly.Kernel;
import monopoly.Player;

/**
 * Created by Mengxiao Lin on 2016/6/17.
 */
public class HospitalCell extends AbstractCell {
    public HospitalCell(int id) {
        super(id, "医院", "受伤的玩家接受治疗的地方。");
    }

    @Override
    public void arrivedEffect(Player player) {
        FaultMessage faultMessage = (FaultMessage) Kernel.getInstance().getMessageFactory().createMessage("FaultMessage");
        faultMessage.setDescription("玩家"+player.getName()+"受伤进入医院，停留两回合！");
        player.setPauseTurn(2);
        player.setOrientation(-player.getOrientation());
        Kernel.getInstance().getMessagePipe().onMessageArrived(faultMessage);
    }
}
