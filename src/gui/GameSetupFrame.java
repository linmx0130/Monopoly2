package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Mengxiao Lin on 2016/6/7.
 */
public class GameSetupFrame extends JFrame {
    private JSpinner startDateSpinner;
    private JList<String> playerList;
    private DefaultListModel<String> playerListModel;
    private JTextField newPlayerNameTextField;
    private JFrame parentFrame;

    private void closeAndReturnWelcome() {
        parentFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        parentFrame.setVisible(true);
        this.setVisible(false);
    }

    private JPanel createBtnPanel() {
        JButton cancelBtn, startGameBtn;
        JPanel ret = new JPanel(new BorderLayout());
        JPanel btnPanel = new JPanel(new GridLayout());
        cancelBtn = new JButton("取消");
        cancelBtn.addActionListener(e -> closeAndReturnWelcome());
        startGameBtn = new JButton("开始游戏");
        btnPanel.add(cancelBtn);
        btnPanel.add(startGameBtn);
        ret.add(btnPanel, BorderLayout.EAST);
        return ret;
    }

    private JPanel createTopPanel() {
        JPanel ret = new JPanel(new BorderLayout());
        startDateSpinner = new JSpinner();
        startDateSpinner.setModel(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(startDateSpinner, "yyyy-MM-dd");
        startDateSpinner.setEditor(dateEditor);
        ret.add(new JLabel("游戏开始日期："), BorderLayout.WEST);
        ret.add(startDateSpinner, BorderLayout.CENTER);
        return ret;
    }

    private JPanel createPlayerListPanel() {
        JPanel ret = new JPanel(new BorderLayout());
        playerList = new JList<>();
        playerListModel = new DefaultListModel<>();
        playerList.setModel(playerListModel);
        playerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ret.add(playerList, BorderLayout.CENTER);

        JPanel newPlayerPanel = new JPanel(new BorderLayout());
        newPlayerPanel.add(new JLabel("玩家名字："), BorderLayout.WEST);
        newPlayerNameTextField = new JTextField();
        newPlayerPanel.add(newPlayerNameTextField);
        JButton addPlayerBtn = new JButton("添加玩家");
        newPlayerPanel.add(addPlayerBtn, BorderLayout.EAST);
        ret.add(newPlayerPanel, BorderLayout.NORTH);

        addPlayerBtn.addActionListener(e -> {
            playerListModel.addElement(newPlayerNameTextField.getText());
            newPlayerNameTextField.setText("");
        });

        JPopupMenu listPopupMenu = new JPopupMenu();
        JMenuItem removeBtn = new JMenuItem("删除");
        JMenuItem moveUpBtn = new JMenuItem("上移");
        JMenuItem moveDownBtn = new JMenuItem("下移");
        removeBtn.addActionListener(e -> {
            if (playerList.isSelectionEmpty()) return;
            int selectedIndex = playerList.getSelectedIndex();
            playerListModel.remove(selectedIndex);
        });
        moveUpBtn.addActionListener(e -> {
            if (playerList.isSelectionEmpty()) return;
            int selectedIndex = playerList.getSelectedIndex();
            if (selectedIndex == 0) return;
            int targetIndex = selectedIndex - 1;
            String itemToMove = playerListModel.get(selectedIndex);
            playerListModel.remove(selectedIndex);
            playerListModel.add(targetIndex, itemToMove);
        });
        moveDownBtn.addActionListener(e -> {
            if (playerList.isSelectionEmpty()) return;
            int selectedIndex = playerList.getSelectedIndex();
            if (selectedIndex == playerListModel.size() - 1) return;
            int targetIndex = selectedIndex + 1;
            String itemToMove = playerListModel.get(selectedIndex);
            playerListModel.remove(selectedIndex);
            playerListModel.add(targetIndex, itemToMove);
        });
        listPopupMenu.add(removeBtn);
        listPopupMenu.add(moveUpBtn);
        listPopupMenu.add(moveDownBtn);
        playerList.setComponentPopupMenu(listPopupMenu);
        return ret;
    }

    public GameSetupFrame(JFrame parentFrame) {
        setLayout(new BorderLayout());
        this.parentFrame = parentFrame;
        add(createTopPanel(), BorderLayout.NORTH);
        add(createBtnPanel(), BorderLayout.SOUTH);
        add(createPlayerListPanel(), BorderLayout.CENTER);
        pack();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                closeAndReturnWelcome();
            }
        });
        setMinimumSize(new Dimension(450, 300));
        setTitle("设置游戏");
    }
}
