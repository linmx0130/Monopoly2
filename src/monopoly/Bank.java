package monopoly;

import message.FaultMessage;
import message.SuccessMessage;
import monopoly.Kernel;
import monopoly.Player;

/**
 * Created by Mengxiao Lin on 2016/4/12.
 */
public class Bank {
    private double deposits[];
    public Bank(int userCount){
        deposits= new double[userCount];
    }
    public double getDeposit(Player player){
        return deposits[player.getId()-1];
    }
    public void storeMoney(Player player, double amount){
        if (player.charge(amount)){
            deposits[player.getId()-1] += amount;
            SuccessMessage successMessage = (SuccessMessage) Kernel.getInstance().getMessageFactory().createMessage("SuccessMessage");
            successMessage.setDescription("成功存款"+ amount+"元！现在 "+ player.getName()+" 存款为"+ deposits[player.getId()-1]+"元");
            Kernel.getInstance().getMessagePipe().onMessageArrived(successMessage);
        }else{
            FaultMessage faultMessage= (FaultMessage) Kernel.getInstance().getMessageFactory().createMessage("FaultMessage");
            faultMessage.setDescription("现金不足，无法存款！");
            Kernel.getInstance().getMessagePipe().onMessageArrived(faultMessage);
        }
    }
}
