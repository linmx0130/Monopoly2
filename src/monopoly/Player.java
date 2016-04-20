package monopoly;

import message.SuccessMessage;
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
    private boolean isLost;
    int id;
    int orientation;
    int coupon;
    public Player(String name){
        this.name=name;
        propertyCells = new ArrayList<>();
        cards = new ArrayList<>();
        orientation = 1;
        coupon = 0;
        isLost = false;
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
            value -= this.money;
            this.money = 0;
            //get money from bank
            if (Kernel.getInstance().getBank().getDeposit(this)>=value){
                Kernel.getInstance().getBank().modifyMoney(this, -value);
                return true;
            }
            value -= Kernel.getInstance().getBank().getDeposit(this);
            Kernel.getInstance().getBank().modifyMoney(this , - Kernel.getInstance().getBank().getDeposit(this));
            //sell properties
            Object[] cells= this.getPropertyCells().stream().sorted((a,b) -> {
                double diff = b.getBuyingPrice() - a.getBuyingPrice();
                if (diff>0) return 1;
                if (diff<0) return -1;
                return 0;
            }).toArray();
            ArrayList<String> cellsSold = new ArrayList<>();
            for (int i=0;i<cells.length;++i){
                PropertyCell cell =(PropertyCell) cells[i];
                this.money+=cell.getBuyingPrice();
                this.getPropertyCells().remove(cell);
                cell.setOwner(null);
                cellsSold.add(cell.getName());
                if (this.money >=value) break;
            }
            if (this.money < value) {
                setLost(true);
                SuccessMessage msg = (SuccessMessage) Kernel.getInstance().getMessageFactory().createMessage("SuccessMessage");
                msg.setDescription("玩家"+name+"因为资不抵债，破产离开游戏。");
                Kernel.getInstance().getMessagePipe().onMessageArrived(msg);
                return false;
            }else{
                this.money -= value;
                SuccessMessage msg = (SuccessMessage) Kernel.getInstance().getMessageFactory().createMessage("SuccessMessage");
                StringBuffer buffer = new StringBuffer("因为现金存款不足，出售了");
                cellsSold.forEach(e -> buffer.append(e+"，"));
                buffer.append("现在还剩现金");
                buffer.append(this.money);
                buffer.append("元");
                msg.setDescription(buffer.toString());
                Kernel.getInstance().getMessagePipe().onMessageArrived(msg);
                return true;
            }
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

    public boolean isLost() {
        return isLost;
    }

    public void setLost(boolean lost) {
        isLost = lost;
    }
}
