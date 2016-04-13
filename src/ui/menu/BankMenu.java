package ui.menu;

import monopoly.Bank;
import monopoly.Kernel;
import monopoly.Player;
import ui.Util;

import java.util.Scanner;

/**
 * Created by Mengxiao Lin on 2016/4/12.
 */
public class BankMenu {
    public static Bank bank;
    public static void storeMoneyMenu(Player player){
        System.out.println("请输入存款金额。");
        double value = Util.getDoubleFromScanner(new Scanner(System.in));
        bank.storeMoney(player, value);
    }
    public static void withdrawMenu(Player player){
        System.out.println("请输入取款金额。");
        double value = Util.getDoubleFromScanner(new Scanner(System.in));
        bank.withdraw(player, value);
    }
    public static void showBankMenu(Player player) {
        if (bank == null) bank=Kernel.getInstance().getBank();
        do {
            System.out.println("===$银行$===");
            System.out.println(player.getName() + " 您好！您现在有现金" + player.getMoney() +
                    "元，存款" + bank.getDeposit(player) + "元。");
            System.out.println("请选择您希望进行的操作：");
            System.out.println(" 1.存款");
            System.out.println(" 2.取款");
            System.out.println(" 输入其他数字离开银行。");
            int choose = Util.getIntFromScanner(new Scanner(System.in));
            switch (choose) {
                case 1:
                    storeMoneyMenu(player);
                    break;
                case 2:
                    withdrawMenu(player);
                    break;
                default:
                    return ;
            }
        }while(true);
    }
}
