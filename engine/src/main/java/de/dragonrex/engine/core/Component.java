package de.dragonrex.engine.core;

public abstract class Component {

    protected GameObject gameObject;

    void setGameObject(GameObject obj) {
        this.gameObject = obj;
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public void start() {}
    public void update(float deltaTime) {}
    public void render() {}
    public void onDestroy() {}
}
