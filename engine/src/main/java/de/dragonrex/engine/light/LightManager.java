package de.dragonrex.engine.light;

import de.dragonrex.engine.light.impl.DirectionalLight;

public class LightManager {

    private static DirectionalLight sun;

    public static void setSun(DirectionalLight light) {
        sun = light;
    }

    public static DirectionalLight getSun() {
        return sun;
    }
}
