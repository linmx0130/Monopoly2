package gui.map;

import monopoly.cell.AbstractCell;
import monopoly.cell.BankCell;

import java.awt.*;

/**
 * Created by Mengxiao Lin on 2016/6/9.
 */
public class BankCellAdapter extends CellMapAdapter{
    private MapViewer viewer;
    public BankCellAdapter(AbstractCell cell, MapViewer viewer) {
        super(cell, viewer);
        this.viewer = viewer;
    }

    @Override
    public Image getImage() {
        Image ret = viewer.createImage(64,64);
        Graphics2D g = (Graphics2D) ret.getGraphics();
        g.drawString("$",20,20); //TODO: use a real icon of bank
        return ret;
    }
}
