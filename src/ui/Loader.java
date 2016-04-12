package ui;

import message.MessageFactory;
import message.MessagePipe;
import monopoly.Kernel;
import monopoly.Player;
import ui.menu.MainMenu;
import ui.message.MessageFactoryImpl;
import ui.message.MessagePipeImpl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Mengxiao Lin on 2016/4/12.
 */
public class Loader {
    private static MessageFactory consoleMessageFactory;
    private static MessagePipe consoleMessagePipe;
    private static void askUserInformation(){
        Scanner cin = new Scanner(System.in);
        System.out.println("请输入玩家总数。");
        int userCount = Util.getIntFromScanner(cin);
        Kernel.createInstance(userCount);
        // input players
        for (int i=0;i<userCount;++i){
            System.out.println("请输入第"+(i+1)+"位玩家的姓名");
            System.out.print(">> ");
            String buf = cin.nextLine().trim();
            Player player = new Player(buf);
            player.setId(i+1);
            Kernel.getInstance().addPlayer(player,consoleMessageFactory, consoleMessagePipe);
        }
    }
    public static void main(String args[]){
        consoleMessageFactory= new MessageFactoryImpl();
        consoleMessagePipe=new MessagePipeImpl();
        askUserInformation();
        // load map
        try {
            Kernel.getInstance().getGameMap().loadMapFromStream(new FileInputStream("map.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Map file not found!");
        }
        Kernel.getInstance().initPlayers();
        //main loop
        do {
            MainMenu.showMainMenu(Kernel.getInstance().getCurrentPlayer());
        }while (true);
    }
}
