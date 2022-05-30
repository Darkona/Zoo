package com.darkona.zoo.render;

import com.darkona.zoo.Configuration;
import com.darkona.zoo.control.Input;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.simulation.Simulation;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Display extends JFrame {

    private final Canvas canvas;
    private final int scale = Configuration.getInstance().getDisplayScale();

    public Display(Size size, Input input) {
        addKeyListener(input);
        setTitle("Zoo");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(size.width * scale , size.height * scale));
        canvas.setFocusable(false);

        add(canvas);
        pack();

        canvas.createBufferStrategy(2);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void render(Simulation simulation) {
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics2D graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
        graphics.scale(scale, scale);
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        simulation.getWorld().render(graphics);
        graphics.dispose();
        bufferStrategy.show();
    }
}
