package gui;

import monopoly.Player;
import monopoly.card.AbstractCard;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Mengxiao Lin on 2016/6/14.
 */
public class UseCardDialog extends JDialog {
    class CardAdapter{
        AbstractCard card;

        public CardAdapter(AbstractCard card) {
            this.card = card;
        }

        @Override
        public String toString() {
            return card.getName();
        }
    }
    private JList<CardAdapter> cardList;
    private DefaultListModel<CardAdapter> cardListModel;
    private AbstractCard cardChosen;

    private JPanel getBottomPanel(){
        JPanel ret = new JPanel(new BorderLayout());
        JPanel btnPanel = new JPanel(new GridLayout());
        ret.add(btnPanel, BorderLayout.EAST);
        JButton cancelBtn = new JButton("取消");
        JButton useCardBtn = new JButton("使用");
        btnPanel.add(cancelBtn);
        cancelBtn.addActionListener(_e->{
            setVisible(false);
        });
        useCardBtn.addActionListener(_e->{
            if (cardList.isSelectionEmpty()){
                JOptionPane.showConfirmDialog(UseCardDialog.this, "请选择一张卡！", "Monopoly 2",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
                return;
            }
            cardChosen = cardListModel.get(cardList.getSelectedIndex()).card;
            setVisible(false);
        });
        btnPanel.add(useCardBtn);
        return ret;
    }
    private UseCardDialog(Player player) {
        cardChosen = null;
        ArrayList<AbstractCard> cards = player.getCards();
        cards.sort((c1,c2)->
            c1.getName().compareTo(c2.getName())
        );

        setLayout(new BorderLayout());
        cardListModel = new DefaultListModel<>();
        cardList = new JList<>(cardListModel);
        cards.forEach(c-> cardListModel.addElement(new CardAdapter(c)));
        cardList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        add(new JLabel("请选择一张道具卡发动。"), BorderLayout.NORTH);
        add(cardList, BorderLayout.CENTER);
        add(getBottomPanel(), BorderLayout.SOUTH);
        setModal(true);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(200,300));
        pack();
        setTitle("使用道具卡");
    }

    private AbstractCard getCardChosen() {
        return cardChosen;
    }

    public static AbstractCard showUseCardDialog(Player player){
        UseCardDialog dialog = new UseCardDialog(player);
        dialog.setVisible(true);
        return dialog.getCardChosen();
    }
}
