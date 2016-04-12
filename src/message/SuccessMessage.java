package message;

/**
 * Created by Mengxiao Lin on 2016/4/12.
 */
public abstract class SuccessMessage implements Message {
    private String description;
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
