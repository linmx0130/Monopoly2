package message;

/**
 * Created by Mengxiao Lin on 2016/4/11.
 */
public abstract class FaultMessage implements Message{
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
