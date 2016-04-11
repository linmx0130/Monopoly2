package monopoly;
import monopoly.cell.AbstractCell;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mengxiao Lin on 2016/4/11.
 */
public class GameMap {
    private ArrayList<AbstractCell> cellList;
    public GameMap(){
        cellList = new ArrayList<>();
    }
    public void addCell(AbstractCell cell){
        cellList.add(cell);
    }
    public AbstractCell getCellById(int id){
        return cellList.stream().filter(e -> e.getId() == id).findFirst().get();
    }
    public List<AbstractCell> getCellList(){
        return cellList;
    }
}
