package gui;

import gui.map.MapViewer;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Mengxiao Lin on 2016/6/9.
 */
public class GameFrame extends JFrame{
    private MapViewer mapViewer;

    public GameFrame() {
        setLayout(new BorderLayout());
        mapViewer = new MapViewer();
        add(mapViewer, BorderLayout.CENTER);
        pack();
        setTitle("Monopoly 2");
        setLocationRelativeTo(null);
    }
}
