package de.dragonrex.engine.ui;

import de.dragonrex.engine.ui.rendering.UIRenderer;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class UISystem {
    private static List<UIElement> elements = new ArrayList<>();

    public static void add(UIElement element) {
        elements.add(element);
    }

    public static void update(float deltaTime) {
        for (UIElement element : elements) {
            if(element.visible()) element.update(deltaTime);
        }
    }

    public static void render() {
        UIRenderer.begin();
        for (UIElement element : elements) {
            element.render();
        }
        UIRenderer.end();
    }
}
