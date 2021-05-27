package com.qlang.h2d.extention.spriter;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import games.rednblack.editor.renderer.components.SpriterDataComponent;

/**
 * @author Created by qlang on 5/27/2021.
 */
public class SpriterSystem extends IteratingSystem {

    private ComponentMapper<SpriterObjectComponent> spineObjectComponentMapper = ComponentMapper.getFor(SpriterObjectComponent.class);

    public SpriterSystem() {
        super(Family.all(SpriterDataComponent.class, SpriterObjectComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        SpriterObjectComponent spineObjectComponent = spineObjectComponentMapper.get(entity);

//        spineObjectComponent.state.update(deltaTime); // Update the animation time.
//        spineObjectComponent.state.apply(spineObjectComponent.skeleton); // Poses skeleton using current animations. This sets the bones' local SRT.
//
//        spineObjectComponent.skeleton.updateWorldTransform(); //
    }
}
