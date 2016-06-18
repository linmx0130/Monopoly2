package gui.message;

import gui.message.human.*;
import message.CardShopQuestion;
import message.Message;
import message.MessageFactory;
import message.NewsMessage;

import java.util.HashMap;

/**
 * Message factory for human player.
 * Created by Mengxiao Lin on 2016/6/8.
 */
public class HumanMessageFactoryImpl implements MessageFactory{
    private HashMap<String, Class> messageTypeClassMap;

    public HumanMessageFactoryImpl() {
        this.messageTypeClassMap = new HashMap<>();
        registerMessageType("BankMessage", BankMessageImpl.class);
        registerMessageType("YesOrNoQuestion", YesOrNoQuestionImpl.class);
        registerMessageType("SuccessMessage", SuccessMessageImpl.class);
        registerMessageType("PropertyMessage", PropertyMessageImpl.class);
        registerMessageType("LotteryQuestion", LotteryQuestionImpl.class);
        registerMessageType("NewsMessage", NewsMessageImpl.class);
        registerMessageType("CardShopQuestion", CardShopQuestionImpl.class);
        registerMessageType("GameOverMessage",GameOverMessageImpl.class);
        registerMessageType("RoadBlockCardQuestion", RoadBlockCardQuestionImpl.class);
        registerMessageType("DiceControllerQuestion", DiceControllerQuestionImpl.class);
        registerMessageType("FaultMessage", FaultMessageImpl.class);
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
