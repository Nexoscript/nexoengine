package de.dragonrex.engine.ui.controls;

import de.dragonrex.engine.ui.UIElement;
import de.dragonrex.engine.ui.UITransform;
import de.dragonrex.engine.ui.rendering.UIRenderer;
import de.dragonrex.engine.input.Mouse;
import org.joml.Vector4f;
import static org.lwjgl.glfw.GLFW.*;

public class UIButton extends UIElement {

    private Runnable onClick;

    private Vector4f normalColor = new Vector4f(0.2f, 0.6f, 0.9f, 1.0f);
    private Vector4f hoverColor  = new Vector4f(0.3f, 0.7f, 1.0f, 1.0f);
    private Vector4f pressColor  = new Vector4f(0.15f, 0.5f, 0.8f, 1.0f);

    private boolean hovered = false;
    private boolean pressedLastFrame = false;

    public UIButton(float x, float y, float w, float h, Runnable onClick) {
        this.transform = new UITransform(x, y, w, h);
        this.onClick = onClick;
    }

    @Override
    public void update(float dt) {
        float mx = (float) Mouse.getX();
        float my = (float) Mouse.getY();

        hovered = contains(mx, my);

        boolean pressedNow = Mouse.isDown(GLFW_MOUSE_BUTTON_LEFT);

        // Click = gedrückt → losgelassen über dem Button
        if (hovered && pressedLastFrame && !pressedNow) {
            if (onClick != null) {
                onClick.run();
            }
        }

        pressedLastFrame = pressedNow;
    }

    @Override
    public void render() {
        if (!visible) return;

        Vector4f color = normalColor;

        if (hovered) {
            color = Mouse.isDown(GLFW_MOUSE_BUTTON_LEFT)
                    ? pressColor
                    : hoverColor;
        }

        UIRenderer.drawRect(
                transform.x,
                transform.y,
                transform.width,
                transform.height,
                color
        );
    }

    /* ======================
       Customization
       ====================== */

    public void setNormalColor(Vector4f color) {
        this.normalColor = color;
    }

    public void setHoverColor(Vector4f color) {
        this.hoverColor = color;
    }

    public void setPressColor(Vector4f color) {
        this.pressColor = color;
    }
}
