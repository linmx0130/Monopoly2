package gui.message.human;

import message.CardShopQuestion;
import monopoly.Kernel;
import monopoly.Pair;
import monopoly.Player;
import monopoly.card.AbstractCard;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Mengxiao Lin on 2016/6/13.
 */
class CardShopDialog extends JDialog {
    private String cardChosen;
    public CardShopDialog(ArrayList<Pair<String,Integer>> cardList, Player player) {
        setLayout(new BorderLayout());
        String tips = "本店供应以下卡片，欢迎选购！\n您现在有点券";
        tips+=player.getCoupon();
        tips+="张！";
        JTextArea tipsArea =new JTextArea(tips);
        tipsArea.setEditable(false);
        tipsArea.setBackground(this.getBackground());
        add(tipsArea,BorderLayout.NORTH);
        JPanel cardBtnPanel = new JPanel(new GridLayout(cardList.size()+1, 1));
        for (Pair<String, Integer> card:cardList){
            JButton cardBtn = new JButton(card.getFirst()+"("+card.getSecond()+"点券)");
            cardBtn.addActionListener(_e->{
                cardChosen = card.getFirst();
                CardShopDialog.this.setVisible(false);
            });
            cardBtnPanel.add(cardBtn);
            if (card.getSecond()>player.getCoupon()) cardBtn.setEnabled(false);
        }
        JButton exitBtn = new JButton("放弃购买");
        exitBtn.addActionListener(e-> CardShopDialog.this.setVisible(false));
        cardBtnPanel.add(exitBtn);
        add(cardBtnPanel, BorderLayout.CENTER);
        pack();
        cardChosen = null;
        setModal(true);
        setTitle("卡片商店");
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public String getCardChosen() {
        return cardChosen;
    }
}
public class CardShopQuestionImpl extends CardShopQuestion {
    @Override
    public void action() {
        ArrayList<Pair<String, Integer>> cardList = new ArrayList<>();
        HashMap<String, String> cardNameTypeNameMap = new HashMap<>();
        getValueMap().entrySet().stream().forEach(e-> {
            AbstractCard card = Kernel.getInstance().getCardFactory().generateCard(e.getKey());
            cardList.add(new Pair<>(card.getName(), e.getValue()));
            cardNameTypeNameMap.put(card.getName(), e.getKey());
        });
        CardShopDialog dialog = new CardShopDialog(cardList,getPlayer());
        dialog.setVisible(true);
        if (dialog.getCardChosen() == null) {
            setCardName(null);
        }
        else{
            setCardName(cardNameTypeNameMap.get(dialog.getCardChosen()));
        }
    }
}
