package gui;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by Mengxiao Lin on 2016/6/7.
 */
public class WelcomeFrame extends JFrame {
    private JButton startGameBtn, quitGameBtn, aboutBtn;
    private JPanel createBtnPanel(){
        JPanel ret = new JPanel(new GridLayout(3,1));
        startGameBtn = new JButton("开始游戏");
        aboutBtn = new JButton("关于");
        quitGameBtn = new JButton("退出游戏");
        ret.add(startGameBtn);
        ret.add(aboutBtn);
        ret.add(quitGameBtn);
        quitGameBtn.addActionListener(e-> System.exit(0));
        startGameBtn.addActionListener(e->{
            setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
            GameSetupFrame f = new GameSetupFrame(WelcomeFrame.this);
            f.setLocationRelativeTo(WelcomeFrame.this);
            f.setVisible(true);
            WelcomeFrame.this.setVisible(false);
        });
        aboutBtn.addActionListener(e->{
            AboutDialog dialog = new AboutDialog();
            dialog.setVisible(true);
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
    private class AboutDialog extends JDialog{
        public AboutDialog() {
            setLayout(new BorderLayout());
            JTextArea textArea = new JTextArea();
            textArea.setLineWrap(false);
            textArea.setEditable(false);
            JScrollPane pane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS ,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            add(pane,BorderLayout.CENTER);

            JPanel btnPanel = new JPanel(new BorderLayout());
            JButton okBtn = new JButton("关闭");
            okBtn.addActionListener(e-> setVisible(false));
            btnPanel.add(okBtn,BorderLayout.EAST);
            add(btnPanel,BorderLayout.SOUTH);
            setTitle("关于");
            //load data
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/copyright.txt"),"utf-8"));
                String buf;
                StringBuilder total = new StringBuilder();
                do{
                    buf = reader.readLine();
                    if (buf == null) break;
                    total.append(buf);
                    total.append("\n");
                } while (true);
                textArea.setText(total.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            pack();
            setLocationRelativeTo(null);
            setModal(true);
        }
    }
}
