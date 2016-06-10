package gui.map;

import gui.Constant;
import monopoly.cell.AbstractCell;
import monopoly.cell.PropertyCell;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Mengxiao Lin on 2016/6/10.
 */
public class PropertyCellAdapter extends CellMapAdapter {
    private MapViewer viewer;
    private static ArrayList<ImageIcon> iconList;
    private PropertyCell cell;
    public PropertyCellAdapter(AbstractCell cell, MapViewer viewer) {
        super(cell, viewer);
        this.viewer = viewer;
        this.cell = (PropertyCell) cell;
        if (iconList == null){
            iconList = new ArrayList<>();
            iconList.add(new ImageIcon(getClass().getResource("/image/house1.png")));
            iconList.add(new ImageIcon(getClass().getResource("/image/house2.png")));
            iconList.add(new ImageIcon(getClass().getResource("/image/house3.png")));
        }
    }

    @Override
    public Image getImage() {
        Image ret = viewer.createImage(128,128);
        Graphics2D g = (Graphics2D)ret.getGraphics();
        g.setStroke(new BasicStroke(5));
        if (cell.getOwner() == null) {
            g.setColor(Color.ORANGE);
        }else {
            g.setColor(Constant.PLAYER_COLOR[cell.getOwner().getId()-1]);
        }
        g.drawRect(4,4,120,120);
        int targetIconId = Math.min(iconList.size(), cell.getLevel())-1;
        g.drawImage(iconList.get(targetIconId).getImage(),8,8,112,112,null);
        return ret;
    }
}
