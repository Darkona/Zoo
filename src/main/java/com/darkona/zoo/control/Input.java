package com.darkona.zoo.control;

import lombok.Data;
import lombok.extern.java.Log;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@Data
@Log
public class Input implements KeyListener {

    private boolean[] pressed;

    public Input(){
        pressed = new boolean[255];
    }

    public boolean isPressed(int keyCode){
        return pressed[keyCode];
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        pressed[e.getKeyCode()] = true;
        //log.log(Level.INFO, "Pressed " + e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressed[e.getKeyCode()] = false;
        //log.log(Level.INFO, "Released " + e.getKeyCode());
    }
}
