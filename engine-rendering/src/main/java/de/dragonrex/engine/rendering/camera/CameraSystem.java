package de.dragonrex.engine.rendering.camera;

public class CameraSystem {
    private static CameraComponent mainCamera;

    public static void register(CameraComponent cam) {
        if(cam.isPrimary()) {
            mainCamera = cam;
        }
    }

    public static CameraComponent getMainCamera() {
        return mainCamera;
    }
}
