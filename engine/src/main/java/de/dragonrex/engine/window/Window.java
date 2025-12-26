package de.dragonrex.engine.window;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import de.dragonrex.engine.components.CameraComponent;
import de.dragonrex.engine.scene.SceneManager;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Window {

    private long handle;
    private final WindowConfig config;

    public Window(WindowConfig config) {
        this.config = config;
    }

    public void create() {
        if (!glfwInit()) {
            throw new IllegalStateException("GLFW can't be initialized!");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, config.resizable ? GLFW_TRUE : GLFW_FALSE);

        handle = glfwCreateWindow(
                config.width,
                config.height,
                config.title,
                0,
                0
        );

        if (handle == 0) {
            throw new RuntimeException("Window can't be created!");
        }

        glfwMakeContextCurrent(handle);
        GL.createCapabilities();
        GL11.glViewport(0, 0, config.width, config.height);
        glfwSetFramebufferSizeCallback(handle, (window, width, height) -> {
            GL11.glViewport(0, 0, width, height);

            if (SceneManager.getActiveScene() != null) {
                CameraComponent cam = SceneManager.getActiveScene().getActiveCamera();
                if (cam != null) {
                    cam.setAspect((float) width / (float) height);
                }
            }
        });

        glfwSwapInterval(config.vsync ? 1 : 0);
        glfwShowWindow(handle);
    }

    public void setBorderlessFullscreen() {

        long monitor = glfwGetPrimaryMonitor();
        GLFWVidMode videoMode = glfwGetVideoMode(monitor);

        glfwSetWindowAttrib(handle, GLFW_DECORATED, GLFW_FALSE);

        glfwSetWindowMonitor(
                handle,
                monitor,
                0,
                0,
                videoMode.width(),
                videoMode.height(),
                videoMode.refreshRate()
        );
    }

    public void setWindowed(int width, int height) {
        glfwSetWindowAttrib(handle, GLFW_DECORATED, GLFW_TRUE);
        glfwSetWindowMonitor(handle, 0, 0, 0, width, height, 0);
        int[] left = new int[1];
        int[] top = new int[1];
        int[] right = new int[1];
        int[] bottom = new int[1];
        glfwGetWindowFrameSize(handle, left, top, right, bottom);
        glfwSetWindowSize(
                handle,
                width + left[0] + right[0],
                height + top[0] + bottom[0]
        );
        long monitor = glfwGetPrimaryMonitor();
        GLFWVidMode vm = glfwGetVideoMode(monitor);
        glfwSetWindowPos(
                handle,
                (vm.width() - width) / 2,
                (vm.height() - height) / 2
        );
    }

    public void setCursorMode(CursorMode mode) {
        switch (mode) {
            case NORMAL -> glfwSetInputMode(handle, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
            case DISABLED -> glfwSetInputMode(handle, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
            case HIDDEN -> glfwSetInputMode(handle, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
        }
    }

    public void update() {
        glfwSwapBuffers(handle);
        glfwPollEvents();
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(handle);
    }

    public void destroy() {
        glfwDestroyWindow(handle);
        glfwTerminate();
    }

    public long getHandle() {
        return handle;
    }
}

