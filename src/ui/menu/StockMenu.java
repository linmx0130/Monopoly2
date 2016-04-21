package ui.menu;

import monopoly.Kernel;
import monopoly.Player;
import monopoly.stock.Stock;
import monopoly.stock.StockMarket;
import ui.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by Mengxiao Lin on 2016/4/21.
 */
public class StockMenu {
    public static void showStocks(StockMarket market){
        String[][] buffer = new String[market.getStocks().size()+1][4];
        buffer[0][0] = "序号";
        buffer[0][1]="公司名";
        buffer[0][2]="每股价格";
        buffer[0][3]="昨日涨跌";
        for (int i=0;i<market.getStocks().size(); ++i){
            Stock stock = market.getStocks().get(i);
            buffer[i+1][0]=String.valueOf(stock.getId());
            buffer[i+1][1]=stock.getName();
            buffer[i+1][2]=String.format("%.2f",stock.getPrice());
            buffer[i+1][3]=String.format("%.2f",stock.getFloatRate());
        }
        Util.printTable(buffer);
    }
    private static void sellStockMenu(StockMarket market, String buf){
        String[] dataBuf = buf.split(" ");
        if (dataBuf.length!=3){
            System.out.println("输入错误，请重新输入");
            return ;
        }
        int stockId, amount;
        try {
            stockId = Integer.valueOf(dataBuf[1]);
            amount = Integer.valueOf(dataBuf[2]);
        } catch (NumberFormatException e){
            System.out.println("输入错误，请重新输入");
            return;
        }
        market.sellStock(Kernel.getInstance().getCurrentPlayer(), stockId, amount);
    }
    private static void buyStockMenu(StockMarket market, String buf){
        String[] dataBuf = buf.split(" ");
        if (dataBuf.length!=3){
            System.out.println("输入错误，请重新输入");
            return ;
        }
        int stockId, amount;
        try {
            stockId = Integer.valueOf(dataBuf[1]);
            amount = Integer.valueOf(dataBuf[2]);
        } catch (NumberFormatException e){
            System.out.println("输入错误，请重新输入");
            return;
        }
        market.buyStock(Kernel.getInstance().getCurrentPlayer(), stockId, amount);
    }
    private static void showStockHold(StockMarket market){
        Player player = Kernel.getInstance().getCurrentPlayer();
        HashMap<Stock, Integer> stockHold = market.getStockHold(player);
        String [][] buf = new String[stockHold.size()+1][4];
        buf[0][0]="序号";
        buf[0][1]="股票名";
        buf[0][2]="每股价格";
        buf[0][3]="持股量";
        ArrayList<Stock> stockList =new ArrayList<>();
        stockHold.keySet().stream().sorted((s1, s2) ->s2.getId()-s1.getId()).forEach(stock -> stockList.add(stock));
        for (int i=0;i<stockList.size();++i){
            Stock stock = stockList.get(i);
            buf[i+1][0]=String.valueOf(stock.getId());
            buf[i+1][1]=stock.getName();
            buf[i+1][2]=String.format("%.2f",stock.getPrice());
            buf[i+1][3]=String.valueOf(stockHold.get(stock));
        }
        Util.printTable(buf);
    }
    public static void show(){
        StockMarket market = Kernel.getInstance().getStockMarket();
        boolean flag = true;
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        do {
            showStocks(market);
            System.out.println("b x n  -> 购买x号股n份");
            System.out.println("s x n  -> 售出x号股n份");
            System.out.println("l      -> 查看持股情况");
            System.out.println("q      -> 退出股票市场");
            System.out.print(">> ");
            String buf;
            try {
                buf = cin.readLine();
                if (buf == null){
                    throw new RuntimeException("ERROR INPUT!");
                }
            } catch (IOException e) {
                throw new RuntimeException("ERROR INPUT!");
            }
            buf= buf.trim();
            if (buf.equals("q")) flag = false;
            else if (buf.equals("l")) {
                showStockHold(market);
            }
            else if (buf.startsWith("b")) {
                buyStockMenu(market, buf);
            }else if (buf.startsWith("s")){
                sellStockMenu(market, buf);
            }else{
                System.out.println("输入错误，请重新输入");
            }
        }while (flag);
    }
}
