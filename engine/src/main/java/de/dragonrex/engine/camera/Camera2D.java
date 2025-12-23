package de.dragonrex.engine.camera;

import org.joml.Matrix4f;

public class Camera2D {
    private int width, height;
    private Matrix4f projection;

    public Camera2D(int width, int height) {
        this.width = width;
        this.height = height;
        projection = new Matrix4f().ortho2D(0, width, 0, height);
    }

    public Matrix4f getProjection() { return projection; }
}