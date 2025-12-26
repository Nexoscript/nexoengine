package de.dragonrex.client.scene;

import de.dragonrex.client.player.PlayerController;
import de.dragonrex.client.rendering.ChunkMeshBuilder;
import de.dragonrex.client.world.Chunk;
import de.dragonrex.client.world.World;
import de.dragonrex.engine.components.CameraComponent;
import de.dragonrex.engine.components.Transform;
import de.dragonrex.engine.core.GameObject;
import de.dragonrex.engine.light.LightManager;
import de.dragonrex.engine.light.impl.DirectionalLight;
import de.dragonrex.engine.rendering.Material;
import de.dragonrex.engine.rendering.Mesh;
import de.dragonrex.engine.rendering.MeshRenderer;
import de.dragonrex.engine.rendering.RenderSystem;
import de.dragonrex.engine.rendering.shader.ShaderLibrary;
import de.dragonrex.engine.scene.Scene;

public class GameScene extends Scene {

    @Override
    protected void onRender(CameraComponent camera) {
        RenderSystem.render(camera);
    }

    @Override
    protected void onInit() {
        GameObject player = new GameObject();

        Transform transform = new Transform();
        transform.getPosition().set(0, 0, 0);
        transform.getRotation().set(0, 0, 0);

        CameraComponent camera = new CameraComponent();
        camera.setPrimary(true);

        player.addComponent(transform);
        player.addComponent(camera);
        player.addComponent(new PlayerController());

        add(player);

        DirectionalLight sun = new DirectionalLight();
        sun.getDirection().set(-0.3f, -1.0f, -0.2f);
        sun.getColor().set(1.0f, 0.95f, 0.85f);
        sun.intensity = 1.2f;

        LightManager.setSun(sun);

        World world = new World();

        Chunk chunk = world.getChunk(0, 0);

        Mesh mesh = ChunkMeshBuilder.build(chunk);
        Material mat = new Material(ShaderLibrary.createBasicShader());
        mat.setColor(0.2f, 0.8f, 0.2f, 1.0f);

        GameObject chunkObj = new GameObject();

        Transform transform1 = new Transform();
        transform1.getPosition().set(0, 0, -5);
        transform1.getRotation().set(0, 0, 0);
        chunkObj.addComponent(transform1);
        chunkObj.addComponent(new MeshRenderer(mesh, mat));

        add(chunkObj);
    }
}
