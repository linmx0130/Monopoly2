package ui.message;

import message.BankMessage;
import ui.menu.BankMenu;

/**
 * Created by Mengxiao Lin on 2016/4/12.
 */
public class BankMessageImpl extends BankMessage {
    @Override
    public void action() {
        BankMenu.showBankMenu(getPlayer());
    }
}
