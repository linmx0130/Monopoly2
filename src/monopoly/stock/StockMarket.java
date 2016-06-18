package monopoly.stock;

import javafx.util.Pair;
import message.FaultMessage;
import message.SuccessMessage;
import monopoly.Kernel;
import monopoly.Player;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Mengxiao Lin on 2016/4/21.
 */
public class StockMarket {
    private ArrayList<Stock> stocks;
    private HashMap<Player, HashMap<Stock, Integer>> stockHold;
    public StockMarket(){
        stocks = new ArrayList<>();
        stockHold = new HashMap<>();
    }
    public void addStock(Stock stock){
        stocks.add(stock);
    }
    public List<Stock> getStocks(){
        return stocks;
    }
    public void randomFloat(){
        stocks.forEach(s -> s.randomChange());
    }
    public void loadInitFromStream(InputStream is){
        Scanner reader = new Scanner(is);
        int count = reader.nextInt();
        for (int i=0;i<count;++i){
            int id=reader.nextInt();
            String name =reader.next();
            double p1 = reader.nextDouble(), p2= reader.nextDouble();
            Stock tmp = new Stock(id,name,p1);
            tmp.setPrice(p2);
            stocks.add(tmp);
        }
    }
    public boolean buyStock(Player player, int stockId, int amount){
        Stock stock=stocks.stream().filter(e -> e.getId() == stockId).findFirst().get();
        FaultMessage faultMsg = (FaultMessage) Kernel.getInstance().getMessageFactory().createMessage("FaultMessage");
        if (stock == null) {
            faultMsg.setDescription("该股票不存在！");
            Kernel.getInstance().getMessagePipe().onMessageArrived(faultMsg);
            return false;
        }
        double totalCost = stock.getPrice()*amount;
        if (player.charge(totalCost)){
            if (!stockHold.containsKey(player)){
                stockHold.put(player, new HashMap<>());
            }
            HashMap<Stock, Integer> holdMap = stockHold.get(player);
            if (!holdMap.containsKey(stock)){
                holdMap.put(stock, 0);
            }
            Integer d = holdMap.get(stock);
            d+=amount;
            holdMap.put(stock, d);
            SuccessMessage msg = (SuccessMessage) Kernel.getInstance().getMessageFactory().createMessage("SuccessMessage");
            msg.setDescription("成功购买股票 "+stock.getName()+" 共"+amount+"股！");
            Kernel.getInstance().getMessagePipe().onMessageArrived(msg);
            return true;
        }else{
            faultMsg.setDescription("现金不足，无法购买！");
            Kernel.getInstance().getMessagePipe().onMessageArrived(faultMsg);
            return false;
        }
    }
    public boolean sellStock(Player player, int stockId, int amount){
        Stock stock=stocks.stream().filter(e -> e.getId() == stockId).findFirst().get();
        FaultMessage faultMsg = (FaultMessage) Kernel.getInstance().getMessageFactory().createMessage("FaultMessage");
        if (stock == null) {
            faultMsg.setDescription("该股票不存在！");
            Kernel.getInstance().getMessagePipe().onMessageArrived(faultMsg);
            return false;
        }
        HashMap<Stock, Integer> holdMap = stockHold.get(player);
        if (holdMap == null) {
            faultMsg.setDescription("您没有持有该股票！");
            Kernel.getInstance().getMessagePipe().onMessageArrived(faultMsg);
            return false;
        }
        if (!holdMap.containsKey(stock)){
            faultMsg.setDescription("您没有持有该股票！");
            Kernel.getInstance().getMessagePipe().onMessageArrived(faultMsg);
            return false;
        }
        Integer holdAmount = holdMap.get(stock);
        if (holdAmount< amount) {
            faultMsg.setDescription("您只持有"+ holdAmount+"股，无法卖出！");
            Kernel.getInstance().getMessagePipe().onMessageArrived(faultMsg);
            return false;
        }
        holdAmount -= amount;
        holdMap.put(stock, holdAmount);
        player.income(stock.getPrice()*amount);
        SuccessMessage msg = (SuccessMessage) Kernel.getInstance().getMessageFactory().createMessage("SuccessMessage");
        msg.setDescription("成功卖出股票 "+stock.getName()+" ，共"+amount+"股！");
        Kernel.getInstance().getMessagePipe().onMessageArrived(msg);
        return true;
    }
    public HashMap<Stock, Integer> getStockHold(Player player){
        if (!stockHold.containsKey(player)){
            stockHold.put(player,new HashMap<>());
        }
        return stockHold.get(player);
    }
    public double getTotalHold(Player player){
        if (! stockHold.containsKey(player)) return 0;
        return stockHold.get(player).entrySet().stream()
                .map(entry-> entry.getKey().getPrice()* entry.getValue().doubleValue())
                .reduce(0.0, (a,b) ->a+b);
    }
}
