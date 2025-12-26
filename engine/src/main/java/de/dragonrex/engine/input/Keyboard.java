package de.dragonrex.engine.input;

import static org.lwjgl.glfw.GLFW.*;

public final class Keyboard {

    private static boolean[] keys = new boolean[GLFW_KEY_LAST];
    private static boolean[] lastKeys = new boolean[GLFW_KEY_LAST];

    private Keyboard() {}

    static void init(long window) {
        glfwSetKeyCallback(window, (w, key, scancode, action, mods) -> {
            if (key >= 0 && key < keys.length) {
                keys[key] = action != GLFW_RELEASE;
            }
        });
    }

    static void update() {
        System.arraycopy(keys, 0, lastKeys, 0, keys.length);
    }

    public static boolean isDown(int key) {
        return keys[key];
    }

    public static boolean isPressed(int key) {
        return keys[key] && !lastKeys[key];
    }

    public static boolean isReleased(int key) {
        return !keys[key] && lastKeys[key];
    }
}

