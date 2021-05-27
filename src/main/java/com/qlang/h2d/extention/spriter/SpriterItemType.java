package com.qlang.h2d.extention.spriter;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.World;

import games.rednblack.editor.renderer.box2dLight.RayHandler;
import games.rednblack.editor.renderer.commons.IExternalItemType;
import games.rednblack.editor.renderer.components.SpriterDataComponent;
import games.rednblack.editor.renderer.factory.EntityFactory;
import games.rednblack.editor.renderer.factory.component.ComponentFactory;
import games.rednblack.editor.renderer.resources.IResourceRetriever;
import games.rednblack.editor.renderer.systems.render.logic.Drawable;
import games.rednblack.editor.renderer.utils.ComponentRetriever;
import games.rednblack.editor.renderer.utils.Version;

/**
 * @author Created by qlang on 5/27/2021.
 */
public class SpriterItemType implements IExternalItemType {

    public static final Version SUPPORTED_SPINE_VERSION = new Version("3.8");

    private ComponentFactory factory;
    private IteratingSystem system;
    private Drawable drawable;

    public SpriterItemType() {
        factory = new SpriterComponentFactory();
        system = new SpriterSystem();
        drawable = new SpriterDrawableLogic();
    }

    @Override
    public int getTypeId() {
        return EntityFactory.SPRITER_TYPE;
    }

    @Override
    public Drawable getDrawable() {
        return drawable;
    }

    @Override
    public IteratingSystem getSystem() {
        return system;
    }

    @Override
    public ComponentFactory getComponentFactory() {
        return factory;
    }

    @Override
    public void injectMappers() {
        ComponentRetriever.addMapper(SpriterDataComponent.class);
        ComponentRetriever.addMapper(SpriterObjectComponent.class);
    }

    @Override
    public void injectDependencies(PooledEngine engine, RayHandler rayHandler, World world, IResourceRetriever rm) {
        factory.injectDependencies(engine, rayHandler, world, rm);
    }
}
