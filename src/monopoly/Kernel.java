package monopoly;

import message.MessageFactory;
import message.MessagePipe;

/**
 * Created by Mengxiao Lin on 2016/4/11.
 */
public class Kernel {
    private GameMap gameMap;
    private MessagePipe[] messagePipes;
    private MessageFactory[] messageFactories;
    private Player[] players;
    private int currentPlayer;
    private Kernel(int userCount){
        gameMap=new GameMap();
        if (userCount>4){
            throw new RuntimeException("Too many users!");
        }
        messagePipes = new MessagePipe[userCount];
        messageFactories= new MessageFactory[userCount];
        players = new Player[userCount];
        currentPlayer = 0;
    }
    private static Kernel instance;
    public static void createInstance(int userCount){
        instance= new Kernel(userCount);
    }
    public static Kernel getInstance(){
        if (instance == null){
            instance = new Kernel(4);
        }
        return instance;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public MessagePipe getMessagePipe() {
        return messagePipes[currentPlayer];
    }

    public void setMessagePipe(MessagePipe messagePipe, Player player) {
        for (int i=0;i<players.length;++i){
            if (player == players[i]){
                messagePipes[i]=messagePipe;
            }
        }
    }

    public MessageFactory getMessageFactory() {
        return messageFactories[currentPlayer];
    }

    public void setMessageFactory(MessageFactory messageFactory, Player player) {
        for (int i=0;i<players.length;++i){
            if (player == players[i]){
                messageFactories[i]=messageFactory;
            }
        }
    }
    public void addPlayer(Player player, MessageFactory messageFactory, MessagePipe messagePipe){
        int pos = player.getId()-1;
        players[pos]=player;
        messageFactories[pos]=messageFactory;
        messagePipes[pos]=messagePipe;
    }
}
