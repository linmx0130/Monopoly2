package gui;

import gui.map.MapViewer;
import gui.message.PlayerMessagePipeImpl;
import monopoly.Kernel;
import monopoly.Player;
import monopoly.card.AbstractCard;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Created by Mengxiao Lin on 2016/6/9.
 */
public class GameFrame extends JFrame{
    private MapViewer mapViewer;
    private FormItemTextField playerNameTextField;
    private FormItemTextField cashTextField;
    private FormItemTextField dateField;
    private DiceShower diceShower;
    private JPanel buildNameTextField(){
        playerNameTextField = new FormItemTextField("玩家名");
        playerNameTextField.setEnabled(false);
        playerNameTextField.getTextField().setHorizontalAlignment(JTextField.CENTER);
        playerNameTextField.getTextField().setFont(new Font("Times New Roman", Font.BOLD,20));
        return playerNameTextField;
    }
    private JPanel buildBottomBtnPanel(){
        JButton nextPlayerBtn = new JButton("投骰子结束我的回合！");
        JButton useCardBtn = new JButton("使用道具卡");
        JButton stockBtn = new JButton("进入股市");
        JButton giveUpBtn = new JButton("认输");
        JPanel bottomBtnPanel =new JPanel(new GridLayout(4,1));
        bottomBtnPanel.add(useCardBtn);
        bottomBtnPanel.add(stockBtn);
        bottomBtnPanel.add(giveUpBtn);
        bottomBtnPanel.add(nextPlayerBtn);
        useCardBtn.addActionListener(_e->{
            AbstractCard cardToUse = UseCardDialog.showUseCardDialog(Kernel.getInstance().getCurrentPlayer());
            Player object = null;
            if (cardToUse == null) return;
            if (cardToUse.needObject()){
                object = ChoosePlayerDialog.showChoosePlayerDialog(true);
                if (object==null){
                    JOptionPane.showConfirmDialog(GameFrame.this, "放弃使用效果卡！", "Monopoly2",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    return ;
                }
            }
            if (!cardToUse.canBeUse(Kernel.getInstance().getCurrentPlayer(),object)){
                JOptionPane.showConfirmDialog(GameFrame.this, "这张卡使用条件不满足，无法使用！", "Monopoly2",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                return ;
            }
            if (Kernel.getInstance().issueCard(cardToUse, Kernel.getInstance().getCurrentPlayer(), object)){
                Kernel.getInstance().getCurrentPlayer().getCards().remove(cardToUse);
                JOptionPane.showConfirmDialog(GameFrame.this, cardToUse.getName()+" 发动成功！", "使用道具卡",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showConfirmDialog(GameFrame.this, cardToUse.getName()+" 使用失败！", "使用道具卡",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            }
            mapViewer.repaint();
        });
        nextPlayerBtn.addActionListener(e->{
            Thread t = new Thread(()->{
                nextPlayerBtn.setEnabled(false);
                Kernel kernel = Kernel.getInstance();
                Random random=new Random();
                int step = random.nextInt(6)+1;
                if (kernel.getNextDiceValue()!= null){
                    step = kernel.getNextDiceValue();
                    kernel.setNextDiceValue(null);
                }
                diceShower.setCurrentNumber(step);

                for (int i=1 ;i<step ;++i) {
                    if (kernel.hasBlockOnNextPosition()) {
                        JOptionPane.showConfirmDialog(this,":( 遇到路障，停止前进！","Monopoly2",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
                        break;
                    }
                    kernel.playerMoveWithoutEffect();
                    mapViewer.repaint();
                    kernel.playerMoveEffect();
                    showCurrentPlayer();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
                kernel.playerMoveEndWithoutEffect();
                mapViewer.repaint();
                kernel.playerMoveEndEffect();
                mapViewer.repaint();
                ((PlayerMessagePipeImpl)kernel.getMessagePipe()).setActive(false);
                nextPlayerBtn.setEnabled(true);
                do {
                    kernel.nextPlayer();
                    showCurrentPlayer();
                    if (kernel.getCurrentPlayer().getPauseTurn()!=0){
                        kernel.getCurrentPlayer().setPauseTurn(kernel.getCurrentPlayer().getPauseTurn()-1);
                        JOptionPane.showConfirmDialog(GameFrame.this,
                                "玩家"+kernel.getCurrentPlayer().getName()+"受伤，跳过本回合！",
                                "Monopoly 2",JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        break;
                    }
                }while (true);
            });
            t.start();
        });
        giveUpBtn.addActionListener(e->{
            int answer = JOptionPane.showConfirmDialog(GameFrame.this, "您确定认输吗?","Monopoly 2",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (answer == JOptionPane.YES_OPTION) {
                Kernel.getInstance().giveUpGame();
                Kernel.getInstance().nextPlayer();
                showCurrentPlayer();
            }
        });
        stockBtn.addActionListener(e->{
            StockFrame dialog = new StockFrame();
            dialog.setVisible(true);
        });
        return bottomBtnPanel;
    }
    private JPanel buildPlayerInformationBoard(){
        JPanel ret = new JPanel(new BorderLayout());

        JPanel topPanel =new JPanel(new GridLayout(3,1));
        cashTextField = new FormItemTextField("现金：");
        cashTextField.setEnabled(false);
        dateField = new FormItemTextField("当前日期：");
        dateField.setEnabled(false);
        diceShower = new DiceShower();
        topPanel.add(buildNameTextField());
        topPanel.add(cashTextField);
        topPanel.add(dateField);

        JPanel upPanel = new JPanel(new BorderLayout());
        upPanel.add(topPanel, BorderLayout.NORTH);
        upPanel.add(diceShower, BorderLayout.CENTER);
        ret.add(upPanel, BorderLayout.NORTH);
        ret.add(buildBottomBtnPanel(), BorderLayout.SOUTH);
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
        showCurrentPlayer();
        pack();
        setTitle("Monopoly 2");
        setLocationRelativeTo(null);
    }

    public void showCurrentPlayer(){
        Player player = Kernel.getInstance().getCurrentPlayer();
        playerNameTextField.setText(player.getName());
        playerNameTextField.getTextField().setDisabledTextColor(Constant.PLAYER_COLOR[player.getId()-1]);
        cashTextField.setText(String.format("%.2f",player.getMoney()));
        ((PlayerMessagePipeImpl)Kernel.getInstance().getMessagePipe()).setActive(true);
        dateField.setText(Kernel.getInstance().getCurrentDate().toString());
    }
}
