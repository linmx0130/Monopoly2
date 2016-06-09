package gui.map;

import monopoly.GameMap;
import monopoly.Kernel;
import monopoly.cell.AbstractCell;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Mengxiao Lin on 2016/6/8.
 */
public class MapViewer extends JPanel {
    private int paddingUp = 10;
    private int paddingDown = 10;
    private int paddingLeft = 10;
    private int paddingRight = 10;

    public MapViewer() {
        setPreferredSize(new Dimension(500, 500));
    }

    private Image getImageOfMap() {
        int imageWidth = getWidth(),
            imageHeight = getHeight();
        GameMap gameMap = Kernel.getInstance().getGameMap();
        // the direction of map is different to that of the image!
        int cellWidth = (imageWidth - paddingLeft - paddingRight) / gameMap.getWidth();
        int cellHeight = (imageHeight - paddingUp - paddingDown) / gameMap.getHeight();
        Image ret = createImage(imageWidth, imageHeight);
        Graphics2D g = (Graphics2D) ret.getGraphics();
        g.setColor(Color.GREEN);
        for (AbstractCell cell : gameMap.getCellList()) {
            int cellRealX = cell.getY() * cellWidth + paddingLeft;
            int cellRealY = cell.getX() * cellHeight + paddingUp;
            CellMapAdapter cellMapAdapter = CellMapAdapter.getAdapterByCell(cell, this);
            if (cellMapAdapter == null) {
                g.drawRect(cellRealX, cellRealY, cellWidth, cellHeight);
            }else{
                g.drawImage(cellMapAdapter.getImage(), cellRealX,cellRealY, cellWidth,cellHeight,null);
            }
        }
        return ret;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;
        Image buf = getImageOfMap();
        g.drawImage(buf,0 , 0,getWidth(), getHeight(), null);
    }

    public void setPaddingUp(int paddingUp) {
        this.paddingUp = paddingUp;
    }

    public void setPaddingDown(int paddingDown) {
        this.paddingDown = paddingDown;
    }

    public void setPaddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
    }

    public void setPaddingRight(int paddingRight) {
        this.paddingRight = paddingRight;
    }
}
