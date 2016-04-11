package ui.message;

import message.FaultMessage;

/**
 * Created by Mengxiao Lin on 2016/4/11.
 */
public class FaultMessageImpl extends FaultMessage{
    @Override
    public void action() {
        System.out.println(getDescription());
    }
}
