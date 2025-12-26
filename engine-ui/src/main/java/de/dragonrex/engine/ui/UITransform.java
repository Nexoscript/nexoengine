package de.dragonrex.engine.ui;

public class UITransform {
    public float x;
    public float y;
    public float width;
    public float height;

    public UITransform(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public UITransform() {
        this(0, 0, 0, 0);
    }
}
