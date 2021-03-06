package gui;

import gui.message.*;
import message.MessageFactory;
import message.MessagePipe;
import monopoly.Date;
import monopoly.Kernel;
import monopoly.Pair;
import monopoly.Player;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Mengxiao Lin on 2016/6/7.
 */
public class GameSetupFrame extends JFrame {
    class PlayerNameAdapter{
        String name;
        boolean isAI;

        public PlayerNameAdapter(String name, boolean isAI) {
            this.name = name;
            this.isAI = isAI;
        }

        @Override
        public String toString() {
            if (isAI) return name+"(AI)";
            return name;
        }
    }
    private JSpinner startDateSpinner;
    private JComboBox<String> mapComboBox;
    private JList<PlayerNameAdapter> playerList;
    private DefaultListModel<PlayerNameAdapter> playerListModel;
    private JTextField newPlayerNameTextField;
    private JFrame parentFrame;
    //private JCheckBox isAICheckBox;
    private ArrayList<Pair<String, String>> mapList;

    private void closeAndReturnWelcome() {
        parentFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        parentFrame.setVisible(true);
        this.setVisible(false);
    }

    private void setUpKernel(){
        Kernel.createInstance(playerListModel.size());
        Kernel kernel = Kernel.getInstance();
        java.util.Date userInputDate = (java.util.Date)startDateSpinner.getValue();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(userInputDate);
        Date startDate = new Date(calendar.get(Calendar.YEAR),
                                  calendar.get(Calendar.MONTH)+1,
                                  calendar.get(Calendar.DAY_OF_MONTH));
        kernel.setCurrentDate(startDate);
        //load map
        String mapFilename = mapList.get(mapComboBox.getSelectedIndex()).getSecond();
        kernel.getGameMap().loadMapFromStream(getClass().getResourceAsStream("/map/"+mapFilename));
        //add player
        for (int i=0;i<playerListModel.size();++i){
            PlayerNameAdapter adapter = playerListModel.get(i);
            Player player = new Player(adapter.name);
            player.setId(i+1);
            MessagePipe messagePipe;
            MessageFactory messageFactory;
            if (adapter.isAI){
                messagePipe = new AIMessagePipeImpl();
                messageFactory = new AIMessageFactoryImpl();
            }else{
                messagePipe = new HumanMessagePipeImpl();
                messageFactory = new HumanMessageFactoryImpl();
            }
            Kernel.getInstance().addPlayer(player, messageFactory, new PlayerMessagePipeImpl(messagePipe));
        }
        Kernel.getInstance().initPlayers();
    }

    private ArrayList<Pair<String,String>> loadMapList(){
        ArrayList<Pair<String, String>> ret = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/map/maplist.json"), "utf-8"));
            StringBuilder buffer = new StringBuilder();
            do {
                String line = bufferedReader.readLine();
                if (line == null) break;
                buffer.append(line);
            }while (true);
            JSONObject mapListObj = new JSONObject(buffer.toString());
            JSONArray mapList = mapListObj.getJSONArray("maplist");
            for (int i=0;i<mapList.length();++i){
                JSONObject item = mapList.getJSONObject(i);
                ret.add(new Pair<>(item.getString("name"),item.getString("file")));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    private JPanel createBtnPanel() {
        JButton cancelBtn, startGameBtn;
        JPanel ret = new JPanel(new BorderLayout());
        JPanel btnPanel = new JPanel(new GridLayout());
        cancelBtn = new JButton("取消");
        cancelBtn.addActionListener(e -> closeAndReturnWelcome());
        startGameBtn = new JButton("开始游戏");
        startGameBtn.addActionListener(e->{
            if (playerListModel.size() <2){
                JOptionPane.showConfirmDialog(GameSetupFrame.this,"玩家太少，无法开始游戏！", "Monopoly 2",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
                return ;
            }
            setUpKernel();
            GameFrame f = new GameFrame();
            f.setVisible(true);
            setVisible(false);
        });
        btnPanel.add(cancelBtn);
        btnPanel.add(startGameBtn);
        ret.add(btnPanel, BorderLayout.EAST);
        return ret;
    }

    private JPanel createTopPanel() {
        JPanel startDateBox = new JPanel(new BorderLayout());
        startDateSpinner = new JSpinner();
        startDateSpinner.setModel(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(startDateSpinner, "yyyy-MM-dd");
        startDateSpinner.setEditor(dateEditor);
        startDateBox.add(new JLabel("游戏开始日期："), BorderLayout.WEST);
        startDateBox.add(startDateSpinner, BorderLayout.CENTER);
        JPanel chooseMapBox = new JPanel(new BorderLayout());
        chooseMapBox.add(new JLabel("选择地图："), BorderLayout.WEST);
        mapComboBox = new JComboBox<>();
        chooseMapBox.add(mapComboBox, BorderLayout.CENTER);
        mapList = loadMapList();
        for (Pair<String,String> item : mapList){
            mapComboBox.addItem(item.getFirst());
        }
        JPanel ret = new JPanel(new GridLayout(2,1));
        ret.add(startDateBox);
        ret.add(chooseMapBox);
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
        JPanel newPlayerBtnPanel = new JPanel(new BorderLayout());
        JButton addPlayerBtn = new JButton("添加玩家");
        //isAICheckBox = new JCheckBox("AI玩家");
        //newPlayerBtnPanel.add(isAICheckBox,BorderLayout.WEST);
        newPlayerBtnPanel.add(addPlayerBtn,BorderLayout.CENTER);
        newPlayerPanel.add(newPlayerBtnPanel, BorderLayout.EAST);
        ret.add(newPlayerPanel, BorderLayout.NORTH);

        addPlayerBtn.addActionListener(e -> {
            if (newPlayerNameTextField.getText().trim().length() == 0){
                return;
            }
            if (playerListModel.size()>=4){
                JOptionPane.showConfirmDialog(GameSetupFrame.this, "本游戏只支持最多4个玩家，无法继续添加！",
                        "Monopoly 2", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
                return ;
            }
            PlayerNameAdapter adapter = new PlayerNameAdapter(
                    newPlayerNameTextField.getText().trim(), false);
                    //isAICheckBox.isSelected());
            playerListModel.addElement(adapter);
            newPlayerNameTextField.setText("");
            //isAICheckBox.setSelected(false);
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
            PlayerNameAdapter itemToMove = playerListModel.get(selectedIndex);
            playerListModel.remove(selectedIndex);
            playerListModel.add(targetIndex, itemToMove);
        });
        moveDownBtn.addActionListener(e -> {
            if (playerList.isSelectionEmpty()) return;
            int selectedIndex = playerList.getSelectedIndex();
            if (selectedIndex == playerListModel.size() - 1) return;
            int targetIndex = selectedIndex + 1;
            PlayerNameAdapter itemToMove = playerListModel.get(selectedIndex);
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
        setResizable(false);
        setTitle("设置游戏");
    }
}
