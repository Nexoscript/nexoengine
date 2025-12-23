package de.dragonrex.engine.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

import java.util.HashSet;
import java.util.Set;

public class Input {
    private static Set<Integer> pressedKeys = new HashSet<>();

    public static void setup(long window) {
        GLFW.glfwSetKeyCallback(window, new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (action == GLFW.GLFW_PRESS) pressedKeys.add(key);
                if (action == GLFW.GLFW_RELEASE) pressedKeys.remove(key);
            }
        });
    }

    public static boolean isKeyPressed(int key) {
        return pressedKeys.contains(key);
    }
}
