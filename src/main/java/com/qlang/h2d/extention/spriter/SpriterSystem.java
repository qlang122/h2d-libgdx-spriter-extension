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

    private final ComponentMapper<SpriterObjectComponent> spineObjectComponentMapper = ComponentMapper.getFor(SpriterObjectComponent.class);

    public SpriterSystem() {
        super(Family.all(SpriterDataComponent.class, SpriterObjectComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        SpriterObjectComponent component = spineObjectComponentMapper.get(entity);
        component.animation.update(deltaTime * 1000);
    }
}
