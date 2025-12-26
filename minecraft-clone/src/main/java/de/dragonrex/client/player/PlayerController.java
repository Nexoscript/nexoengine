package de.dragonrex.client.player;

import de.dragonrex.engine.GameEngine;
import de.dragonrex.engine.core.Component;
import de.dragonrex.engine.components.Transform;
import de.dragonrex.engine.input.Keyboard;
import de.dragonrex.engine.input.Mouse;
import de.dragonrex.engine.window.CursorMode;

import static org.lwjgl.glfw.GLFW.*;

public class PlayerController extends Component {

    private float speed = 5f;
    private float sensitivity = 0.002f;

    private float yaw = 0f;
    private float pitch = 0f;

    private boolean mouseCaptured = true;
    private boolean fullscreen = false;

    @Override
    public void start() {
        GameEngine.window.setCursorMode(CursorMode.DISABLED);
    }

    @Override
    public void update(float deltaTime) {

        if (Keyboard.isPressed(GLFW_KEY_ESCAPE)) {
            mouseCaptured = !mouseCaptured;

            GameEngine.window.setCursorMode(
                    mouseCaptured
                            ? CursorMode.DISABLED
                            : CursorMode.NORMAL
            );
        }

        if (Keyboard.isPressed(GLFW_KEY_F11)) {
            if(!fullscreen) {
                GameEngine.window.setBorderlessFullscreen();
                fullscreen = true;
            } else {
                GameEngine.window.setWindowed(1280, 720);
                fullscreen = false;
            }
        }


        if (!mouseCaptured) return;

        Transform t = gameObject.getComponent(Transform.class);

        if (Keyboard.isDown(GLFW_KEY_W))
            t.getPosition().z -= speed * deltaTime;
        if (Keyboard.isDown(GLFW_KEY_S))
            t.getPosition().z += speed * deltaTime;
        if (Keyboard.isDown(GLFW_KEY_A))
            t.getPosition().x -= speed * deltaTime;
        if (Keyboard.isDown(GLFW_KEY_D))
            t.getPosition().x += speed * deltaTime;
        if (Keyboard.isDown(GLFW_KEY_SPACE))
            t.getPosition().y += speed * deltaTime;
        if (Keyboard.isDown(GLFW_KEY_LEFT_SHIFT))
            t.getPosition().y -= speed * deltaTime;

        yaw   -= (float) (Mouse.getDeltaX() * sensitivity);
        pitch -= (float) (Mouse.getDeltaY() * sensitivity);

        pitch = Math.max(-1.5f, Math.min(1.5f, pitch));

        t.getRotation().set(pitch, yaw, 0);
    }
}

