package monopoly;

import message.MessageFactory;
import message.MessagePipe;

/**
 * Created by Mengxiao Lin on 2016/4/11.
 */
public class Kernel {
    private GameMap gameMap;
    private MessagePipe messagePipe;
    private MessageFactory messageFactory;
    private Kernel(){
        gameMap=new GameMap();
    }
    private static Kernel instance;
    public static Kernel getInstance(){
        if (instance == null){
            instance = new Kernel();
        }
        return instance;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public MessagePipe getMessagePipe() {
        return messagePipe;
    }

    public void setMessagePipe(MessagePipe messagePipe) {
        this.messagePipe = messagePipe;
    }

    public MessageFactory getMessageFactory() {
        return messageFactory;
    }

    public void setMessageFactory(MessageFactory messageFactory) {
        this.messageFactory = messageFactory;
    }
}
