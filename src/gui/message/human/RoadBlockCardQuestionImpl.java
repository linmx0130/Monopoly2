package gui.message.human;

import gui.map.MapViewer;
import message.RoadBlockCardQuestion;
import monopoly.GameMap;
import monopoly.Kernel;
import monopoly.cell.AbstractCell;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Mengxiao Lin on 2016/6/15.
 */
public class RoadBlockCardQuestionImpl extends RoadBlockCardQuestion {
    private AbstractCell cellChosen;
    private JLabel tipsLabel;
    private JButton okBtn;
    private void cancelUse(){
        setChoose(-9);
        //since the choose < -8 is not allowed
    }
    @Override
    public void action() {
        RoadBlockCardDialog dialog = new RoadBlockCardDialog();
        dialog.setVisible(true);
    }
    private class RoadBlockCardDialog extends JDialog{
        private MapViewer mapViewer;
        private JPanel buildBtnPanel(){
            JPanel ret = new JPanel(new BorderLayout());
            JPanel btnPanel = new JPanel(new GridLayout(1,2));
            JButton cancelBtn = new JButton("取消");
            cancelBtn.addActionListener(e->{
                cancelUse();
                setVisible(false);
            });
            okBtn = new JButton("确定");
            okBtn.addActionListener(e->{
                if (cellChosen == null){
                    JOptionPane.showConfirmDialog(RoadBlockCardDialog.this,"请选择一个位置放置路障！","路障卡",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                    return ;
                }
                setVisible(false);
            });
            okBtn.setEnabled(false);
            btnPanel.add(cancelBtn);
            btnPanel.add(okBtn);
            ret.add(btnPanel,BorderLayout.EAST);
            return ret;
        }
        RoadBlockCardDialog() {
            mapViewer = new MapViewer();
            setLayout(new BorderLayout());
            tipsLabel = new JLabel("请选择一个位置安放路障");
            add(tipsLabel,BorderLayout.NORTH);
            add(mapViewer, BorderLayout.CENTER);
            mapViewer.addCellClickedListener(cellBeClicked -> {
                GameMap gameMap = Kernel.getInstance().getGameMap();
                int dis = gameMap.getDistanceBetweenCells(cellBeClicked, gameMap.getPlayerPosition(getSubject()));
                if (dis > 8) {
                    JOptionPane.showConfirmDialog(RoadBlockCardDialog.this, "只能选择8步以内的位置", "路障卡",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
                    return ;
                }
                cellChosen = cellBeClicked;
                okBtn.setEnabled(true);
                tipsLabel.setText("您选择了："+cellChosen.getName());
            });
            add(buildBtnPanel(), BorderLayout.SOUTH);
            pack();
            setModal(true);
            setTitle("路障卡");
            setLocationRelativeTo(null);
        }
    }

    @Override
    public AbstractCell getCellChosen() {
        return cellChosen;
    }
}
