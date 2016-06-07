package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Mengxiao Lin on 2016/6/7.
 */
public class WelcomeFrame extends JFrame {
    private JButton startGameBtn, quitGameBtn, mapEditorBtn;
    private JPanel createBtnPanel(){
        JPanel ret = new JPanel(new GridLayout(3,1));
        startGameBtn = new JButton("开始游戏");
        mapEditorBtn = new JButton("地图编辑器");
        quitGameBtn = new JButton("退出游戏");
        ret.add(startGameBtn);
        ret.add(mapEditorBtn);
        ret.add(quitGameBtn);
        quitGameBtn.addActionListener(e-> System.exit(0));
        startGameBtn.addActionListener(e->{
            setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
            GameSetupFrame f = new GameSetupFrame(WelcomeFrame.this);
            f.setLocationRelativeTo(WelcomeFrame.this);
            f.setVisible(true);
            WelcomeFrame.this.setVisible(false);
        });
        return ret;
    }
    public WelcomeFrame() {
        setLayout(new BorderLayout());
        add(createBtnPanel(),BorderLayout.SOUTH);
        pack();
        setTitle("Monopoly 2");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
