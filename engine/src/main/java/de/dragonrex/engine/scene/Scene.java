package de.dragonrex.engine.scene;

import de.dragonrex.engine.components.CameraComponent;
import de.dragonrex.engine.core.GameObject;
import java.util.ArrayList;
import java.util.List;

public abstract class Scene {

    protected List<GameObject> gameObjects = new ArrayList<>();
    private CameraComponent activeCamera;

    public void init() {
        for (GameObject obj : gameObjects) {
            obj.start();

            CameraComponent cam = obj.getComponent(CameraComponent.class);
            if (cam != null && cam.isPrimary()) {
                activeCamera = cam;
            }
        }
    }

    public void update(float deltaTime) {
        for (GameObject obj : gameObjects) {
            obj.update(deltaTime);
        }
    }

    public final void render() {
        if (activeCamera == null) {
            System.err.println("❌ No active CameraComponent found!");
            return;
        }
        for (GameObject obj : gameObjects) {
            obj.render();
        }
        onRender(activeCamera);
    }

    protected abstract void onRender(CameraComponent camera);
    protected abstract void onInit();

    public void shutdown() {
        for (GameObject obj : gameObjects) {
            obj.destroy();
        }
    }

    public void add(GameObject obj) {
        gameObjects.add(obj);
    }

    public CameraComponent getActiveCamera() {
        return activeCamera;
    }
}

