package de.dragonrex.client.controller;

import de.dragonrex.engine.camera.Camera3D;
import de.dragonrex.engine.input.Input;
import org.lwjgl.glfw.GLFW;

public class CameraController3D {

    private Camera3D camera;
    private long window;

    public CameraController3D(Camera3D camera, long window) {
        this.camera = camera;
        this.window = window;
    }

    public void update(float deltaTime) {
        float speed = 5f * deltaTime;

        if (Input.isKeyPressed(GLFW.GLFW_KEY_W)) {
            camera.moveForward(speed);
        }
        if (Input.isKeyPressed(GLFW.GLFW_KEY_S)) {
            camera.moveBackward(speed);
        }
        if (Input.isKeyPressed(GLFW.GLFW_KEY_A)) {
            camera.moveLeft(speed);
        }
        if (Input.isKeyPressed(GLFW.GLFW_KEY_D)) {
            camera.moveRight(speed);
        }
    }
}

