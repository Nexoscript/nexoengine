package de.dragonrex.client.controller;

import de.dragonrex.engine.camera.Camera3D;
import de.dragonrex.engine.input.Mouse;
import org.lwjgl.glfw.GLFW;

public class MouseCameraController {

    private final Camera3D camera;
    private float sensitivity = 0.1f;
    private boolean active = false;

    public MouseCameraController(Camera3D camera) {
        this.camera = camera;
        setActive(true);
    }

    public void update() {
        if (!active) return;
        if (Mouse.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_RIGHT)) {
            camera.rotate(
                    -(float) Mouse.getDeltaY() * 0.1f, // pitch
                    (float) Mouse.getDeltaX() * 0.1f, // yaw
                    0f
            );
        }
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
