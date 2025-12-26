package de.dragonrex.engine.core;

import java.util.ArrayList;
import java.util.List;

public class GameObject {

    private List<Component> components = new ArrayList<>();

    public void addComponent(Component component) {
        component.setGameObject(this);
        components.add(component);
    }

    public <T extends Component> T getComponent(Class<T> type) {
        for (Component c : components) {
            if (type.isAssignableFrom(c.getClass())) {
                return type.cast(c);
            }
        }
        return null;
    }

    public void start() {
        components.forEach(Component::start);
    }

    public void update(float deltaTime) {
        components.forEach(c -> c.update(deltaTime));
    }

    public void render() {
        components.forEach(Component::render);
    }

    public void destroy() {
        components.forEach(Component::onDestroy);
    }
}

