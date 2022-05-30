package com.darkona.zoo.control;

import java.awt.event.KeyEvent;

public class PlayerController implements Controller{

    private final Input input;

    public PlayerController(Input input){
        this.input = input;
    }

    public boolean[] getInputArray(){
        return input.getPressed();
    }

    @Override
    public boolean isRequestingUp() {
        return input.isPressed(KeyEvent.VK_UP);
    }

    @Override
    public boolean isRequestingDown() {
        return input.isPressed(KeyEvent.VK_DOWN);
    }

    @Override
    public boolean isRequestingLeft() {
        return input.isPressed(KeyEvent.VK_LEFT);
    }

    @Override
    public boolean isRequestingRight() {
        return input.isPressed(KeyEvent.VK_RIGHT);
    }

    @Override
    public boolean isSpace() {
        return input.isPressed(KeyEvent.VK_SPACE);
    }

    @Override
    public boolean isEscape() {
        return input.isPressed(KeyEvent.VK_ESCAPE);
    }

    @Override
    public boolean isA() {
        return input.isPressed(KeyEvent.VK_A);
    }

    @Override
    public boolean isB() {
        return input.isPressed(KeyEvent.VK_B);
    }

    @Override
    public boolean isC() {
        return input.isPressed(KeyEvent.VK_C);
    }

    @Override
    public boolean isD() {
        return input.isPressed(KeyEvent.VK_D);
    }

    @Override
    public boolean isE() {
        return input.isPressed(KeyEvent.VK_E);
    }

    @Override
    public boolean isF() {
        return input.isPressed(KeyEvent.VK_F);
    }

    @Override
    public boolean isG() {
        return input.isPressed(KeyEvent.VK_G);
    }

    @Override
    public boolean isH() {
        return input.isPressed(KeyEvent.VK_H);
    }

    @Override
    public boolean isI() {
        return input.isPressed(KeyEvent.VK_I);
    }

    @Override
    public boolean isJ() {
        return input.isPressed(KeyEvent.VK_J);
    }

    @Override
    public boolean isK() {
        return input.isPressed(KeyEvent.VK_K);
    }

    @Override
    public boolean isL() {
        return input.isPressed(KeyEvent.VK_L);
    }

    @Override
    public boolean isM() {
        return input.isPressed(KeyEvent.VK_M);
    }

    @Override
    public boolean isN() {
        return input.isPressed(KeyEvent.VK_N);
    }
}
