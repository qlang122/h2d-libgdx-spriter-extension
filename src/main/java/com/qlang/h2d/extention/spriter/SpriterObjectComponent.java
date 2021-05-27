package com.qlang.h2d.extention.spriter;

import java.util.ArrayList;

import games.rednblack.editor.renderer.components.BaseComponent;

/**
 * @author Created by qlang on 5/27/2021.
 */
public class SpriterObjectComponent implements BaseComponent {
    //    public Player player;
//    public Data data;
    public ArrayList<String> animations = new ArrayList<String>();
    public ArrayList<String> entities = new ArrayList<String>();
    public int currentEntityIndex = 0;
    public int currentAnimationIndex;

    public int entity;
    public int animation;
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
