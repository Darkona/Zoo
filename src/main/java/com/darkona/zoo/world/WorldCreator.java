package com.darkona.zoo.world;

import com.darkona.zoo.common.Position;
import com.darkona.zoo.entity.animal.Animal;
import lombok.AllArgsConstructor;
import org.pmw.tinylog.Logger;

@AllArgsConstructor
public class WorldCreator {

    private World world;

    public void createAnimal(Class<? extends Animal> clazz, Position position) {
        try{
            Animal a = clazz.getConstructor(World.class, Position.class).newInstance(world, position);
            world.setAnimal(a);
            Logger.info(String.format("Creating %s at %s", a.getName(), position));
        }catch (Exception e){
            Logger.error(String.format("Can't create %s at %s", clazz.getName(), position));
        }
    }

    @SuppressWarnings("unchecked")
    public void createAnimal(String animalName, Position position){
        try {
            Class<? extends Animal> clazz =
                    (Class<? extends Animal>) this.getClass().getClassLoader().loadClass("com.darkona.zoo.entity.animal." + animalName);
            createAnimal(clazz, position);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
