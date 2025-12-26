package de.dragonrex.engine.ui.rendering;

import de.dragonrex.engine.rendering.shader.Shader;
import de.dragonrex.engine.window.Window;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;

public final class UIRenderer {

    private static Shader shader;
    private static Matrix4f projection = new Matrix4f();

    private UIRenderer() {}

    /* ======================
       Frame handling
       ====================== */

    public static void begin() {
        if (shader == null) {
            shader = UIShader.createShader();
        }

        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        shader.bind();

        projection.identity().ortho2D(
                0,
                Window.getWidth(),
                Window.getHeight(),
                0
        );

        shader.setUniform("projection", projection);
    }

    public static void end() {
        shader.unbind();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    /* ======================
       Drawing
       ====================== */

    public static void drawRect(
            float x, float y,
            float w, float h,
            Vector4f color
    ) {
        Matrix4f model = new Matrix4f()
                .translate(x, y, 0)
                .scale(w, h, 1);

        shader.setUniform("model", model);
        shader.setUniform("color", color);

        UIQuadMesh.get().render();
    }
}

