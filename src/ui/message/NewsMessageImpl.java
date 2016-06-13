package ui.message;

import message.NewsMessage;

/**
 * Created by Mengxiao Lin on 2016/4/14.
 */
public class NewsMessageImpl extends NewsMessage {
    @Override
    public void action() {
        System.out.println(getDescription());
    }
}
