package de.dragonrex.engine.engine.objects;

import de.dragonrex.engine.shader.Mesh;
import de.dragonrex.engine.shader.Shader;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL20;

public abstract class GameObject2D {
    public float x, y;
    public Mesh mesh;
    protected Shader shader;

    public abstract void update();
    public void render(Matrix4f projection) {
        shader.bind();
        Matrix4f model = new Matrix4f().translate(x, y, 0f);
        int loc = GL20.glGetUniformLocation(shader.programId, "transform");
        GL20.glUniformMatrix4fv(loc, false, projection.mul(model, new Matrix4f()).get(new float[16]));
        mesh.render();
        shader.unbind();
    }
}
