package monopoly.card;

import monopoly.Player;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Mengxiao Lin on 2016/4/16.
 */
public class CardStack {
    private ArrayList<AbstractCard> longTermCards;
    public CardStack(){
        longTermCards = new ArrayList<>();
    }
    public boolean issueCard(AbstractCard card, Player subject, Player object){
        if (!card.useCard(subject, object)){
            return false; //failure in use
        }
        if (card.isLongTermCard()){
            longTermCards.add(card);
        }else {
            card.cardEffect();
        }
        return true;
    }
    public void turnAction(){
        longTermCards.forEach(c -> c.cardEffect());
        Object[] cardToRemove = longTermCards.stream().filter(c -> c.isEffectFinished()).toArray();
        Arrays.stream(cardToRemove).forEach(c -> longTermCards.remove(c));
    }
}
