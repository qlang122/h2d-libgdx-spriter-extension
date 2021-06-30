package com.qlang.h2d.extention.spriter;

import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

import games.rednblack.editor.renderer.components.BaseComponent;
import me.winter.gdx.animation.Animation;
import me.winter.gdx.animation.Entity;

/**
 * @author Created by qlang on 5/27/2021.
 */
public class SpriterObjectComponent implements BaseComponent {
    public static final String ACTION_PLAY_CLICKED = "ACTION_PLAY_CLICKED";
    public static final String ACTION_PAUSE_CLICKED = "ACTION_PAUSE_CLICKED";
    public static final String ACTION_GO2FIRST_CLICKED = "ACTION_GO2FIRST_CLICKED";
    public static final String ACTION_GO2LAST_CLICKED = "ACTION_GO2LAST_CLICKED";
    public static final String ACTION_GO2PREV_CLICKED = "ACTION_GO2PREV_CLICKED";
    public static final String ACTION_GO2NEXT_CLICKED = "ACTION_GO2NEXT_CLICKED";

    public String animationName = "";

    public Entity entity;
    public Animation animation;

    public Rectangle rectangle = new Rectangle();

    public ArrayList<Animation> animations = new ArrayList<>();
    public ArrayList<Entity> entities = new ArrayList<>();

    public int currentEntityIndex = 0;
    public String currentAnimationName = "";

    @Override
    public void reset() {
        animationName = "";
        entity = null;
        animation = null;
        currentEntityIndex = 0;
        currentAnimationName = "";
        rectangle.set(0, 0, 0, 0);
        animations.clear();
        entities.clear();
    }

    public ArrayList<Animation> getAnimations() {
        return animations;
    }

    public void doActions(String action) {
        switch (action) {
            case ACTION_PLAY_CLICKED:
                play();
                break;
            case ACTION_PAUSE_CLICKED:
                pause();
                break;
            case ACTION_GO2FIRST_CLICKED:
                goToFirstFrame();
                break;
            case ACTION_GO2LAST_CLICKED:
                goToLastFrame();
                break;
            case ACTION_GO2PREV_CLICKED:
                goToPrevFrame();
                break;
            case ACTION_GO2NEXT_CLICKED:
                goToNextFrame();
                break;
        }
    }

    public void play() {
        if (animation != null) {
            animation.startPlay();
        }
    }

    public void pause() {
        if (animation != null) {
            animation.pausePlay();
        }
    }

    private void goToPrevFrame() {
        if (animation != null) {
            animation.pausePlay();
            animation.prevKey();
        }
    }

    private void goToNextFrame() {
        if (animation != null) {
            animation.pausePlay();
            animation.nextKey();
        }
    }

    private void goToFirstFrame() {
        if (animation != null) {
            animation.pausePlay();
            animation.first();
        }
    }

    private void goToLastFrame() {
        if (animation != null) {
            animation.pausePlay();
            animation.last();
        }
    }

    public boolean isPlaying() {
        return animation != null && animation.isPlaying();
    }

    public boolean isLooping() {
        return animation != null && animation.isLooping();
    }

    public void setLooping(boolean value) {
        if (animation != null) animation.setLooping(value);
    }

    public void setAnimation(String name) {
        if (currentAnimationName.equals(name)) return;
        this.currentAnimationName = name;
        for (Animation anim : animations) {
            if (anim.getName().equals(name)) {
                animation = anim;
                break;
            }
        }
    }
}
