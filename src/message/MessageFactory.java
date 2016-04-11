package message;

/**
 * Created by Mengxiao Lin on 2016/4/11.
 */
public interface MessageFactory {
    Message createMessage(String messageType);
}
