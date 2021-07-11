/*
 * ******************************************************************************
 *  * Copyright 2015 See AUTHORS file.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *   http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  *****************************************************************************
 */

package com.qlang.h2d.extention.spriter;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import games.rednblack.editor.renderer.box2dLight.RayHandler;
import games.rednblack.editor.renderer.components.DimensionsComponent;
import games.rednblack.editor.renderer.components.SpriterDataComponent;
import games.rednblack.editor.renderer.components.TransformComponent;
import games.rednblack.editor.renderer.data.MainItemVO;
import games.rednblack.editor.renderer.data.ProjectInfoVO;
import games.rednblack.editor.renderer.data.ResolutionEntryVO;
import games.rednblack.editor.renderer.data.SpriterVO;
import games.rednblack.editor.renderer.factory.EntityFactory;
import games.rednblack.editor.renderer.factory.component.ComponentFactory;
import games.rednblack.editor.renderer.resources.IResourceRetriever;
import games.rednblack.editor.renderer.utils.ComponentRetriever;
import me.winter.gdx.animation.Animation;
import me.winter.gdx.animation.scml.SCMLLoader;
import me.winter.gdx.animation.scml.SCMLProject;

/**
 * @author Created by qlang on 5/27/2021.
 */
public class SpriterComponentFactory extends ComponentFactory {
    private SpriterObjectComponent objectComponent;

    public SpriterComponentFactory() {
        super();
    }

    public SpriterComponentFactory(PooledEngine engine, RayHandler rayHandler, World world, IResourceRetriever rm) {
        super(engine, rayHandler, world, rm);
    }

    @Override
    public void createComponents(Entity root, Entity entity, MainItemVO vo) {
        objectComponent = createSpriterObjectComponent(entity, (SpriterVO) vo);
        createSpriterDataComponent(entity, (SpriterVO) vo);
        createCommonComponents(entity, vo, EntityFactory.SPRITER_TYPE);
        createParentNodeComponent(root, entity);
        createNodeComponent(root, entity);
    }

    @Override
    protected DimensionsComponent createDimensionsComponent(Entity entity, MainItemVO vo) {
        DimensionsComponent component = new DimensionsComponent();

        SpriterObjectComponent spriterComponent = ComponentRetriever.get(entity, SpriterObjectComponent.class);

        ResolutionEntryVO resolutionEntryVO = rm.getLoadedResolution();
        ProjectInfoVO projectInfoVO = rm.getProjectVO();
        float multiplier = resolutionEntryVO.getMultiplier(projectInfoVO.originalResolution);

        Rectangle rect = spriterComponent.rectangle;
        component.width = (int) rect.width * multiplier / projectInfoVO.pixelToWorld;
        component.height = (int) rect.height * multiplier / projectInfoVO.pixelToWorld;

        entity.add(component);
        return component;
    }

    protected SpriterObjectComponent createSpriterObjectComponent(Entity entity, SpriterVO vo) {
        SpriterObjectComponent component = new SpriterObjectComponent();
        component.animationName = vo.animationName;
        component.currentEntityIndex = vo.currentEntityIndex;
        component.currentAnimationName = vo.currentAnimationName;

        FileHandle scmlFile = rm.getSpriterSCML(vo.animationName);
        Array<FileHandle> extraScmlFile = rm.getSpriterExtraSCML(vo.animationName);
        TextureAtlas atlas = rm.getSpriterAtlas(vo.animationName);

        SCMLLoader loader = new SCMLLoader(new InternalFileHandleResolver());
        SCMLProject scmlProject = loader.load(atlas, scmlFile);
        Array<SCMLProject> extras = new Array<>();
        for (FileHandle handle : extraScmlFile) {
            extras.add(loader.load(atlas, handle));
        }

        component.entity = scmlProject.getEntity(component.currentEntityIndex);
        if (component.entity != null) {
            Animation animation = component.currentAnimationName.isEmpty()
                    ? component.entity.getAnimation(0)
                    : component.entity.getAnimation(component.currentAnimationName);
            if (animation == null) animation = component.entity.getAnimation(0);
            component.animation = animation;
            component.currentAnimationName = component.animation.getName();
            component.rectangle.set(component.animation.getBoundingRectangle(null));

            Array<Animation> array = component.entity.getAnimations();
            for (Animation anim : array) {
                component.animations.add(anim);
            }
            for (SCMLProject project : extras) {
                Array<me.winter.gdx.animation.Entity> entities = project.getSourceEntities();
                for (Animation anim : entities.get(0).getAnimations()) {
                    component.animations.add(anim);
                }
            }
        }
        Array<me.winter.gdx.animation.Entity> array = scmlProject.getSourceEntities();
        for (me.winter.gdx.animation.Entity entity1 : array.iterator()) {
            component.entities.add(entity1);
        }

        entity.add(component);
        return component;
    }

    @Override
    protected TransformComponent createTransformComponent(Entity entity, MainItemVO vo, DimensionsComponent dimensionsComponent) {
        TransformComponent component = engine.createComponent(TransformComponent.class);
        component.rotation = vo.rotation;
        component.scaleX = vo.scaleX;
        component.scaleY = vo.scaleY;
        System.out.println("--0-->>" + vo.x + " " + vo.y);
        component.x = vo.x;
        component.y = vo.y;
        component.isXYScaleLinked = vo.scaleXYLink;

        component.originX = dimensionsComponent.width / 2f;
        component.originY = dimensionsComponent.height / 2f;

        component.flipX = vo.flipX;
        component.flipY = vo.flipY;

        entity.add(component);

        SpriterObjectComponent spriterComponent = ComponentRetriever.get(entity, SpriterObjectComponent.class);
        for (Animation animation : spriterComponent.animations) {
            animation.setPosition(component.x + (dimensionsComponent.width / 2),
                    component.y + (dimensionsComponent.height / 2));
            animation.setScale(component.scaleX * (component.flipX ? -1 : 1), component.scaleY * (component.flipY ? -1 : 1));
            animation.setAngle(component.rotation);
        }
        return component;
    }

    protected SpriterDataComponent createSpriterDataComponent(Entity entity, SpriterVO vo) {
        SpriterDataComponent component = new SpriterDataComponent();
        component.animationName = vo.animationName;
        component.isLooping = vo.isLooping;
        component.currentEntityIndex = vo.currentEntityIndex;
        component.currentAnimationName = vo.currentAnimationName.isEmpty() ? objectComponent.animation.getName() : vo.currentAnimationName;

        entity.add(component);
        return component;
    }

}
