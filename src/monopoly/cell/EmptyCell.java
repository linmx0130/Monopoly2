package monopoly.cell;

import monopoly.Player;

/**
 * Created by Mengxiao Lin on 2016/4/11.
 */
public class EmptyCell extends AbstractCell{
    public EmptyCell(int id){
        super(id, "Empty", "Nothing happened here.");
    }
    @Override
    public void arrivedEffect(Player player) {

    }
}
