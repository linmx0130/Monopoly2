package ui.message;

import message.RoadBlockCardQuestion;
import ui.Util;

import java.util.Scanner;

/**
 * Created by Mengxiao Lin on 2016/4/17.
 */
public class RoadBlockCardQuestionImpl extends RoadBlockCardQuestion {
    @Override
    public void action() {
        System.out.println("请输入您想安防路障的地点到您的距离，正数向前，负数向后，可以输入-8~8，输入其他数字放弃使用。");
        int choose = Util.getIntFromScanner(new Scanner(System.in));
        setChoose(choose);
    }
}
