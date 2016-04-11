package ui.map;

import monopoly.GameMap;
import monopoly.cell.AbstractCell;
import monopoly.cell.EmptyCell;
import monopoly.cell.PropertyCell;

/**
 * Created by Mengxiao Lin on 2016/4/11.
 */
public class MapViewer {
    private GameMap gameMap;
    private static String BLANK = "  ";
    private static String EMPTY_CELL = "空";

    public MapViewer(GameMap gameMap){
        this.gameMap=gameMap;
    }

    private String getPropertyMark(PropertyCell cell){
        final String cellOwnerMark = "◎①②③④";
        String ret = "";
        if (cell.getOwner() == null) ret += cellOwnerMark.charAt(0);
        else ret += cellOwnerMark.charAt(cell.getOwner().getId());
        return ret;
    }
    private String getMark(AbstractCell cell){
        if (cell instanceof EmptyCell) return EMPTY_CELL;
        if (cell instanceof PropertyCell) return getPropertyMark((PropertyCell)cell);
        return "崩";
    }
    public void printMap(){
        String [][] buffer = new String[gameMap.getHeight()][gameMap.getWidth()];
        for (int i=0; i< gameMap.getHeight();++i){
            for (int j=0;j<gameMap.getWidth();++j){
                buffer[i][j] = BLANK;
            }
        }
        gameMap.getCellList().stream().forEach(cell -> {
            buffer[cell.getX()][cell.getY()] = getMark(cell);
        });
        for (int i=0;i<gameMap.getHeight();++i){
            for (int j=0;j<gameMap.getWidth();++j){
                System.out.print(buffer[i][j]);
            }
            System.out.println();
        }
    }
}
