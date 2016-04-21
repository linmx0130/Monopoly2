package ui.message;

import message.DiceControllerQuestion;

import java.util.Scanner;

/**
 * Created by Mengxiao Lin on 2016/4/21.
 */
public class DiceControllerQuestionImpl extends DiceControllerQuestion {
    @Override
    public void action() {
        Scanner cin = new Scanner(System.in);
        String buf = null;
        System.out.println("请输入1~6的数字，输入其他放弃使用遥控骰子。");
        System.out.print(">> ");
        buf = cin.nextLine().trim();
        try{
            int choose = Integer.valueOf(buf);
            if (choose <=0 || choose > 6) setChoose(null);
            else setChoose(choose);
        } catch (NumberFormatException e){
            setChoose(null);
        }
    }
}
