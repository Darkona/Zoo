package com.darkona.zoo.render.renderer.entity;

import com.darkona.zoo.common.Size;
import com.darkona.zoo.entity.Player;
import com.darkona.zoo.render.ImageUtils;
import com.darkona.zoo.render.renderer.Renderer;
import com.darkona.zoo.world.WorldThing;

import java.awt.*;

public class PlayerRenderer implements Renderer {

    private static final Image image = ImageUtils.loadImage("/animal/pc.png");;
    private final int u;
    private final int v;
    private final Size imageSize;
    public PlayerRenderer(){
        this.u = this.v = 5;
        imageSize = new Size(15);
    }

    public void render(Graphics graphics, Player p){
        if (p == null) return;
        render(graphics, p, u, v, imageSize);
    }
    @Override
    public void render(Graphics graphics, WorldThing thing, int u, int v, Size size) {
        ImageUtils.drawImage(image, thing, graphics, 5,5, new Size(15,15));
    }

}
