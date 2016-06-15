package gui;

import monopoly.Kernel;
import monopoly.Player;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Mengxiao Lin on 2016/6/14.
 */
public class ChoosePlayerDialog extends JDialog {
    private Player playerChosen;
    private JComboBox<PlayerAdapter> playerComboBox;
    class PlayerAdapter{
        Player player;

        public PlayerAdapter(Player player) {
            this.player = player;
        }

        @Override
        public String toString() {
            return player.getName();
        }
    }
    private JPanel buildBtnPanel(boolean showCancelBtn){
        JPanel ret;
        if (showCancelBtn) {
            ret = new JPanel(new GridLayout(2, 1));
            JButton cancelBtn = new JButton("取消");
            cancelBtn.addActionListener(_e-> setVisible(false));
            ret.add(cancelBtn);
        }else{
            ret = new JPanel(new GridLayout(1,1));
        }
        JButton okBtn = new JButton("确定");
        ret.add(okBtn);
        okBtn.addActionListener(_e->{
            playerChosen = ((PlayerAdapter)playerComboBox.getSelectedItem()).player;
            setVisible(false);
        });
        return ret;
    }
    private ChoosePlayerDialog(boolean showCancelBtn) {
        setLayout(new BorderLayout());
        playerComboBox = new JComboBox<>();
        Kernel.getInstance().getPlayers().forEach(p -> playerComboBox.addItem(new PlayerAdapter(p)));
        add(new JLabel("请选择一个玩家。"),BorderLayout.NORTH);
        add(playerComboBox, BorderLayout.CENTER);
        add(buildBtnPanel(showCancelBtn), BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
        setTitle("选择玩家");
        setModal(true);
    }

    public static Player showChoosePlayerDialog(boolean showCancelBtn){
        ChoosePlayerDialog dialog = new ChoosePlayerDialog(showCancelBtn);
        dialog.setVisible(true);
        return dialog.playerChosen;
    }
}
