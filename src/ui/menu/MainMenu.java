package ui.menu;

import monopoly.Kernel;
import monopoly.Player;
import ui.Util;
import ui.map.MapViewer;

import java.util.Random;
import java.util.Scanner;

/**
 * Created by Mengxiao Lin on 2016/4/12.
 */
public class MainMenu {
    public static void showMainMenu(Player player){
        Kernel kernel = Kernel.getInstance();
        MapViewer mapViewer=new MapViewer(kernel.getGameMap());
        Player currentPlayer = kernel.getCurrentPlayer();
        boolean waiting = true;
        do {
            System.out.println("============");
            System.out.println("玩家 " + player.getName() +" ，现在是第" + kernel.getGameTurn() + "回合，请问你要做什么？");
            System.out.println(" 0 - 查看地图");
            System.out.println(" 1 - 查看原始地图");
            System.out.println(" 2 - 想看的都看了，心满意足地扔骰子前进！");
            int choose = Util.getIntFromScanner(new Scanner(System.in));
            switch (choose){
                case 0:
                    mapViewer.printMapWithRole();
                    break;
                case 1:
                    mapViewer.printMap();
                    break;
                case 2:
                    Random random=new Random();
                    int step = random.nextInt(6)+1;
                    System.out.println(" 您投出了"+step+"！");
                    for (int i=1 ;i<step ;++i) {
                        kernel.playerMove();
                    }
                    kernel.playerMoveEnd();
                    kernel.nextPlayer();
                    waiting = false;
                    break;
                default:
            }
        }while (waiting);
    }
}
