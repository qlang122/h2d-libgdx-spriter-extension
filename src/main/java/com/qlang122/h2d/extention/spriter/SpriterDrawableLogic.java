package com.qlang122.h2d.extention.spriter;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Batch;

import games.rednblack.editor.renderer.components.TransformComponent;
import games.rednblack.editor.renderer.systems.render.logic.Drawable;

public class SpriterDrawableLogic implements Drawable {

    private ComponentMapper<SpriterDrawerComponent> spriterDrawerMapper;
    private ComponentMapper<SpriterObjectComponent> spriterMapper;
    private ComponentMapper<TransformComponent> transformMapper;

    public SpriterDrawableLogic() {
        spriterDrawerMapper = ComponentMapper.getFor(SpriterDrawerComponent.class);
        spriterMapper = ComponentMapper.getFor(SpriterObjectComponent.class);
        transformMapper = ComponentMapper.getFor(TransformComponent.class);
    }

    @Override
    public void draw(Batch batch, Entity entity, float parentAlpha, RenderingType renderingType) {
        TransformComponent entityTransformComponent = transformMapper.get(entity);
        SpriterDrawerComponent spriterDrawerComponent = spriterDrawerMapper.get(entity);
        SpriterObjectComponent spriter = spriterMapper.get(entity);
//        Player player = spriter.player;
//
//        player.setPosition(entityTransformComponent.x, entityTransformComponent.y);
//        //TODO dimentions
//        //player.setPivot(getWidth() / 2, getHeight() / 2);
//        player.setScale(spriter.scale);
//        player.rotate(entityTransformComponent.rotation - player.getAngle());
//        player.update();
//        spriterDrawerComponent.drawer.beforeDraw(player, batch);
    }

}
