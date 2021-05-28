package com.qlang.h2d.extention.spriter;

import java.util.ArrayList;

import games.rednblack.editor.renderer.components.BaseComponent;
import me.winter.gdx.animation.Animation;
import me.winter.gdx.animation.Entity;

/**
 * @author Created by qlang on 5/27/2021.
 */
public class SpriterObjectComponent implements BaseComponent {
    public String animationName = "";

    public Entity entity;
    public Animation animation;

    public ArrayList<Animation> animations = new ArrayList<>();
    public ArrayList<Entity> entities = new ArrayList<>();

    public int currentEntityIndex = 0;
    public String currentAnimationName = "";

    @Override
    public void reset() {
        animationName = "";
        currentEntityIndex = 0;
    }

    public ArrayList<Animation> getAnimations() {
        return animations;
    }

    public void setAnimation(String name) {
        this.currentAnimationName = name;
        for (Animation anim : animations) {
            if (anim.getName().equals(name)) {
                animation = anim;
                break;
            }
        }
    }
}
