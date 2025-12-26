package de.dragonrex.engine.scene;

public class SceneManager {

    private static Scene activeScene;
    private static Scene pendingScene;

    private SceneManager() {}

    public static void setScene(Scene scene) {
        pendingScene = scene;
    }

    public static Scene getActiveScene() {
        return activeScene;
    }

    public static void update(float deltaTime) {

        // Scene-Wechsel sicher durchführen
        if (pendingScene != null) {
            if (activeScene != null) {
                activeScene.shutdown();
            }

            activeScene = pendingScene;
            pendingScene = null;

            activeScene.onInit();
            activeScene.init();
        }

        if (activeScene != null) {
            activeScene.update(deltaTime);
        }
    }

    public static void render() {
        if (activeScene != null) {
            activeScene.render();
        }
    }

    public static void shutdown() {
        if (activeScene != null) {
            activeScene.shutdown();
        }
    }
}

