package monopoly;

import message.MessageFactory;
import message.MessagePipe;
import monopoly.card.AbstractCard;
import monopoly.card.CardFactory;
import monopoly.card.CardStack;
import monopoly.cell.AbstractCell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Mengxiao Lin on 2016/4/11.
 */
public class Kernel {
    private GameMap gameMap;
    private MessagePipe[] messagePipes;
    private MessageFactory[] messageFactories;
    private Player[] players;
    private int currentPlayer;
    private int gameTurn;
    private Bank bank;
    private CardFactory cardFactory;
    private CardStack cardStack;
    private Kernel(int userCount){
        gameMap=new GameMap();
        if (userCount>4){
            throw new RuntimeException("Too many users!");
        }
        messagePipes = new MessagePipe[userCount];
        messageFactories= new MessageFactory[userCount];
        players = new Player[userCount];
        currentPlayer = 0;
        gameTurn = 0;
        bank = new Bank(userCount);
        cardFactory= new CardFactory();
        cardStack = new CardStack();
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
    public Player getCurrentPlayer(){
        return players[currentPlayer];
    }
    public int getGameTurn(){
        return gameTurn;
    }
    public void nextPlayer(){
        if (++currentPlayer == players.length) {
            currentPlayer = 0;
            gameTurn++;
            cardStack.turnAction();
        }
    }
    public List<Player> getPlayers(){
        ArrayList<Player> ret = new ArrayList<>();
        for (int i=0;i<players.length;++i){
            ret.add(players[i]);
        }
        return ret;
    }

    public void playerMove(){
        Player currentPlayer = players[this.currentPlayer];
        AbstractCell nowPos = gameMap.getPlayerPosition(currentPlayer);
        if (currentPlayer.getOrientation() == 1) nowPos = nowPos.getNextCell();
        else nowPos = nowPos.getPreviousCell();
        gameMap.setPlayerToCell(currentPlayer, nowPos);
        nowPos.moveOverEffect(currentPlayer);
    }
    public void playerMoveEnd(){
        Player currentPlayer = players[this.currentPlayer];
        AbstractCell nowPos = gameMap.getPlayerPosition(currentPlayer);
        if (currentPlayer.getOrientation() == 1) nowPos = nowPos.getNextCell();
        else nowPos = nowPos.getPreviousCell();
        gameMap.setPlayerToCell(currentPlayer, nowPos);
        nowPos.arrivedEffect(currentPlayer);
    }
    public void initPlayers(){
        Arrays.stream(players).forEach(e -> {
            gameMap.setPlayerToCell(e, gameMap.getStartCell());
            e.setMoney(10000);
        });
    }

    public Bank getBank() {
        return bank;
    }

    public CardFactory getCardFactory() {
        return cardFactory;
    }
    public void issueCard(AbstractCard card, Player subject, Player object){
        cardStack.issueCard(card, subject, object);
    }
}
