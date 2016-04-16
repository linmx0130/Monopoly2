package monopoly.cell;
import monopoly.Pair;
import monopoly.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mengxiao Lin on 2016/4/11.
 */
public abstract class AbstractCell {
    private int id, x, y;
    private String name;
    private String description;
    private AbstractCell nextCell, previousCell;
    protected AbstractCell(int id, String name, String description){
        this.id= id;
        this.name = name;
        this.description = description;
    }

    public void setNextCell(AbstractCell nextCell) {
        this.nextCell = nextCell;
    }

    public int getId() {
        return id;
    }

    public AbstractCell getNextCell() {
        return nextCell;
    }

    public AbstractCell getPreviousCell() {
        return previousCell;
    }

    public void setPreviousCell(AbstractCell previousCell) {
        this.previousCell = previousCell;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public abstract void arrivedEffect(Player player);
    public void moveOverEffect(Player player){
        //no effect for most cell
    }
    public List<Pair<String, String>> getCellInformation(){
        ArrayList<Pair<String, String>> ret =new ArrayList<>();
        ret.add(new Pair<>("类型", name));
        ret.add(new Pair<>("描述", description));
        return ret;
    }

}
