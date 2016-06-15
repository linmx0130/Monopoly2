package message;

import monopoly.Player;
import monopoly.cell.AbstractCell;

/**
 * Created by Mengxiao Lin on 2016/4/17.
 */
public abstract  class RoadBlockCardQuestion implements Question {
    private Player subject;
    private int choose;

    public Player getSubject() {
        return subject;
    }

    public void setSubject(Player subject) {

        this.subject = subject;
    }

    public int getChoose() {
        return choose;
    }

    public void setChoose(int choose) {
        this.choose = choose;
    }

    public AbstractCell getCellChosen(){
        return null;
    }
}
