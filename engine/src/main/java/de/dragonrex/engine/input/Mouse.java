package de.dragonrex.engine.input;

import static org.lwjgl.glfw.GLFW.*;

public final class Mouse {

    private static double x, y;
    private static double lastX, lastY;
    private static boolean[] buttons = new boolean[GLFW_MOUSE_BUTTON_LAST];

    private Mouse() {}

    static void init(long window) {

        glfwSetCursorPosCallback(window, (w, xpos, ypos) -> {
            x = xpos;
            y = ypos;
        });

        glfwSetMouseButtonCallback(window, (w, button, action, mods) -> {
            if (button >= 0 && button < buttons.length) {
                buttons[button] = action != GLFW_RELEASE;
            }
        });
    }

    static void update() {
        lastX = x;
        lastY = y;
    }

    public static double getX() { return x; }
    public static double getY() { return y; }

    public static double getDeltaX() { return x - lastX; }
    public static double getDeltaY() { return y - lastY; }

    public static boolean isDown(int button) {
        return buttons[button];
    }
}

