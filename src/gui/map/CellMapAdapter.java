package gui.map;

import monopoly.cell.*;

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
        registerAdapter(BankCell.class, BankCellAdapter.class);
        registerAdapter(NewsCell.class, NewsCellAdapter.class);
        registerAdapter(CardCell.class, CardCellAdapter.class);
        registerAdapter(CardShopCell.class, CardShopCellAdapter.class);
        registerAdapter(CouponCell.class, CouponCellAdapter.class);
        registerAdapter(LotteryCell.class, LotteryCellAdapter.class);
        registerAdapter(PropertyCell.class, PropertyCellAdapter.class);
        registerAdapter(EmptyCell.class, EmptyCellAdapter.class);
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
