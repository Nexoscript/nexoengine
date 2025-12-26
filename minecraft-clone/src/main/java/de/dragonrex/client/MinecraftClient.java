package de.dragonrex.client;

import de.dragonrex.client.scene.GameScene;
import de.dragonrex.engine.GameEngine;

public class MinecraftClient {

    void main() {
        GameEngine engine = new GameEngine();
        engine.config.title = "Minecraft Clone";
        engine.start(new GameScene());
    }
}
