package ui.map;

import monopoly.GameMap;
import monopoly.Kernel;
import monopoly.Player;
import monopoly.cell.*;

/**
 * Created by Mengxiao Lin on 2016/4/11.
 */
public class MapViewer {
    private GameMap gameMap;
    private static String BLANK = "  ";
    private static String EMPTY_CELL = "空";
    private static String BANK_CELL="银";
    private static String NEWS_CELL="新";
    private static String CARD_CELL="卡";
    private static final String CELL_OWNER_MARK= "◎①②③④";
    private static final String PLAYER_MARK=" αβγδ";
    public MapViewer(GameMap gameMap){
        this.gameMap=gameMap;
    }

    private String getPropertyMark(PropertyCell cell){
        String ret = "";
        if (cell.getOwner() == null) ret += CELL_OWNER_MARK.charAt(0);
        else ret += CELL_OWNER_MARK.charAt(cell.getOwner().getId());
        return ret;
    }
    private String getMark(AbstractCell cell){
        if (cell instanceof EmptyCell) return EMPTY_CELL;
        if (cell instanceof PropertyCell) return getPropertyMark((PropertyCell)cell);
        if (cell instanceof BankCell) return BANK_CELL;
        if (cell instanceof NewsCell) return NEWS_CELL;
        if (cell instanceof CardCell) return CARD_CELL;
        return "崩";
    }
    public String[][] getMapStringBuffer(){
        String [][] buffer = new String[gameMap.getHeight()][gameMap.getWidth()];
        for (int i=0; i< gameMap.getHeight();++i){
            for (int j=0;j<gameMap.getWidth();++j){
                buffer[i][j] = BLANK;
            }
        }
        gameMap.getCellList().stream().forEach(cell -> {
            buffer[cell.getX()][cell.getY()] = getMark(cell);
        });
        return buffer;

    }
    private void printBuffer(String[][] buffer){
        for (int i=0;i<gameMap.getHeight();++i) {
            for (int j = 0; j < gameMap.getWidth(); ++j) {
                System.out.print(buffer[i][j]);
            }
            System.out.println();
        }
    }
    public void printMap(){
        String [][] buffer= getMapStringBuffer();
        printBuffer(buffer);
    }
    public void printMapWithRole(){
        String [][] buffer= getMapStringBuffer();
        Kernel.getInstance().getPlayers().stream().forEach(e->{
            AbstractCell cell = gameMap.getPlayerPosition(e);
            buffer[cell.getX()][cell.getY()] = ""+ PLAYER_MARK.charAt(e.getId());
        });
        Player currentPlayer = Kernel.getInstance().getCurrentPlayer();
        AbstractCell currentCell = gameMap.getPlayerPosition(currentPlayer);
        buffer[currentCell.getX()][currentCell.getY()]= ""+PLAYER_MARK.charAt(currentPlayer.getId());
        printBuffer(buffer);
    }
}
