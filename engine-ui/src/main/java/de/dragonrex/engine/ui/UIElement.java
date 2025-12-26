package de.dragonrex.engine.ui;

public abstract class UIElement {
    protected UITransform transform = new UITransform();
    protected boolean visible = true;

    public UITransform getTransform() {
        return transform;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean visible() {
        return visible;
    }

    public abstract void render();
    public abstract void update(float deltaTime);

    protected boolean contains(float mx, float my) {
        return mx >= transform.x &&
                mx <= transform.x + transform.width &&
                my >= transform.y &&
                my <= transform.y + transform.height;
    }
}
