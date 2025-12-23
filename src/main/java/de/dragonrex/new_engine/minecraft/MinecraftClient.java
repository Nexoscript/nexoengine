package de.dragonrex.new_engine.minecraft;

import de.dragonrex.new_engine.engine.Engine;
import de.dragonrex.new_engine.engine.GameObjectManager2D;
import de.dragonrex.new_engine.engine.GameObjectManager3D;
import de.dragonrex.new_engine.input.Input;
import de.dragonrex.new_engine.minecraft.controller.MouseCameraController;
import de.dragonrex.new_engine.minecraft.player.Player;
import de.dragonrex.new_engine.minecraft.world.World;
import de.dragonrex.new_engine.shader.Shader;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

public class MinecraftClient extends Engine {

    private Shader shader;
    private World world;
    private Player player;

    public MinecraftClient() {
        super(1200, 800, "Minecraft Client");
    }

    @Override
    protected void initGame() {
        GL11.glClearColor(0.53f, 0.81f, 0.92f, 1f);
        GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
        shader = new Shader("vertex.glsl", "fragment.glsl");

        world = new World(4, 4, shader);
        world.generateChunks();

        camera3D.setPosition(32f, 40f, 32f);
        player = new Player(camera3D, world);
    }

    @Override
    protected void update(float deltaTime) {
        if(Input.isKeyPressed(GLFW.GLFW_KEY_SPACE)) {
            player.jump();
        }
        player.update(deltaTime);
        GameObjectManager3D.updateAll();
        GameObjectManager2D.updateAll();
    }

    @Override
    protected void render() {
        GameObjectManager3D.renderAll(camera3D.getProjection(), camera3D.getView());
        GameObjectManager2D.renderAll(camera2D.getProjection());
    }

    @Override
    protected void cleanupGame() {
        GameObjectManager3D.cleanupAll();
        GameObjectManager2D.cleanupAll();
        shader.cleanup();
    }

    void main() {
        new MinecraftClient().start();
    }
}

