package ui.message;

import message.Message;
import message.MessagePipe;

/**
 * Created by Mengxiao Lin on 2016/4/11.
 */
public class MessagePipeImpl implements MessagePipe {
    public MessagePipeImpl() {
    }

    @Override
    public void onMessageArrived(Message message) {
        message.action();
    }
}
