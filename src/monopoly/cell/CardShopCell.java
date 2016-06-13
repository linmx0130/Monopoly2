package monopoly.cell;

import message.CardShopQuestion;
import message.FaultMessage;
import message.SuccessMessage;
import monopoly.Kernel;
import monopoly.Player;
import monopoly.card.AbstractCard;
import monopoly.card.CardFactory;

import java.util.HashMap;

/**
 * Created by Mengxiao Lin on 2016/4/17.
 */
public class CardShopCell extends AbstractCell {
    private HashMap<String, Integer> cardCouponMap;
    public CardShopCell(int id){
        super(id, "道具店", "可以使用点券购买道具的商店。");
        cardCouponMap = new HashMap<>();
        cardCouponMap.put("LandCard", 2);
        cardCouponMap.put("TurnOrientationCard",1);
        cardCouponMap.put("RoadBlockCard",1);
        cardCouponMap.put("RobCard",1);
        cardCouponMap.put("TaxCard",1);
        cardCouponMap.put("DiceControllerCard",1);
    }
    @Override
    public void arrivedEffect(Player player) {
        String choose = null;
        do {
            CardShopQuestion question = (CardShopQuestion)Kernel.getInstance().getMessageFactory().createMessage("CardShopQuestion");
            question.setPlayer(player);
            question.setValueMap(cardCouponMap);
            Kernel.getInstance().getMessagePipe().onMessageArrived(question);
            choose = question.getCardName();
            if (choose == null) break;
            int value = cardCouponMap.get(choose);
            if (player.getCoupon() < value){
                FaultMessage msg = (FaultMessage) Kernel.getInstance().getMessageFactory().createMessage("FaultMessage");
                msg.setDescription("点券不足！");
                Kernel.getInstance().getMessagePipe().onMessageArrived(msg);
            }else {
                player.setCoupon(player.getCoupon()-value);
                AbstractCard cardToBuy = Kernel.getInstance().getCardFactory().generateCard(choose);
                player.addCard(cardToBuy);
                SuccessMessage msg = (SuccessMessage) Kernel.getInstance().getMessageFactory().createMessage("SuccessMessage");
                msg.setDescription(player.getName()+ "成功购买"+cardToBuy.getName()+"一张！");
                Kernel.getInstance().getMessagePipe().onMessageArrived(msg);
            }
        }while (choose !=null);
    }

    @Override
    public void moveOverEffect(Player player) {
        arrivedEffect(player);
    }
}
