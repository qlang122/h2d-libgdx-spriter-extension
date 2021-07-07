package com.qlang.h2d.extention.spriter;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Batch;

import games.rednblack.editor.renderer.components.DimensionsComponent;
import games.rednblack.editor.renderer.components.TintComponent;
import games.rednblack.editor.renderer.components.TransformComponent;
import games.rednblack.editor.renderer.systems.render.logic.Drawable;
import me.winter.gdx.animation.AnimatedPart;
import me.winter.gdx.animation.Animation;

/**
 * @author Created by qlang on 5/27/2021.
 */
public class SpriterDrawableLogic implements Drawable {
    private final ComponentMapper<SpriterObjectComponent> spriterMapper = ComponentMapper.getFor(SpriterObjectComponent.class);
    private final ComponentMapper<TransformComponent> transformMapper = ComponentMapper.getFor(TransformComponent.class);
    private final ComponentMapper<TintComponent> tintComponentMapper = ComponentMapper.getFor(TintComponent.class);
    private final ComponentMapper<DimensionsComponent> dimensionsComponentMapper = ComponentMapper.getFor(DimensionsComponent.class);

    public SpriterDrawableLogic() {
    }

    @Override
    public void draw(Batch batch, Entity entity, float parentAlpha, RenderingType renderingType) {
        TransformComponent transform = transformMapper.get(entity);
        SpriterObjectComponent spriter = spriterMapper.get(entity);
        TintComponent tint = tintComponentMapper.get(entity);
        DimensionsComponent dimen = dimensionsComponentMapper.get(entity);

        Animation animation = spriter.animation;

        if (animation != null) {
            //make the source center to the current control rect center
            animation.setPosition(transform.x + (dimen.width / 2), transform.y + (dimen.height / 2));

            float scaleX = transform.scaleX * (transform.flipX ? -1 : 1);
            float scaleY = transform.scaleY * (transform.flipY ? -1 : 1);

            animation.setScale(scaleX, scaleY);
            animation.setAngle(transform.rotation);
            if (spriter.tintEnable) {
                tint.color.a *= parentAlpha;
                animation.tintSprite(tint.color);
            }

            animation.draw(batch);
        }
    }

}
