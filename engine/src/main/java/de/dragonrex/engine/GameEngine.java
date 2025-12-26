package de.dragonrex.engine;

import de.dragonrex.engine.input.Input;
import de.dragonrex.engine.scene.Scene;
import de.dragonrex.engine.scene.SceneManager;
import de.dragonrex.engine.window.Window;
import de.dragonrex.engine.window.WindowConfig;

public class GameEngine {

    private boolean running;
    public static Window window;
    public WindowConfig config;

    private long lastFrameTime;

    public GameEngine(WindowConfig config) {
        this.config = config;
    }

    public GameEngine() {
        this(new WindowConfig());
    }

    public void start(Scene startScene) {
        SceneManager.setScene(startScene);
        this.running = true;

        init();
        loop();
        shutdown();
    }

    private void init() {
        window = new Window(config);
        window.create();

        Input.init(window.getHandle());

        lastFrameTime = System.nanoTime();
    }

    private void loop() {
        while (running && !window.shouldClose()) {
            float delta = calculateDeltaTime();

            SceneManager.update(delta);
            Input.update();
            SceneManager.render();

            window.update();
        }
    }

    private void shutdown() {
        SceneManager.shutdown();
        window.destroy();
    }

    public void stop() {
        running = false;
    }

    private float calculateDeltaTime() {
        long now = System.nanoTime();
        float delta = (now - lastFrameTime) / 1_000_000_000.0f;
        lastFrameTime = now;
        return delta;
    }
}
