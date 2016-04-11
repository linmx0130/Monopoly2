package monopoly;
import monopoly.cell.AbstractCell;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mengxiao Lin on 2016/4/11.
 */
public class GameMap {
    private int width, height;
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
}
