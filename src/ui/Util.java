package ui;

import monopoly.Kernel;
import monopoly.Player;

import java.util.List;
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
    public static void printSpan(int count){
        for (int i=0;i<count;++i){
            System.out.print(" ");
        }
    }
    public static int getPrintLength(String s){
        int ret =0;
        for (int i=0;i<s.length();++i){
            if (s.charAt(i) <256) ret++;
            else ret+=2;
        }
        return ret;
    }
    public static void printTable(String[][] buffer){
        int[] rowSize = new int[buffer[0].length];
        for (int i=0;i<buffer.length;++i){
            for (int j =0;j<buffer[0].length;++j){
                if (rowSize[j]<getPrintLength(buffer[i][j])){
                    rowSize[j]=getPrintLength(buffer[i][j]);
                }
            }
        }
        for (int i=0;i<buffer.length;++i){
            for (int j=0;j<buffer[0].length;++j){
                System.out.print(buffer[i][j]);
                printSpan(rowSize[j]+3 - getPrintLength(buffer[i][j]));
            }
            System.out.println();
        }
    }
    public static Player choosePlayer(String msg, Scanner scanner){
        System.out.println(msg);
        Kernel.getInstance().getPlayers().stream().sorted((p1,p2)-> p1.getId()-p2.getId())
                .forEach(player -> System.out.println(player.getId()+"."+player.getName()));
        System.out.println("0.放弃选择玩家");
        boolean pass = false;
        int value=0;
        do {
            pass = true;
            System.out.print(">> ");
            String buf = scanner.nextLine().trim();
            try {
                value = Integer.parseInt(buf);
            } catch (NumberFormatException e){
                pass =false;
            }
            if (value <0 || value > Kernel.getInstance().getPlayers().size()){
                pass = false;
            }
            if (!pass){
                System.out.println("输入错误，请重新输入！");
            }
        } while (!pass);
        if (value ==0)return null;
        int finalValue = value;
        return Kernel.getInstance().getPlayers().stream().filter(p -> p.getId() == finalValue).findFirst().get();
    }
}
