package gui;

import monopoly.Kernel;
import monopoly.stock.Stock;
import monopoly.stock.StockMarket;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.RoundingMode;
import java.util.Map;

/**
 * Created by Mengxiao Lin on 2016/6/18.
 */
public class StockFrame extends JDialog {
    private JTable stockTable;
    private TableModel stockTableModel;
    private JTextArea statusTips;
    private JButton buyBtn, sellBtn;
    private void updateStockTableModal() {
        StockMarket market = Kernel.getInstance().getStockMarket();
        java.util.List<Stock> stockList = market.getStocks();
        Map<Stock, Integer> stockHold = market.getStockHold(Kernel.getInstance().getCurrentPlayer());
        String columns[] = {"序号", "公司名", "每股价格", "昨日涨跌", "持股量"};
        String data[][] = new String[stockList.size()][5];
        for (int i = 0; i < market.getStocks().size(); ++i) {
            Stock stock = market.getStocks().get(i);
            data[i][0] = String.valueOf(stock.getId());
            data[i][1] = stock.getName();
            data[i][2] = String.format("%.2f", stock.getPrice());
            data[i][3] = String.format("%.2f", stock.getFloatRate());
            data[i][4] = stockHold.containsKey(stock) ? stockHold.get(stock).toString() : "0";
        }
        stockTableModel = new TableModel() {
            @Override
            public int getRowCount() {
                return data.length;
            }

            @Override
            public int getColumnCount() {
                return columns.length;
            }

            @Override
            public String getColumnName(int columnIndex) {
                return columns[columnIndex];
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columns[columnIndex].getClass();
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
                data[rowIndex][columnIndex]= aValue.toString();
            }

            @Override
            public void addTableModelListener(TableModelListener l) {

            }

            @Override
            public void removeTableModelListener(TableModelListener l) {

            }
        };
        stockTable.setModel(stockTableModel);
    }
    private void updateOperatorPanel(){
        if (stockTable.getSelectedRowCount() == 0) {
            statusTips.setText("您现在有现金："+
                    String.format("%.2f", Kernel.getInstance().getCurrentPlayer().getMoney())
                    +"元。\n股市有风险，投资需谨慎。请通过下方操作盘买入或卖出股票。");
            sellBtn.setEnabled(false);
            buyBtn.setEnabled(false);
            return ;
        }
        int selectedStockId = Integer.parseInt((String)stockTableModel.getValueAt(stockTable.getSelectedRow(),0));
        Stock stock = Kernel.getInstance().getStockMarket().getStocks().stream()
                .filter(s-> s.getId() == selectedStockId).findFirst().get();
        statusTips.setText("股票"+stock.getName()+"目前价格为每股"+
                        String.format("%.2f",stock.getPrice())+
                        "，您有现金"+
                        String.format("%.2f",Kernel.getInstance().getCurrentPlayer().getMoney())+"元，可以购买最多"+
                        (int)(Kernel.getInstance().getCurrentPlayer().getMoney()/stock.getPrice())+ "股。");
        sellBtn.setEnabled(true);
        buyBtn.setEnabled(true);
    }
    private void buildTables(){
        stockTable = new JTable();
        updateStockTableModal();
        stockTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        stockTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        stockTable.setPreferredScrollableViewportSize(new Dimension(400,200));
        stockTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                updateOperatorPanel();
            }
        });
        stockTable.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                updateOperatorPanel();
            }
        });
    }
    private JPanel buildOperatingPanel(){
        JPanel ret = new JPanel(new BorderLayout());
        statusTips = new JTextArea();
        statusTips.setEditable(false);
        statusTips.setLineWrap(true);
        statusTips.setBackground(ret.getBackground());
        ret.add(statusTips,BorderLayout.CENTER);

        JPanel operatorSpinPanel = new JPanel(new BorderLayout());
        operatorSpinPanel.add(new JLabel("买入/卖出数量："),BorderLayout.WEST);
        JSpinner spinner = new JSpinner();
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner);
        spinner.setEditor(editor);
        editor.getModel().setMinimum(0);
        editor.getModel().setStepSize(10);
        editor.getModel().setValue(0);
        editor.getFormat().setRoundingMode(RoundingMode.CEILING);
        operatorSpinPanel.add(spinner,BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new GridLayout(3,1));
        btnPanel.add(operatorSpinPanel);
        buyBtn = new JButton("买入");
        sellBtn = new JButton("卖出");
        btnPanel.add(buyBtn);
        btnPanel.add(sellBtn);
        ret.add(btnPanel,BorderLayout.SOUTH);

        buyBtn.addActionListener(e-> {
            if (stockTable.getSelectedRowCount() == 0) return;
            Kernel.getInstance().getStockMarket().buyStock(
                    Kernel.getInstance().getCurrentPlayer(),
                    Integer.parseInt((String) stockTableModel.getValueAt(stockTable.getSelectedRow(), 0)),
                    (Integer) spinner.getValue()
            );
            updateStockTableModal();
            updateOperatorPanel();
        });
        sellBtn.addActionListener(e->{
            if (stockTable.getSelectedRowCount() == 0) return;
            Kernel.getInstance().getStockMarket().sellStock(
                Kernel.getInstance().getCurrentPlayer(),
                Integer.parseInt((String)stockTableModel.getValueAt(stockTable.getSelectedRow(),0)),
                (Integer)spinner.getValue()
            );
            updateStockTableModal();
            updateOperatorPanel();
        });
        return ret;
    }
    public StockFrame() {
        setLayout(new BorderLayout());
        buildTables();
        JScrollPane paneStock = new JScrollPane(stockTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(paneStock, BorderLayout.CENTER);
        add(buildOperatingPanel(), BorderLayout.SOUTH);
        updateOperatorPanel();
        stockTable.setFillsViewportHeight(true);
        pack();
        setTitle("股市");
        setModal(true);
        setLocationRelativeTo(null);
    }
}
