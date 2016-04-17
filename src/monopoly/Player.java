package monopoly;

import monopoly.card.AbstractCard;
import monopoly.cell.PropertyCell;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mengxiao Lin on 2016/4/11.
 */
public class Player {
    private double money;
    private ArrayList<PropertyCell> propertyCells;
    private ArrayList<AbstractCard> cards;
    private String name;
    int id;
    int orientation;
    int coupon;
    public Player(String name){
        this.name=name;
        propertyCells = new ArrayList<>();
        cards = new ArrayList<>();
        orientation = 1;
        coupon = 0;
    }
    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    /**
     * charge some money
     * @param value the amount of money to charge
     * @return success or not
     */
    public boolean charge(double value){
        if (this.money >= value){
            this.money -= value;
            return true;
        }else{
            return false;
        }
    }
    /**
     * force the player to pay some money
     * @param value the amount of money to charge
     * @return success or not
     */
    public boolean chargeForce(double value){
        if (this.money >= value){
            this.money -= value;
            return true;
        }else{
            //get money from bank
            if (Kernel.getInstance().getBank().getDeposit(this)>=value){
                Kernel.getInstance().getBank().modifyMoney(this, -value);
                return true;
            }
            //TODO
            throw new RuntimeException("MONEY NOT ENOUGH");
        }
    }
    public void income(double value){
        this.money +=value;
    }

    public List<PropertyCell> getPropertyCells() {
        return propertyCells;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        if (orientation != 1 && orientation!=-1){
            throw new RuntimeException("Wrong value for orientation!");
        }
        this.orientation = orientation;
    }

    public ArrayList<AbstractCard> getCards() {
        return cards;
    }
    public void addCard(AbstractCard card){
        cards.add(card);
    }

    public int getCoupon() {
        return coupon;
    }

    public void setCoupon(int coupon) {
        this.coupon = coupon;
    }
}
