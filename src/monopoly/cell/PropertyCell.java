package monopoly.cell;

import message.FaultMessage;
import message.PropertyMessage;
import message.YesOrNoQuestion;
import monopoly.Kernel;
import monopoly.Player;

/**
 * Created by Mengxiao Lin on 2016/4/11.
 */
public class PropertyCell extends AbstractCell{
    private final static int LEVEL_BOUND = 6;
    private double basePrice;
    private int level;
    private Player owner;
    public PropertyCell(int id, String name, String description, double basePrice){
        super(id, name,description);
        this.basePrice = basePrice;
        this.level = 1;
        this.owner = null;
    }
    private void askLevelUp() {
        YesOrNoQuestion question = (YesOrNoQuestion) Kernel.getInstance().getMessageFactory().createMessage("YesOrNoQuestion");
        question.setDescription("您到达了属于您的 " + getName() + "，如果升级需要花费 " + getLevelUpCost() + "元，是否升级？");
        Kernel.getInstance().getMessagePipe().onMessageArrived(question);
        if (question.getAnswer()) {
            if (owner.charge(getLevelUpCost())) {
                levelUp();
                PropertyMessage message = (PropertyMessage) Kernel.getInstance().getMessageFactory().createMessage("PropertyMessage");
                message.setPlayer(owner);
                message.setMoney(getLevelUpCost());
                message.setActionType(PropertyMessage.ActionType.LEVEL_UP);
                Kernel.getInstance().getMessagePipe().onMessageArrived(message);
            } else {
                FaultMessage message = (FaultMessage) Kernel.getInstance().getMessageFactory().createMessage("FaultMessage");
                message.setDescription("您的现金不足，无法升级！");
                Kernel.getInstance().getMessagePipe().onMessageArrived(message);
            }
        }
    }
    private void askBuying(Player player) {
        YesOrNoQuestion question = (YesOrNoQuestion) Kernel.getInstance().getMessageFactory().createMessage("YesOrNoQuestion");
        question.setDescription("您到达了无人占有的 " + getName() + "，买下它只要 " + getBuyingPrice() + "元，是否购买？");
        Kernel.getInstance().getMessagePipe().onMessageArrived(question);
        if (question.getAnswer()) {
            if (player.charge(getBuyingPrice())){
                setOwner(player);
                PropertyMessage message = (PropertyMessage) Kernel.getInstance().getMessageFactory().createMessage("PropertyMessage");
                message.setPlayer(owner);
                owner.getPropertyCells().add(this);
                message.setMoney(getBuyingPrice());
                message.setCell(this);
                message.setActionType(PropertyMessage.ActionType.BUYING);
                Kernel.getInstance().getMessagePipe().onMessageArrived(message);
            } else {
                FaultMessage message = (FaultMessage) Kernel.getInstance().getMessageFactory().createMessage("FaultMessage");
                message.setDescription("您的现金不足，无法购买！");
                Kernel.getInstance().getMessagePipe().onMessageArrived(message);
            }
        }
    }
    @Override
    public void arrivedEffect(Player player) {
        if (owner != null){
            if (player == owner){
                if (level != LEVEL_BOUND) {
                    askLevelUp();
                }
            }else {
                double chargeMoney= getPassingCost();
                if (player.chargeForce(chargeMoney)){
                    owner.setMoney(owner.getMoney()+chargeMoney);
                    PropertyMessage message = (PropertyMessage) Kernel.getInstance().getMessageFactory().createMessage("PropertyMessage");
                    message.setActionType(PropertyMessage.ActionType.PASSING);
                    message.setCell(this);
                    message.setMoney(getPassingCost());
                    message.setPlayer(player);
                    Kernel.getInstance().getMessagePipe().onMessageArrived(message);
                }
            }
        }else {
            askBuying(player);
        }
    }

    public double getBasePrice() {
        return basePrice;
    }

    public double getBuyingPrice(){
        return basePrice * level;
    }

    public double getLevelUpCost(){
        return basePrice * 0.5;
    }

    public int getLevel() {
        return level;
    }
    public double getPassingCost(){
        return getBuyingPrice() *0.2;
    }

    public void levelUp(){
        if (level < LEVEL_BOUND) level++;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}
