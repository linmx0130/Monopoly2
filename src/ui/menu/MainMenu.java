package ui.menu;

import monopoly.Kernel;
import monopoly.Player;
import monopoly.card.AbstractCard;
import monopoly.cell.AbstractCell;
import ui.Util;
import ui.map.MapViewer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Mengxiao Lin on 2016/4/12.
 */
public class MainMenu {
    public static void showMainMenu(Player player){
        Kernel kernel = Kernel.getInstance();
        MapViewer mapViewer=new MapViewer(kernel.getGameMap());
        boolean waiting = true;
        do {
            System.out.println("============");
            System.out.println("玩家 " + player.getName() +" ，现在是第" + kernel.getCurrentDate() + "，请问你要做什么？");
            System.out.println(" 0 - 查看地图");
            System.out.println(" 1 - 查看原始地图");
            System.out.println(" 2 - 使用道具");
            System.out.println(" 3 - 前方10步内示警");
            System.out.println(" 4 - 查看前后指定步数的具体信息");
            System.out.println(" 5 - 查看所有玩家的资产信息");
            System.out.println(" 6 - 想看的都看了，心满意足地扔骰子前进！");
            System.out.println(" 7 - 进入股市投资赚钱！");
            System.out.println(" 8 - 认输。。。");
            int choose = Util.getIntFromScanner(new Scanner(System.in));
            switch (choose){
                case 0:
                    mapViewer.printMapWithRole();
                    break;
                case 1:
                    mapViewer.printMap();
                    break;
                case 2:
                    showCardMenu();
                    break;
                case 3:
                    roadBlockWarning();
                    break;
                case 4:
                    showCellInformation();
                    break;
                case 5:
                    showPlayerPropertyInformation();
                    break;
                case 6:
                    Random random=new Random();
                    int step = random.nextInt(6)+1;
                    if (Kernel.getInstance().getNextDiceValue()!= null){
                        step = Kernel.getInstance().getNextDiceValue();
                        Kernel.getInstance().setNextDiceValue(null);
                    }
                    System.out.println(" 您投出了"+step+"！");
                    for (int i=1 ;i<step ;++i) {
                        if (kernel.hasBlockOnNextPosition()) {
                            System.out.println(":( 遇到路障，停止前进！");
                            break;
                        }
                        kernel.playerMove();
                    }
                    kernel.playerMoveEnd();
                    kernel.nextPlayer();
                    waiting = false;
                    break;
                case 7:
                    StockMenu.show();
                    break;
                case 8:
                    kernel.forgiveGame();
                    kernel.nextPlayer();
                    waiting = false;
                    break;
                default:
            }
        }while (waiting);
    }
    private static void showPlayerPropertyInformation(){
        ArrayList<String[]> buffer = new ArrayList<>();
        String [] title= new String[8];
        title[0]="玩家名";
        title[1]="现金";
        title[2]="存款";
        title[3]="点券";
        title[4]="房产数量";
        title[5]="房产总额";
        title[6]="股票总额";
        title[7]="资产总额";
        buffer.add(title);
        Kernel.getInstance().getPlayers().stream().forEach(e->{
            String [] row =new String [8];
            row[0] = e.getName();
            double money = e.getMoney();
            row[1] = String.valueOf(money);
            double deposit = Kernel.getInstance().getBank().getDeposit(e);
            row[2] = String.valueOf(deposit);
            row[3] = String.valueOf(e.getCoupon());
            row[4] = String.valueOf(e.getPropertyCells().size());
            double propertyValue = e.getPropertyCells().stream().map(cell->cell.getBuyingPrice()).reduce(0.0, (a,b)->a+b);
            row[5]= String.format("%.2f",propertyValue);
            double stockValue = Kernel.getInstance().getStockMarket().getTotalHold(e);
            row[6]= String.format("%.2f",stockValue);
            row[7]= String.format("%.2f",money+deposit+propertyValue+stockValue);
            buffer.add(row);
        });
        String [][] outer= new String[buffer.size()][];
        for (int i=0;i<buffer.size();++i){
            outer[i]=buffer.get(i);
        }
        Util.printTable(outer);
    }
    private static void showCellInformation(){
        Scanner cin = new Scanner(System.in);
        System.out.println("请输入要查看的地块与您当前位置相差的步数，向前输入正数，向后负数，输入非数字退出：");
        System.out.print(">> ");
        String buf = cin.nextLine().trim();
        int step;
        try {
            step = Integer.parseInt(buf);
        } catch(NumberFormatException e){
            return ;
        }
        AbstractCell nowPos = Kernel.getInstance().getGameMap().getPlayerPosition(Kernel.getInstance().getCurrentPlayer());
        if (step > 0 ){
            for (int i=0;i<step; ++i) nowPos = nowPos.getNextCell();
        }else{
            step = -step;
            for (int i=0;i<step; ++i) nowPos = nowPos.getPreviousCell();
        }
        nowPos.getCellInformation().stream().forEach(dataPair ->
            System.out.println(dataPair.getFirst()+"："+dataPair.getSecond())
        );
    }
    public static void showCardMenu(){
        Player player = Kernel.getInstance().getCurrentPlayer();
        HashMap<String, Integer> cardCountMap = new HashMap<>();
        player.getCards().forEach(c-> {
            if (cardCountMap.keySet().contains(c.getName())){
                cardCountMap.put(c.getName(),cardCountMap.get(c.getName())+1);
            }else {
                cardCountMap.put(c.getName(), 1);
            }
        });
        ArrayList<String> cardNameList = new ArrayList<>();
        cardCountMap.keySet().forEach(e -> cardNameList.add(e));
        if (cardNameList.size() == 0 ){
            System.out.println("您没有卡牌可用！");
            return ;
        }
        System.out.println("您可用的卡牌列表如下：");
        for (int i = 0;i<cardNameList.size();++i){
            System.out.println(" "+(i+1)+"."+cardNameList.get(i)+" "+cardCountMap.get(cardNameList.get(i))+"张");
        }
        System.out.println("请输入您希望使用的卡牌的编号，若输入的数字不在上述之中则放弃使用");
        int choose = Util.getIntFromScanner(new Scanner(System.in));
        if (choose <=0 || choose > cardNameList.size()){
            System.out.println("放弃使用！");
            return ;
        }
        AbstractCard cardToUse = player.getCards().stream()
                .filter(c -> c.getName().equals(cardNameList.get(choose-1)))
                .findFirst().get();
        Player object =null;
        if (cardToUse.needObject()){
            object = Util.choosePlayer("请选择使用的对象", new Scanner(System.in));
            if (object==null){
                System.out.println("放弃使用！");
                return ;
            }
        }
        if (!cardToUse.canBeUse(Kernel.getInstance().getCurrentPlayer(),object)){
            System.out.println("这张卡使用条件不满足，无法使用！");
            return ;
        }
        if (Kernel.getInstance().issueCard(cardToUse, Kernel.getInstance().getCurrentPlayer(), object)){
            player.getCards().remove(cardToUse);
        }
    }
    private static void roadBlockWarning(){
        ArrayList<String> warning = new ArrayList<>();
        AbstractCell currentPos = Kernel.getInstance().getGameMap().getPlayerPosition(Kernel.getInstance().getCurrentPlayer());
        int orientation = Kernel.getInstance().getCurrentPlayer().getOrientation();
        for (int i=0;i<10;++i){
            if (orientation > 0) currentPos = currentPos.getNextCell();
            else currentPos = currentPos.getPreviousCell();
            if (currentPos.hasRoadBlock()){
                warning.add("前方"+i+"步处的"+currentPos.getName()+"有路障！");
            }
        }
        if (warning.size() == 0){
            System.out.println("前方10步内没有路障，请放心前行！");
        }else{
            warning.forEach(e->System.out.println(e));
        }
    }
}
