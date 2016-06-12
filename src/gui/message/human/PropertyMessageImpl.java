package gui.message.human;

import message.PropertyMessage;

import javax.swing.*;

/**
 * Created by Mengxiao Lin on 2016/6/12.
 */
public class PropertyMessageImpl extends PropertyMessage {
    @Override
    public void action() {
        String tips = null;
        switch (this.getActionType()){
            case PASSING:
                tips= getPlayer().getName()+
                        " 路过 "+ getCell().getName()+
                        " 向主人 "+getCell().getOwner().getName() +
                        " 支付 " +getMoney() + "元作为过路费";
                break;
            case BUYING:
                tips = getPlayer().getName()+" 花了 "+getMoney()+"元购买了"+getCell().getName()+"!";
                break;
            case LEVEL_UP:
                tips = getPlayer().getName()+" 花了 "+getMoney()+"元升级了"+getCell().getName()+
                        "，现在 "+getCell().getName()+" 是"+getCell().getLevel()+"级！";
                break;
            default:
        }
        JOptionPane.showConfirmDialog(null, tips,"Monopoly 2",JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
    }
}
