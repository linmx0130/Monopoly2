package monopoly.cell;

import message.NewsMessage;
import monopoly.Bank;
import monopoly.Kernel;
import monopoly.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

/**
 * Created by Mengxiao Lin on 2016/4/13.
 */
public class NewsCell extends AbstractCell {
    public static ArrayList<NewsMessage.NewsEffect> effects = new ArrayList<>();
    public static ArrayList<String> effectDescriptions = new ArrayList<>();
    static {
        effects.add( (message, _e) -> {
            double maxPropertyValue = Kernel.getInstance().getPlayers().stream().map(
                    player -> player.getPropertyCells().stream().map(
                            cell -> cell.getBuyingPrice()
                    ).reduce(0.0, (a,b) -> a+b)
            ).reduce(0.0, (a,b)-> Double.max(a,b));
            Stream<Player> riches = Kernel.getInstance().getPlayers().stream().filter(
                    player-> player.getPropertyCells().stream().map(
                            cell -> cell.getBuyingPrice()
                    ).reduce(0.0,(a,b)->a+b) == maxPropertyValue
            );
            ArrayList<String> richesName =new ArrayList<>();

            StringBuffer buffer=new StringBuffer("公开表扬第一地主");
            riches.forEach(player -> {
                richesName.add(player.getName());
                player.setMoney(player.getMoney()+ maxPropertyValue * 0.1);
                buffer.append(player.getName()+"，");
            });
            buffer.append("奖励");
            buffer.append(maxPropertyValue);
            buffer.append("元！");
            message.setDescription(buffer.toString());
        });
        effectDescriptions.add("公开表扬第一地主xx，奖励yy元！");
        effects.add( (_message, _player) -> {
            Kernel.getInstance().getPlayers().stream().forEach(player -> {
                Bank bank=Kernel.getInstance().getBank();
                bank.modifyMoney(player, -bank.getDeposit(player)*0.1);
            });
        });
        effectDescriptions.add("所有人缴纳财产税10%：存款扣除10%！");
    }
    public NewsCell(int id){
        super(id, "新闻点", "专门负责搞大新闻。");
    }
    @Override
    public void arrivedEffect(Player player) {
        NewsMessage newsMessage = (NewsMessage) Kernel.getInstance().getMessageFactory().createMessage("NewsMessage");
        int effectId = (new Random()).nextInt(effects.size());
        newsMessage.setEffect(effects.get(effectId));
        newsMessage.setDescription(effectDescriptions.get(effectId));
        newsMessage.setPlayer(player);
        Kernel.getInstance().getMessagePipe().onMessageArrived(newsMessage);
    }
}
