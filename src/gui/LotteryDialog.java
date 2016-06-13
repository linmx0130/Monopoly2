package gui;

import monopoly.Kernel;
import monopoly.Player;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Created by Mengxiao Lin on 2016/6/12.
 */
public class LotteryDialog extends JDialog {
    private JComboBox<Integer> numberToBuy;
    private JSpinner stakeSpinner;
    private Player player;
    private JLabel tips;
    private void randomChoose(){
        Random random = new Random();
        int choose = random.nextInt()%10;
        if (choose<0) choose+=10;
        numberToBuy.setSelectedIndex(choose);
    }
    private JPanel buildNumberComboBox(){
        numberToBuy = new JComboBox<>();
        for (int i=1;i<=10;++i){
            numberToBuy.addItem(i);
        }
        randomChoose();
        JPanel ret = new JPanel(new BorderLayout());
        ret.add(new JLabel("选择的数字"),BorderLayout.WEST);
        ret.add(numberToBuy,BorderLayout.CENTER);
        return ret;
    }
    private JPanel buildStakeSpinner(){
        stakeSpinner = new JSpinner();
        JSpinner.NumberEditor stakeSpinnerEditor = new JSpinner.NumberEditor(stakeSpinner);
        stakeSpinner.setEditor(stakeSpinnerEditor);
        stakeSpinner.setValue(100);
        stakeSpinnerEditor.getModel().setStepSize(50);
        stakeSpinnerEditor.getModel().setMinimum(0);
        JPanel ret = new JPanel(new BorderLayout());
        ret.add(new JLabel("下注金额"),BorderLayout.WEST);
        ret.add(stakeSpinner,BorderLayout.CENTER);
        return ret;
    }
    private void loadTips(){
        tips.setText("您现在有现金"+String.format("%.2f",player.getMoney())+"元，请量力购买。");
    }
    public LotteryDialog(Player player) {
        setLayout(new BorderLayout());
        this.player = player;
        tips = new JLabel();
        loadTips();
        JPanel contentPanel = new JPanel(new GridLayout(2,1));
        contentPanel.add(buildNumberComboBox());
        contentPanel.add(buildStakeSpinner());
        setLocationRelativeTo(null);
        add(contentPanel, BorderLayout.CENTER);
        JPanel btnPanel = new JPanel(new GridLayout(1,2));
        JButton buyBtn = new JButton("购买");
        buyBtn.addActionListener(e->{
            double value = (Double)stakeSpinner.getValue();
            int choose = numberToBuy.getItemAt(numberToBuy.getSelectedIndex());
            if (player.charge(value)){
                Kernel.getInstance().getLottery().buyLottery(player,choose ,value);
                JOptionPane.showConfirmDialog(LotteryDialog.this, "成功购买彩票！号码为"+choose+"，下注额度为"+value,
                        "彩票", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                loadTips();
            }else{
                JOptionPane.showConfirmDialog(LotteryDialog.this, "现金不足，无法购买！","彩票",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
            }
        });
        JButton cancelBtn = new JButton("退出");
        cancelBtn.addActionListener(e-> LotteryDialog.this.setVisible(false));
        btnPanel.add(buyBtn);
        btnPanel.add(cancelBtn);
        add(btnPanel, BorderLayout.SOUTH);
        pack();
        setModal(true);
        setTitle("彩票");
    }
}
