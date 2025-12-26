package de.dragonrex.engine.core.scene;

import de.dragonrex.engine.core.GameObject;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {

    protected List<GameObject> gameObjects = new ArrayList<>();

    public void init() {
        for (GameObject obj : gameObjects) {
            obj.start();
        }
    }

    public void update(float deltaTime) {
        for (GameObject obj : gameObjects) {
            obj.update(deltaTime);
        }
    }

    public final void render() {
        for (GameObject obj : gameObjects) {
            obj.render();
        }
    }
    protected abstract void onInit();

    public void shutdown() {
        for (GameObject obj : gameObjects) {
            obj.destroy();
        }
    }

    public void add(GameObject obj) {
        gameObjects.add(obj);
    }
}

