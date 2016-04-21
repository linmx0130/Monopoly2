package monopoly.stock;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Mengxiao Lin on 2016/4/21.
 */
public class Stock {
    private int id;
    private String name;
    private double price;
    private ArrayList<Double> history;

    public Stock(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
        history = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setPrice(double price) {
        history.add(this.price);
        this.price = price;
    }
    public double getFloatRate(){
        return (price - history.get(history.size()-1)) / price;
    }
    public void randomChange(){
        Random random = new Random();
        double rate = (random.nextDouble()-0.5)/5;
        setPrice(price + price*rate);
    }

    public double getPrice() {
        return price;
    }

    public ArrayList<Double> getHistory() {
        return history;
    }
}
