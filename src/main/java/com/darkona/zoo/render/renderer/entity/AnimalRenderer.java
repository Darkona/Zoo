package com.darkona.zoo.render.renderer.entity;

import com.darkona.zoo.common.Size;
import com.darkona.zoo.entity.animal.Animal;
import com.darkona.zoo.render.ImageUtils;
import com.darkona.zoo.render.renderer.Renderer;
import com.darkona.zoo.world.WorldThing;

import java.awt.*;

public class AnimalRenderer implements Renderer {

    public AnimalRenderer() {
    }

    public void render(Graphics graphics, Animal animal) {
        if(animal == null) return;
        render(graphics, animal, animal.getU(), animal.getV(), animal.getImageSize());
    }

    @Override
    public void render(Graphics graphics, WorldThing thing, int u, int v, Size size) {
        ImageUtils.drawImage(((Animal) thing).getImage(), thing, graphics, u, v, size);
    }
}
