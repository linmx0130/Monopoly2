package monopoly;

import message.LotteryAlertMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

/**
 * Created by Mengxiao Lin on 2016/4/24.
 */
public class LotterySystem {
    private HashMap<Player, ArrayList<Pair<Integer, Double>>> lotteryRecord;
    private double prizePool;
    public LotterySystem(){
        lotteryRecord = new HashMap<>();
    }
    private void clearRecord(){
        lotteryRecord.clear();
    }
    public void buyLottery(Player player, int choose, double value){
        if (!lotteryRecord.keySet().contains(player)){
            lotteryRecord.put(player, new ArrayList<>());
        }
        ArrayList<Pair<Integer, Double>> targetList = lotteryRecord.get(player);
        targetList.add(new Pair<>(choose, value));
    }
    public void addPrizePool(double value){
        prizePool += value;
    }
    public void drawLottery(int choose){
        final double totalSold = lotteryRecord.entrySet().stream().map(entry-> entry.getValue())
                .map(list ->
                   list.stream().map(item -> item.getSecond()).reduce(0.0, (a, b) -> a + b)
                ).reduce(0.0, (a,b)->a+b);
        prizePool+= totalSold;
        final double totalHit = lotteryRecord.entrySet().stream().map(entry->entry.getValue())
                .map(list->
                    list.stream()
                            .filter(item ->item.getFirst().intValue()==choose)
                            .map(item-> item.getSecond())
                            .reduce(0.0, (a,b) -> a+b)
                ).reduce(0.0, (a,b) ->a+b);
        if (totalHit == 0.0){
            //no one hit
            LotteryAlertMessage msg = (LotteryAlertMessage) Kernel.getInstance().getMessageFactory().createMessage("LotteryAlertMessage");
            msg.setHit(false);
            msg.setChoose(choose);
            msg.setDescription("本次开奖中奖号码是"+choose+"，无人中奖，奖池累积达到"+prizePool+"，大家下次好运吧！");
            Kernel.getInstance().getMessagePipe().onMessageArrived(msg);
        } else{
            lotteryRecord.entrySet().stream().forEach( entry->{
                Player player = entry.getKey();
                ArrayList<Pair<Integer,Double>> list = entry.getValue();
                double hitCount = list.stream().filter(e -> e.getFirst()==choose)
                        .map(item->item.getSecond()).reduce(0.0, (a,b)->a+b);
                if (hitCount != 0 ){
                    player.income(hitCount/totalSold*prizePool);
                }
            });
            prizePool = 0;
            LotteryAlertMessage msg = (LotteryAlertMessage) Kernel.getInstance().getMessageFactory().createMessage("LotteryAlertMessage");
            msg.setHit(true);
            msg.setChoose(choose);
            msg.setDescription("本次开奖中奖号码是"+choose+"，无人中奖，奖金已经发放，恭喜获奖者！");
            Kernel.getInstance().getMessagePipe().onMessageArrived(msg);
        }
    }
}