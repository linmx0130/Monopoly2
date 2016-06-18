package gui;

import monopoly.Kernel;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.*;

/**
 * Created by Mengxiao Lin on 2016/6/19.
 */
public class PlayerPropertyStatDialog extends JDialog {
    private TableModel dataModel;
    private void buildTableModel(){
        String [] title= new String[8];
        title[0]="玩家名";
        title[1]="现金";
        title[2]="存款";
        title[3]="点券";
        title[4]="房产数量";
        title[5]="房产总额";
        title[6]="股票总额";
        title[7]="资产总额";
        String[][] data = new String[Kernel.getInstance().getPlayers().size()][];
        Kernel.getInstance().getPlayers().stream().forEach(e->{
            String [] row =new String [8];
            row[0] = e.getName();
            double money = e.getMoney();
            row[1] = String.valueOf(money);
            double deposit = Kernel.getInstance().getBank().getDeposit(e);
            row[2] = String.valueOf(deposit);
            row[3] = String.valueOf(e.getCoupon());
            row[4] = String.valueOf(e.getPropertyCells().size());
            double propertyValue = e.getPropertyCells().stream().map(cell->cell.getBuyingPrice()).reduce(0.0, (a,b)->a+b);
            row[5]= String.format("%.2f",propertyValue);
            double stockValue = Kernel.getInstance().getStockMarket().getTotalHold(e);
            row[6]= String.format("%.2f",stockValue);
            row[7]= String.format("%.2f",money+deposit+propertyValue+stockValue);
            data[e.getId()-1]=row;
        });
        dataModel = new TableModel() {
            @Override
            public int getRowCount() {
                return data.length;
            }

            @Override
            public int getColumnCount() {
                return title.length;
            }

            @Override
            public String getColumnName(int columnIndex) {
                return title[columnIndex];
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return title[columnIndex].getClass();
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                return data[rowIndex][columnIndex];
            }

            @Override
            public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
                data[rowIndex][columnIndex] = aValue.toString();
            }

            @Override
            public void addTableModelListener(TableModelListener l) {

            }

            @Override
            public void removeTableModelListener(TableModelListener l) {

            }
        };
    }

    public PlayerPropertyStatDialog() {
        buildTableModel();
        JTable table = new JTable(dataModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setLayout(new BorderLayout());
        add(table, BorderLayout.CENTER);
        JPanel btnPanel = new JPanel(new BorderLayout());
        JButton closeBtn = new JButton("关闭");
        closeBtn.addActionListener(e->setVisible(false));
        btnPanel.add(closeBtn,BorderLayout.EAST);
        add(btnPanel,BorderLayout.SOUTH);
        setModal(true);
        pack();
        setTitle("所有玩家资产情况");
        setLocationRelativeTo(null);
    }
}
