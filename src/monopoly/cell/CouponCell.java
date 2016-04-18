package monopoly.cell;

import message.SuccessMessage;
import monopoly.Kernel;
import monopoly.Player;

import java.util.Random;

/**
 * Created by Mengxiao Lin on 2016/4/17.
 */
public class CouponCell extends AbstractCell {
    private final int UPPER_BOUND = 10;
    private final int LOWER_BOUND = 2;
    public CouponCell(int id){
        super(id, "点券点","随机给到达这里的玩家发放点券。");
    }
    @Override
    public void arrivedEffect(Player player) {
        int coupon = (new Random()).nextInt(UPPER_BOUND - LOWER_BOUND) + LOWER_BOUND;
        player.setCoupon(player.getCoupon()+ coupon);
        SuccessMessage msg = (SuccessMessage)Kernel.getInstance().getMessageFactory().createMessage("SuccessMessage");
        msg.setDescription(player.getName()+"获赠点券"+coupon+"点！");
        Kernel.getInstance().getMessagePipe().onMessageArrived(msg);
    }

    @Override
    public void moveOverEffect(Player player) {
        arrivedEffect(player);
    }
}
