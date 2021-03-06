package gui.map;

import monopoly.cell.AbstractCell;
import javax.swing.*;
import java.awt.*;

/**
 * Created by Mengxiao Lin on 2016/6/9.
 */
public class BankCellAdapter extends CellMapAdapter{
    private MapViewer viewer;
    private static ImageIcon icon;
    public BankCellAdapter(AbstractCell cell, MapViewer viewer) {
        super(cell, viewer);
        this.viewer = viewer;
        if (icon == null){
            icon = new ImageIcon(getClass().getResource("/image/bank.png"));
        }
    }

    @Override
    public Image getImage() {
        Image ret = viewer.createImage(128,128);
        Graphics2D g = (Graphics2D)ret.getGraphics();
        g.setStroke(new BasicStroke(5));
        g.setColor(Color.ORANGE);
        g.drawRect(4,4,120,120);
        g.drawImage(icon.getImage(),8,8,112,112,null);
        return ret;
    }
}
