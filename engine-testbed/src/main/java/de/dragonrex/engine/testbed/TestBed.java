package de.dragonrex.engine.testbed;

import de.dragonrex.engine.runtime.GameEngine;
import de.dragonrex.engine.testbed.scenes.RenderTestScene;

public class TestBed {
    void main() {
        GameEngine engine = new GameEngine();
        engine.config.title = "TestBed";
        engine.start(new RenderTestScene());
    }
}
