package ui.message;

import message.LotteryQuestion;
import message.SuccessMessage;
import monopoly.Kernel;

import java.util.Scanner;

/**
 * Created by Mengxiao Lin on 2016/4/24.
 */
public class LotteryQuestionImpl extends LotteryQuestion {
    @Override
    public void action() {
        int choose =0;
        Scanner cin = new Scanner(System.in);
        boolean flag = true;
        do {
            System.out.println("请输入您想购买的彩票的号码，可以选择1~10，0放弃购买：");
            System.out.print(">> ");
            try {
                choose = cin.nextInt();
            } catch(Exception e){
                System.out.println("输入错误，请重新输入！");
                continue;
            }
            if (choose ==0 )return ;
            if (choose>=1 || choose <=10) flag =false;
            else{
                System.out.println("输入错误，请重新输入！");
            }
        }while (flag);
        double value= 0;
        flag = true;
        do {
            System.out.println("请输入您想下注的金额，0放弃购买：");
            System.out.print(">> ");
            try {
                value = cin.nextDouble();
            } catch(Exception e){
                System.out.println("输入错误，请重新输入！");
                continue;
            }
            if (value ==0 )return ;
            if (value > 0) {
                flag = false;
            }else{
                System.out.println("输入错误，请重新输入！");
            }
        }while (flag);
        if (getPlayer().charge(value)){
            Kernel.getInstance().getLottery().buyLottery(getPlayer(),choose ,value);
            SuccessMessage msg = (SuccessMessage) Kernel.getInstance().getMessageFactory().createMessage("SuccessMessage");
            msg.setDescription("成功购买彩票！号码为"+choose+"，下注额度为"+value);
            Kernel.getInstance().getMessagePipe().onMessageArrived(msg);
        }else{
            System.out.println("现金不足，无法购买！");
        }
    }
}
