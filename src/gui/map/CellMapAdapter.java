package gui.map;

import monopoly.cell.AbstractCell;
import monopoly.cell.BankCell;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Cell adapter: all subclass will received a cell and build image of the cell.
 * Created by Mengxiao Lin on 2016/6/9.
 */
public abstract class CellMapAdapter {

    public CellMapAdapter(AbstractCell cell, MapViewer viewer){}
    public abstract Image getImage();

    private static Map<Class, Class> cellAdapterMap = new HashMap<>();
    static {
        CellMapAdapter.registerAdapter(BankCell.class, BankCellAdapter.class);
    }
    /**
     * Register a cell adapter.
     * @param cellType
     * @param adapterType
     */
    public static void registerAdapter(Class cellType, Class adapterType){
        cellAdapterMap.put(cellType, adapterType);
    }
    public static CellMapAdapter getAdapterByCell(AbstractCell cell, MapViewer viewer){
        if (cellAdapterMap.containsKey(cell.getClass())){
            Class adapterClass = cellAdapterMap.get(cell.getClass());
            try {
                return (CellMapAdapter) adapterClass.getConstructor(AbstractCell.class, MapViewer.class).newInstance(cell,viewer);
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
