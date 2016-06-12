package gui;

import monopoly.Bank;
import monopoly.Kernel;
import monopoly.Player;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Mengxiao Lin on 2016/6/12.
 */
public class BankDialog extends JDialog {
    private JTextArea tipsLabel;
    private Player player;
    private Bank bank;
    public BankDialog(Player player) {
        this.player = player;
        bank = Kernel.getInstance().getBank();
        setLayout(new BorderLayout());
        setTitle("银行");
        tipsLabel = new JTextArea();
        tipsLabel.setEditable(false);
        setModal(true);
        add(tipsLabel, BorderLayout.NORTH);

        JPanel btnPanel = new JPanel(new GridLayout(3,1));
        JButton storeBtn = new JButton("存款");
        storeBtn.addActionListener(e->{
            final String info =  "请输入存款的金额（0~"+ String.format("%.2f",player.getMoney())+"）。";
            final String input= JOptionPane.showInputDialog(BankDialog.this, info, "银行", JOptionPane.QUESTION_MESSAGE);
            try {
                final double value = Double.parseDouble(input);
                if (value<0) throw new NumberFormatException();
                if (value > player.getMoney()){
                    JOptionPane.showConfirmDialog(BankDialog.this,"现金不足，无法存款！","银行",JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                    return ;
                }
                bank.storeMoney(player,value);
                loadInformation();
            } catch (NumberFormatException ex){
                JOptionPane.showConfirmDialog(BankDialog.this,"输入错误，无法存款！","银行",JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                return ;
            }

        });
        JButton withdrawBtn = new JButton("取款");
        withdrawBtn.addActionListener(e->{
            double deposit = bank.getDeposit(player);
            final String info =  "请输入取款的金额（0~"+ String.format("%.2f",deposit)+"）。";
            final String input= JOptionPane.showInputDialog(BankDialog.this, info, "银行", JOptionPane.QUESTION_MESSAGE);
            try {
                final double value = Double.parseDouble(input);
                if (value<0) throw new NumberFormatException();
                if (value > deposit){
                    JOptionPane.showConfirmDialog(BankDialog.this,"存款不足，无法取款！","银行",JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                    return ;
                }
                bank.withdraw(player,value);
                loadInformation();
            } catch (NumberFormatException ex){
                JOptionPane.showConfirmDialog(BankDialog.this,"输入错误，无法取款！","银行",JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                return ;
            }
        });
        JButton exitBtn = new JButton("退出");
        exitBtn.addActionListener(e->{
            BankDialog.this.setVisible(false);
        });
        btnPanel.add(storeBtn);
        btnPanel.add(withdrawBtn);
        btnPanel.add(exitBtn);
        add(btnPanel, BorderLayout.CENTER);
        loadInformation();
        setLocationRelativeTo(null);
        pack();
    }
    private void loadInformation(){
        String tips = player.getName()+"，您好！" + "\n";
        tips+="您现在有现金 ";
        tips+= String.format("%.2f",player.getMoney());
        tips+=" 元，存款 ";
        tips+=String.format("%.2f", bank.getDeposit(player));
        tips+="。";
        tipsLabel.setText(tips);
    }

}
