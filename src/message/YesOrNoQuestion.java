package message;

/**
 * Created by Mengxiao Lin on 2016/4/11.
 */
public abstract class YesOrNoQuestion implements Question{
    private String description;
    private boolean answer;
    public YesOrNoQuestion(String description){
        this.description = null;
        this.answer = false;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public boolean getAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }
}
