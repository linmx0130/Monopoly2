package gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Mengxiao Lin on 2016/6/11.
 */
public class DiceShower extends JPanel {
    private ImageIcon[] diceImages;
    private int currentNumber;
    public DiceShower() {
        setPreferredSize(new Dimension(100,100));
        diceImages = new ImageIcon[6];
        for (int i=1;i<=6;++i){
            diceImages[i-1] = new ImageIcon(getClass().getResource("/image/dice/"+i+".png"));
        }
        currentNumber = 6;
        setBorder(BorderFactory.createLoweredBevelBorder());
    }

    public void setCurrentNumber(int currentNumber) {
        if (currentNumber>=1 && currentNumber<=6) {
            this.currentNumber = currentNumber;
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        int imageSize = Math.min(width, height);
        imageSize = (int)(imageSize*0.8);
        int leftUpX = (width - imageSize)/2;
        int leftUpY = (height - imageSize)/2;
        g.drawImage(diceImages[currentNumber-1].getImage(), leftUpX,leftUpY,imageSize, imageSize,null);
    }
}
