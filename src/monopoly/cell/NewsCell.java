package monopoly.cell;

import message.NewsMessage;
import monopoly.Bank;
import monopoly.Kernel;
import monopoly.Player;
import monopoly.card.CardFactory;

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
        //New 1
        effects.add( (message, _e) -> {
            int maxPropertyCount = Kernel.getInstance().getPlayers().stream().map(
                    p->p.getPropertyCells().size()
                ).reduce(0, (a,b) -> Integer.max(a,b));
            Stream<Player> riches = Kernel.getInstance().getPlayers().stream().filter(
                    player-> player.getPropertyCells().size() == maxPropertyCount
            );
            double maxPropertyValue = Kernel.getInstance().getPlayers().stream().map(
                    player -> player.getPropertyCells().stream().map(
                            cell -> cell.getBuyingPrice()
                    ).reduce(0.0, (a,b) -> a+b)
            ).reduce(0.0, (a,b)-> Double.max(a,b));

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

        //News 2
        effects.add( (_message, _player) -> {
            Kernel.getInstance().getPlayers().stream().forEach(player -> {
                Bank bank=Kernel.getInstance().getBank();
                bank.modifyMoney(player, -bank.getDeposit(player)*0.1);
            });
        });
        effectDescriptions.add("所有人缴纳财产税10%：存款扣除10%！");

        //News 3
        effects.add( (_message, _player)->{
            double maxPropertyValue = Kernel.getInstance().getPlayers().stream().map(
                    player -> player.getPropertyCells().stream().map(
                            cell -> cell.getBuyingPrice()
                    ).reduce(0.0, (a,b) -> a+b)
            ).reduce(0.0, (a,b)-> Double.max(a,b));
            int minPropertyCount = Kernel.getInstance().getPlayers().stream().map(
                    player-> player.getPropertyCells().size()
            ).reduce(Integer.MAX_VALUE, (a,b) -> Integer.min(a,b));
            Stream<Player> poorest = Kernel.getInstance().getPlayers().stream()
                    .filter(player -> player.getPropertyCells().size() == minPropertyCount);
            ArrayList<String> poorestNames = new ArrayList<>();
            StringBuffer buffer = new StringBuffer("公开补助土地最少者");
            poorest.forEach(player -> {
                poorestNames.add(player.getName());
                player.setMoney(player.getMoney()+maxPropertyValue * 0.1);
                buffer.append(player.getName()+"，");
            });
            buffer.append(String.valueOf(maxPropertyValue*0.1));
            buffer.append("元");
            _message.setDescription(buffer.toString());
        });
        effectDescriptions.add("公开补助土地最少者xx，xxx元");

        //News 4
        effects.add( (_message, _player) -> Kernel.getInstance().getPlayers().stream().forEach(player -> {
            Bank bank=Kernel.getInstance().getBank();
            bank.modifyMoney(player, bank.getDeposit(player)*0.1);
        }));
        effectDescriptions.add("银行加发储金红利，每个人得到存款10%！");

        //News 5
        effects.add( (message, _player) ->
            Kernel.getInstance().getPlayers().forEach(player ->
                player.addCard(Kernel.getInstance().getCardFactory().generateRandomCard())
            ));
        effectDescriptions.add("每个人得到一张卡片。");
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
