package ui.message;

import message.FaultMessage;
import message.Message;
import message.MessageFactory;

import java.util.HashMap;

/**
 * Created by Mengxiao Lin on 2016/4/11.
 */
public class MessageFactoryImpl implements MessageFactory {
    public HashMap<String, Class> messageTypeClassMap;

    public MessageFactoryImpl() {
        this.messageTypeClassMap = new HashMap<>();
        registerMessageType("PropertyMessage", PropertyMessageImpl.class);
        registerMessageType("YesOrNoQuestion", YesOrNoQuestionImpl.class);
        registerMessageType("FaultMessage", FaultMessageImpl.class);
        registerMessageType("SuccessMessage", SuccessMessageImpl.class);
        registerMessageType("BankMessage",BankMessageImpl.class);
        registerMessageType("NewsMessage",NewsMessageImpl.class);
        registerMessageType("RoadBlockCardQuestion", RoadBlockCardQuestionImpl.class);
        registerMessageType("CardShopQuestion", CardShopQuestionImpl.class);
        registerMessageType("GameOverMessage",GameOverMessageImpl.class);
        registerMessageType("DiceControllerQuestion", DiceControllerQuestionImpl.class);
        registerMessageType("LotteryQuestion", LotteryQuestionImpl.class);
        registerMessageType("LotteryAlertMessage", LotteryAlertMessageImpl.class);
    }

    public void registerMessageType(String typename, Class typeClass) {
        this.messageTypeClassMap.put(typename, typeClass);
    }

    @Override
    public Message createMessage(String messageTypeName) {
        if (messageTypeClassMap.containsKey(messageTypeName)) {
            try {
                return (Message) messageTypeClassMap.get(messageTypeName).newInstance();
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }
}
