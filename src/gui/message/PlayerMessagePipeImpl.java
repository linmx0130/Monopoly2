package gui.message;

import message.Message;
import message.MessagePipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mengxiao Lin on 2016/6/8.
 */
public class PlayerMessagePipeImpl implements MessagePipe {
    private List<Message> messageStorage;
    private boolean isActive;
    private MessagePipe realPipe;

    public PlayerMessagePipeImpl(MessagePipe realPipe) {
        this.realPipe = realPipe;
        messageStorage = new ArrayList<>();
    }

    @Override
    public void onMessageArrived(Message message) {
        if (isActive) {
            realPipe.onMessageArrived(message);
        }else{
            messageStorage.add(message);
        }
    }

    public void setActive(boolean active) {
        isActive = active;
        if (active){
            messageStorage.forEach(m -> realPipe.onMessageArrived(m));
            messageStorage.clear();
        }
    }
}
