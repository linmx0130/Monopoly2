package monopoly.stock;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Mengxiao Lin on 2016/4/21.
 */
public class StockMarket {
    private ArrayList<Stock> stocks;
    public StockMarket(){
        stocks = new ArrayList<>();
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
}
