package monopoly.cell;
import monopoly.Player;
/**
 * Created by Mengxiao Lin on 2016/4/11.
 */
public abstract class AbstractCell {
    private int id;
    private String name;
    private String description;
    private AbstractCell nextCell;
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

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public abstract void arrivedEffect(Player player);
}
