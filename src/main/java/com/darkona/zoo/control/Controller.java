package com.darkona.zoo.control;

public interface Controller {

    boolean isRequestingUp();
    boolean isRequestingDown();
    boolean isRequestingLeft();
    boolean isRequestingRight();
    boolean isSpace();

    boolean isEscape();

    boolean isA();
    boolean isB();
}
