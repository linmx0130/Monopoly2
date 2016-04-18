package ui.message;

import message.CardShopQuestion;
import monopoly.Kernel;
import monopoly.Pair;
import monopoly.card.AbstractCard;
import ui.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by Mengxiao Lin on 2016/4/18.
 */
public class CardShopQuestionImpl extends CardShopQuestion {
    @Override
    public void action() {
        ArrayList<Pair<String, Integer>> cardList = new ArrayList<>();
        HashMap<String, String> cardNameTypeNameMap = new HashMap<>();
        getValueMap().entrySet().stream().forEach(e-> {
            AbstractCard card = Kernel.getInstance().getCardFactory().generateCard(e.getKey());
            cardList.add(
                new Pair<>(card.getName(), e.getValue())
            );
            cardNameTypeNameMap.put(card.getName(), e.getKey());
        });
        System.out.println("您有点券"+getPlayer().getCoupon()+"点！以下是本店供应的道具卡。");
        for (int i=0;i<cardList.size();++i){
            System.out.println((i+1)+"."+cardList.get(i).getFirst()+" "+cardList.get(i).getSecond()+"点券");
        }
        System.out.println("请输入要购买的卡的编号，输入不在上述列表中则放弃。");
        int choose = Util.getIntFromScanner(new Scanner(System.in));
        if (choose >0 && choose <= cardList.size()){
            String name = cardList.get(choose - 1).getFirst();
            setCardName(cardNameTypeNameMap.get(name));
        }else{
            setCardName(null);
        }
    }
}
