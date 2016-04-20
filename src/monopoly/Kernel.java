package monopoly;

import message.GameOverMessage;
import message.MessageFactory;
import message.MessagePipe;
import message.SuccessMessage;
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
        do {
            ++currentPlayer;
            if (currentPlayer == players.length) {
                currentPlayer = 0;
                gameTurn++;
                turnAction();
            }
            if (!players[currentPlayer].isLost()){
                return ;
            }
        }while (true);
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

    /**
     * issue a card
     * @param card the card to use
     * @param subject the subject of the card
     * @param object the object of the card
     * @return true if success
     */
    public boolean issueCard(AbstractCard card, Player subject, Player object){
        return cardStack.issueCard(card, subject, object);
    }
    public boolean hasBlockOnNextPosition(){
        return gameMap.getPlayerPosition(players[currentPlayer]).getNextCell().hasRoadBlock();
    }
    public void forgiveGame(){
        players[currentPlayer].setLost(true);
        SuccessMessage msg = (SuccessMessage) getMessageFactory().createMessage("SuccessMessage");
        msg.setDescription("玩家"+players[currentPlayer].getName()+"认输，放弃游戏！");
        getMessagePipe().onMessageArrived(msg);
    }
    public void turnAction(){
        //check the count of left players
        if (Arrays.stream(players).filter(p-> !p.isLost()).count() == 1){
            GameOverMessage msg = (GameOverMessage) getMessageFactory().createMessage("GameOverMessage");
            msg.setWinner(Arrays.stream(players).filter(p-> !p.isLost()).findFirst().get());
            getMessagePipe().onMessageArrived(msg);
            return ;
        }
        cardStack.turnAction();
    }
}
