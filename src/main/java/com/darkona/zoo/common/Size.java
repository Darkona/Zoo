package com.darkona.zoo.common;

import lombok.AllArgsConstructor;

@AllArgsConstructor

public class Size {

    public static final Size ONE_BY_ONE = new Size(1,1);

    public int width;
    public int height;

    public Size(){
        this.height = 1;
        this.width = 1;
    }


}
