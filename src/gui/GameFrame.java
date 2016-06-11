package gui;

import gui.map.MapViewer;
import gui.message.PlayerMessagePipeImpl;
import monopoly.Kernel;
import monopoly.Player;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Created by Mengxiao Lin on 2016/6/9.
 */
public class GameFrame extends JFrame{
    private MapViewer mapViewer;
    private JButton nextPlayerBtn;
    private FormItemTextField playerNameTextField;
    private FormItemTextField cashTextField;
    private DiceShower diceShower;
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
        diceShower = new DiceShower();
        topPanel.add(buildNameTextField());
        topPanel.add(cashTextField);

        JPanel upPanel = new JPanel(new BorderLayout());
        upPanel.add(topPanel, BorderLayout.NORTH);
        upPanel.add(diceShower, BorderLayout.CENTER);

        ret.add(upPanel, BorderLayout.NORTH);
        ret.add(nextPlayerBtn, BorderLayout.SOUTH);

        nextPlayerBtn.addActionListener(e->{
            Thread t = new Thread(()->{
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
                        System.out.println(":( 遇到路障，停止前进！");
                        break;
                    }
                    kernel.playerMoveWithoutEffect();
                    mapViewer.repaint();
                    kernel.playerMoveEffect();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
                kernel.playerMoveEndWithoutEffect();
                mapViewer.repaint();
                kernel.playerMoveEndEffect();
                ((PlayerMessagePipeImpl)kernel.getMessagePipe()).setActive(false);
                kernel.nextPlayer();

            });
            t.start();
        });
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
    }
}
