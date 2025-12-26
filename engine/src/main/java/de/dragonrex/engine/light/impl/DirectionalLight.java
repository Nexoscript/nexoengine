package de.dragonrex.engine.light.impl;

import de.dragonrex.engine.light.Light;
import org.joml.Vector3f;

public class DirectionalLight extends Light {

    private Vector3f direction = new Vector3f(-0.2f, -1.0f, -0.3f);

    public Vector3f getDirection() {
        return direction;
    }
}
