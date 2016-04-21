package ui.menu;

import monopoly.Kernel;
import monopoly.stock.Stock;
import monopoly.stock.StockMarket;
import ui.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    public static void show(){
        StockMarket market = Kernel.getInstance().getStockMarket();
        boolean flag = true;
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        do {
            showStocks(market);
            System.out.println("b x n  -> 购买x号股n份");
            System.out.println("s x n  -> 售出x号股n份");
            System.out.println("q      -> 退出股票市场");
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
            else if (buf.startsWith("b")){
                //TODO: buy
            } else if (buf.startsWith("s")){
                //TODO: sell
            }else{
                System.out.println("输入错误，请重新输入");
            }
        }while (flag);
    }
}
