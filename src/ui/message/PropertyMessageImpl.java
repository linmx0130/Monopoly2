package ui.message;
import message.PropertyMessage;
/**
 * Created by Mengxiao Lin on 2016/4/11.
 */
public class PropertyMessageImpl extends PropertyMessage {
    @Override
    public void action() {
        switch (this.getActionType()){
            case PASSING:
                System.out.println(
                        getPlayer().getName()+
                        " 路过 "+ getCell().getName()+
                        " 向主人 "+getCell().getOwner() +
                        " 支付 " +getMoney() + "元作为过路费");
                break;
            case BUYING:
                System.out.println(getPlayer().getName()+" 花了 "+getMoney()+"元购买了"+getCell().getName()+"!");
                break;
            case LEVEL_UP:
                System.out.println(getPlayer().getName()+" 花了 "+getMoney()+"元升级了"+getCell().getName()+
                        "，现在 "+getCell().getName()+" 是"+getCell().getLevel()+"级！");
                break;
            default:
        }
    }
}
