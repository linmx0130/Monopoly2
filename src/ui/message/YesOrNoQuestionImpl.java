package ui.message;

import message.YesOrNoQuestion;

import java.util.Scanner;

/**
 * Created by Mengxiao Lin on 2016/4/11.
 */
public class YesOrNoQuestionImpl extends YesOrNoQuestion{
    public YesOrNoQuestionImpl(String description){
        super(description);
    }
    @Override
    public void action() {
        do {
            System.out.println(getDescription());
            System.out.print("Y/N >> ");
            Scanner cin = new Scanner(System.in);
            String inputStr = cin.nextLine().trim();
            if (inputStr.equals("Y")){
                setAnswer(true);
                break;
            }else if (inputStr.equals("N")){
                setAnswer(false);
                break;
            }else{
                System.out.println("输入有误，请重新输入！");
            }
        }while (true);
    }
}
