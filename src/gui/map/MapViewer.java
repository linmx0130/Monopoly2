package gui.map;

import monopoly.GameMap;
import monopoly.Kernel;
import monopoly.Player;
import monopoly.cell.AbstractCell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by Mengxiao Lin on 2016/6/8.
 */
public class MapViewer extends JPanel {
    private int paddingUp = 10;
    private int paddingDown = 10;
    private int paddingLeft = 10;
    private int paddingRight = 10;
    public ArrayList<CellClickedListener> cellClickedListenerList;
    private ImageIcon roadBlockIcon;
    private GameMap gameMap;
    private ArrayList<ImageIcon> playersIconList;
    private final int PLAYER_OFFSET[][] = {{0,0},{0,1},{1,0},{1,1}};
    private void loadPlayersIcon(){
        playersIconList = new ArrayList<>();
        playersIconList.add(new ImageIcon(getClass().getResource("/image/chess_blue.png")));
        playersIconList.add(new ImageIcon(getClass().getResource("/image/chess_red.png")));
        playersIconList.add(new ImageIcon(getClass().getResource("/image/chess_green.png")));
        playersIconList.add(new ImageIcon(getClass().getResource("/image/chess_magenta.png")));
    }
    public MapViewer() {
        setPreferredSize(new Dimension(500, 500));
        gameMap = Kernel.getInstance().getGameMap();
        cellClickedListenerList = new ArrayList<>();
        loadPlayersIcon();
        roadBlockIcon = new ImageIcon(getClass().getResource("/image/road_block.png"));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                final int clickedX = e.getX(), clickedY = e.getY();
                final int cellWidth = getCellWidth(), cellHeight = getCellHeight();
                gameMap.getCellList().forEach(cell->{
                    final int cellX = getCellRealX(cell), cellY = getCellRealY(cell);
                    final int cellX2 = cellX+ cellWidth, cellY2 = cellY+cellHeight;
                    if (clickedX>=cellX && clickedX< cellX2 && clickedY >=cellY && clickedY <cellY2){
                        cellClickedListenerList.forEach(listener -> {listener.cellClickedAction(cell);});
                    }
                });
            }
        });
    }
    private int getCellWidth(){
        return (getWidth() - paddingLeft - paddingRight) / gameMap.getWidth();
    }
    private int getCellHeight(){
        return (getHeight() - paddingUp - paddingDown) / gameMap.getHeight();
    }
    private int getCellRealX(AbstractCell cell){
        return cell.getY() * getCellWidth() + paddingLeft;
    }
    private int getCellRealY(AbstractCell cell){
        return cell.getX() * getCellHeight() + paddingUp;
    }

    private Image getImageOfMap() {
        int imageWidth = getWidth(),
            imageHeight = getHeight();
        // the direction of map is different to that of the image!
        int cellWidth = getCellWidth();
        int cellHeight = getCellHeight();
        Image ret = createImage(imageWidth, imageHeight);
        Graphics2D g = (Graphics2D) ret.getGraphics();
        g.setColor(Color.GREEN);
        //draw cells
        for (AbstractCell cell : gameMap.getCellList()) {
            int cellRealX = getCellRealX(cell);
            int cellRealY = getCellRealY(cell);
            CellMapAdapter cellMapAdapter = CellMapAdapter.getAdapterByCell(cell, this);
            if (cellMapAdapter == null) {
                g.drawRect(cellRealX, cellRealY, cellWidth, cellHeight);
            }else{
                g.drawImage(cellMapAdapter.getImage(), cellRealX,cellRealY, cellWidth,cellHeight,null);
            }
            if (cell.hasRoadBlock()){
                g.drawImage(roadBlockIcon.getImage(), cellRealX,cellRealY, cellWidth/3, cellHeight/3,null);
            }
        }
        //draw players
        for (Player player : Kernel.getInstance().getPlayers()){
            AbstractCell currentCell = gameMap.getPlayerPosition(player);
            int iconWidth = cellWidth/2, iconHeight = cellHeight/2;
            int targetX = getCellRealX(currentCell)+PLAYER_OFFSET[player.getId()-1][0] * (iconWidth+2);
            int targetY = getCellRealY(currentCell)+PLAYER_OFFSET[player.getId()-1][1] * (iconHeight+2);
            g.drawImage(playersIconList.get(player.getId()-1).getImage(), targetX,targetY,iconWidth, iconHeight,null);
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

    public void addCellClickedListener(CellClickedListener listener){
        cellClickedListenerList.add(listener);
    }

}
