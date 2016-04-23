package ui.message;

import message.LotteryAlertMessage;

/**
 * Created by Mengxiao Lin on 2016/4/24.
 */
public class LotteryAlertMessageImpl extends LotteryAlertMessage {
    @Override
    public void action() {
        if (isHit()){
            System.out.println("======有人中奖啦======");
            System.out.println(getDescription());
            System.out.println("=====================");
        }else{
            System.out.println("======彩票======");
            System.out.println(getDescription());
            System.out.println("================");
        }
    }
}
