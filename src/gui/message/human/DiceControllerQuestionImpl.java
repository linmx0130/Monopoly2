package gui.message.human;

import message.DiceControllerQuestion;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Mengxiao Lin on 2016/6/17.
 */
public class DiceControllerQuestionImpl extends DiceControllerQuestion {
    private class DiceControllerDialog extends JDialog {
        private JComboBox<Integer> choice;
        public DiceControllerDialog() {
            setLayout(new BorderLayout());
            add(new JLabel("请选择下一步骰子的数值："),BorderLayout.NORTH);
            choice = new JComboBox<>();
            for (int i=1;i<=6;++i) choice.addItem(i);
            add(choice, BorderLayout.CENTER);
            JPanel btnPanel = new JPanel(new GridLayout(1,2));
            JButton cancelBtn = new JButton("取消");
            JButton okBtn = new JButton("确认");
            btnPanel.add(cancelBtn);
            btnPanel.add(okBtn);
            cancelBtn.addActionListener(e->{
                setChoose(null);
                setVisible(false);
            });
            okBtn.addActionListener( e->{
                setChoose((Integer)choice.getSelectedItem());
                setVisible(false);
            });
            add(btnPanel, BorderLayout.SOUTH);
            pack();
            setModal(true);
            setLocationRelativeTo(null);
        }
    }
    @Override
    public void action() {
        DiceControllerDialog dialog = new DiceControllerDialog();
        dialog.setVisible(true);
    }
}
