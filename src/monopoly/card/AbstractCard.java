package monopoly.card;

import monopoly.Player;

/**
 * Created by Mengxiao Lin on 2016/4/13.
 */
public abstract class AbstractCard {
    protected Player subject, object;
    protected String name;
    protected String description;

    /**
     * use the card
     * @param subject the card owner
     * @param object the object, null if this card need no object player
     * @return return true if success in using
     */
    public abstract boolean useCard(Player subject, Player object);

    /**
     * @return whether the card has long term effect
     */
    public abstract boolean isLongTermCard();

    /**
     * if a long turn card return false, it will be remove from the world
     * @return whether the effect is finished
     */
    public boolean isEffectFinished(){
        return true;
    }
    /**
     * @return whether the card need object
     */
    public abstract boolean needObject();
    /**
     * call it when the card is used for short term card
     * call it at the start of every turn for long term card
     */
    public abstract void cardEffect();

    public Player getSubject() {
        return subject;
    }

    public Player getObject() {
        return object;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
    public abstract boolean canBeUse(Player subject, Player object);
}
