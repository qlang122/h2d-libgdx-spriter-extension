package com.qlang.h2d.extention.spriter;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Batch;

import games.rednblack.editor.renderer.components.TransformComponent;
import games.rednblack.editor.renderer.systems.render.logic.Drawable;
import me.winter.gdx.animation.AnimatedPart;
import me.winter.gdx.animation.Animation;

/**
 * @author Created by qlang on 5/27/2021.
 */
public class SpriterDrawableLogic implements Drawable {

    private ComponentMapper<SpriterObjectComponent> spriterMapper;
    private ComponentMapper<TransformComponent> transformMapper;

    public SpriterDrawableLogic() {
        spriterMapper = ComponentMapper.getFor(SpriterObjectComponent.class);
        transformMapper = ComponentMapper.getFor(TransformComponent.class);
    }

    @Override
    public void draw(Batch batch, Entity entity, float parentAlpha, RenderingType renderingType) {
        TransformComponent entityTransformComponent = transformMapper.get(entity);
        SpriterObjectComponent spriter = spriterMapper.get(entity);
        Animation animation = spriter.animation;

        AnimatedPart root = animation.getRoot();
        root.getPosition().set(entityTransformComponent.x, entityTransformComponent.y);
        //player.setPivot(getWidth() / 2, getHeight() / 2);
        root.setScale(spriter.scale);
        root.setAngle(entityTransformComponent.rotation - root.getAngle());
        animation.update(0);

        animation.draw(batch);
    }

}
