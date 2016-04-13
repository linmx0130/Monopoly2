package ui;

import java.util.Scanner;

/**
 * Created by Mengxiao Lin on 2016/4/12.
 */
public class Util {
    public static int getIntFromScanner(Scanner scanner){
        boolean pass;
        int value = -1;
        do {
            pass = true;
            System.out.print(">> ");
            String buf = scanner.nextLine().trim();
            try {
                value = Integer.parseInt(buf);
            } catch (NumberFormatException e){
                pass =false;
                System.out.println("输入错误，请重新输入！");
            }
        } while (!pass);
        return value;
    }
    public static double getDoubleFromScanner(Scanner scanner){
        boolean pass;
        double value = -1;
        do {
            pass = true;
            System.out.print(">> ");
            String buf = scanner.nextLine().trim();
            try {
                value = Double.parseDouble(buf);
            } catch (NumberFormatException e){
                pass =false;
                System.out.println("输入错误，请重新输入！");
            }
        } while (!pass);
        return value;
    }
}
