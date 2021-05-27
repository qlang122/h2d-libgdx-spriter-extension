package com.qlang.h2d.extention.spriter;

import java.util.ArrayList;

import games.rednblack.editor.renderer.components.BaseComponent;
import me.winter.gdx.animation.Animation;
import me.winter.gdx.animation.Entity;

/**
 * @author Created by qlang on 5/27/2021.
 */
public class SpriterObjectComponent implements BaseComponent {
    public Entity entity;
    public Animation animation;

    public ArrayList<Animation> animations = new ArrayList<>();
    public ArrayList<Entity> entities = new ArrayList<>();

    public int currentEntityIndex = 0;
    public int currentAnimationIndex;
    public String animationName = "";
    public float scale = 1f;

    @Override
    public void reset() {
        animationName = "";
        scale = 1f;
        currentEntityIndex = 0;
        currentAnimationIndex = 0;
    }
}
