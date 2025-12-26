package de.dragonrex.engine.light;

import org.joml.Vector3f;

public abstract class Light {
    protected Vector3f color = new Vector3f(1, 1, 1);
    public float intensity = 1.0f;

    public Vector3f getColor() {
        return color;
    }

    public float getIntensity() {
        return intensity;
    }
}
