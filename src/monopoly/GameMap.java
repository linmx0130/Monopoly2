package monopoly;
import monopoly.card.AbstractCard;
import monopoly.cell.AbstractCell;
import monopoly.cell.CellFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Mengxiao Lin on 2016/4/11.
 */
public class GameMap {
    private int width, height;
    private ArrayList<AbstractCell> cellList;
    private HashMap<Player, AbstractCell> playerPositions;
    private AbstractCell startCell;
    public GameMap() {
        cellList = new ArrayList<>();
        playerPositions = new HashMap<>();
    }

    public void loadMapFromStream(InputStream is) {
        Scanner reader = new Scanner(is);
        CellFactory cellFactory= new CellFactory();
        height = reader.nextInt();
        width = reader.nextInt();
        int cellCount = reader.nextInt();
        int startCell = reader.nextInt();
        for (int i=0;i<cellCount;++i){
            int cellId= reader.nextInt();
            String cellType= reader.next();
            int x =reader.nextInt();
            int y =reader.nextInt();
            AbstractCell cell = cellFactory.buildCellFromScanner(cellId, cellType, reader);
            cell.setX(x);
            cell.setY(y);
            addCell(cell);
        }
        for (int i=0;i<cellCount;++i){
            int x = reader.nextInt();
            int y =reader.nextInt();
            AbstractCell xCell = getCellById(x);
            AbstractCell yCell =getCellById(y);
            xCell.setNextCell(yCell);
            yCell.setPreviousCell(xCell);
        }
        cellList.stream().forEach(e -> {
            if (e.getNextCell() ==null){
                throw new RuntimeException("Map format error!");
            }
        });
        this.startCell= getCellById(startCell);

    }

    public void addCell(AbstractCell cell) {
        cellList.add(cell);
    }

    public AbstractCell getCellById(int id) {
        return cellList.stream().filter(e -> e.getId() == id).findFirst().get();
    }

    public List<AbstractCell> getCellList() {
        return cellList;
    }

    public void setPlayerToCell(Player player, AbstractCell cell) {
        playerPositions.put(player, cell);
    }

    public AbstractCell getPlayerPosition(Player player) {
        return playerPositions.get(player);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public AbstractCell getStartCell() {
        return startCell;
    }

    public int getDistanceBetweenCells(AbstractCell c1, AbstractCell c2){
        int count = 0 ;
        while (c1!= c2) c1= c1.getNextCell();
        if (cellList.size() - count < count) count = cellList.size() -count;
        return count;
    }
    public int getDistanceBetweenPlayers(Player p1, Player p2){
        return getDistanceBetweenCells(playerPositions.get(p1), playerPositions.get(p2));
    }
}
