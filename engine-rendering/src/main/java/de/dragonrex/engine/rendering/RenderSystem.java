package de.dragonrex.engine.rendering;
import de.dragonrex.engine.core.Transform;
import de.dragonrex.engine.rendering.camera.CameraComponent;
import de.dragonrex.engine.rendering.camera.CameraSystem;
import de.dragonrex.engine.rendering.light.DirectionalLight;
import de.dragonrex.engine.rendering.light.LightManager;
import de.dragonrex.engine.rendering.material.Material;
import de.dragonrex.engine.rendering.primitives.MeshRenderer;
import de.dragonrex.engine.rendering.shader.Shader;
import org.lwjgl.opengl.GL11;

public final class RenderSystem {

    private RenderSystem() {
    }

    public static void render() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        GL11.glFrontFace(GL11.GL_CCW);


        GL11.glClearColor(0.1f, 0.1f, 0.3f, 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        CameraComponent camera = CameraSystem.getMainCamera();
        if (camera == null) return;

        for (MeshRenderer mr : RenderQueue.getQueue()) {
            Material mat = mr.getMaterial();
            Shader shader = mat.getShader();

            shader.bind();

            shader.setUniform("view", camera.getViewMatrix());
            shader.setUniform("projection", camera.getProjectionMatrix());

            Transform camT = camera.getGameObject().getComponent(Transform.class);
            shader.setUniform("viewPos", camT.getPosition());

            mr.draw();

            DirectionalLight sun = LightManager.getSun();

            if (sun != null) {
                shader.setUniform("sun.direction", sun.getDirection());
                shader.setUniform("sun.color", sun.getColor());
                shader.setUniform("sun.intensity", sun.getIntensity());
            }
        }

        RenderQueue.clear();
    }
}
