package ui.message;

import message.SuccessMessage;

/**
 * Created by Mengxiao Lin on 2016/4/12.
 */
public class SuccessMessageImpl extends SuccessMessage {
    @Override
    public void action() {
        System.out.println(getDescription());
    }
}
