package gui.map;

import monopoly.cell.AbstractCell;

/**
 * Listen to the map. Perform action when a cell is clicked.
 * Created by Mengxiao Lin on 2016/6/10.
 */
public interface CellClickedListener {
    void cellClickedAction(AbstractCell cellBeClicked);
}
