package gui;

import gui.map.MapViewer;
import monopoly.Kernel;
import monopoly.Player;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Mengxiao Lin on 2016/6/9.
 */
public class GameFrame extends JFrame{
    private MapViewer mapViewer;
    private JButton nextPlayerBtn;
    private FormItemTextField playerNameTextField;
    private FormItemTextField cashTextField;
    private JPanel buildNameTextField(){
        playerNameTextField = new FormItemTextField("玩家名");
        playerNameTextField.setEnabled(false);
        playerNameTextField.getTextField().setHorizontalAlignment(JTextField.CENTER);
        playerNameTextField.getTextField().setFont(new Font("Times New Roman", Font.BOLD,20));
        return playerNameTextField;
    }

    private JPanel buildPlayerInformationBoard(){
        JPanel ret = new JPanel(new BorderLayout());
        nextPlayerBtn = new JButton("投骰子结束我的回合！");
        JPanel topPanel =new JPanel(new GridLayout(2,1));
        cashTextField = new FormItemTextField("现金：");
        cashTextField.setEnabled(false);

        topPanel.add(buildNameTextField());
        topPanel.add(cashTextField);
        ret.add(topPanel, BorderLayout.NORTH);
        ret.add(nextPlayerBtn, BorderLayout.SOUTH);
        return ret;
    }

    public GameFrame() {
        setLayout(new BorderLayout());
        mapViewer = new MapViewer();
        mapViewer.addCellClickedListener(cell->{
            final StringBuilder informationStr = new StringBuilder();
            cell.getCellInformation().forEach(d->{
                informationStr.append(d.getFirst()).append(": ").append(d.getSecond()).append("\n");
            });
            JOptionPane.showConfirmDialog(GameFrame.this, informationStr.toString(), "Cell Info",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
        });

        add(mapViewer, BorderLayout.CENTER);
        add(buildPlayerInformationBoard(), BorderLayout.EAST);
        showPlayer(Kernel.getInstance().getCurrentPlayer());
        pack();
        setTitle("Monopoly 2");
        setLocationRelativeTo(null);
    }

    public void showPlayer(Player player){
        playerNameTextField.setText(player.getName());
        playerNameTextField.getTextField().setDisabledTextColor(Constant.PLAYER_COLOR[player.getId()-1]);
        cashTextField.setText(String.format("%.2f",player.getMoney()));
    }
}
