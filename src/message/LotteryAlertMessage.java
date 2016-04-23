package message;

/**
 * Created by Mengxiao Lin on 2016/4/24.
 */
public abstract class LotteryAlertMessage implements Message {
    private boolean hit;
    private int choose;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public int getChoose() {
        return choose;
    }

    public void setChoose(int choose) {
        this.choose = choose;
    }
}
