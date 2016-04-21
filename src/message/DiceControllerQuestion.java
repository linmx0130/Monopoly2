package message;

/**
 * Created by Mengxiao Lin on 2016/4/21.
 */
public abstract class DiceControllerQuestion implements Question {
    Integer choose;
    public DiceControllerQuestion(){
        choose = null;
    }
    public Integer getChoose() {
        return choose;
    }

    public void setChoose(Integer choose) {
        this.choose = choose;
    }
}
