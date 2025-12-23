package de.dragonrex.engine.engine.objects;

import de.dragonrex.engine.shader.Mesh;
import de.dragonrex.engine.shader.Shader;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL20;

public abstract class GameObject3D {
    public float x, y, z;
    protected float angle = 0f;

    protected Mesh mesh;
    protected Shader shader;

    public GameObject3D(float x, float y, float z, Mesh mesh, Shader shader) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.mesh = mesh;
        this.shader = shader;
    }

    public abstract void update();

    public void render(Matrix4f projection, Matrix4f view) {
        shader.bind();

        // Model-Matrix
        Matrix4f model = new Matrix4f()
                .translate(x, y, z)
                .rotate((float)Math.toRadians(angle), 0f, 1f, 0f);

        // MVP
        Matrix4f mvp = new Matrix4f();
        projection.mul(view, mvp);
        mvp.mul(model);

        int loc = GL20.glGetUniformLocation(shader.programId, "transform");
        GL20.glUniformMatrix4fv(loc, false, mvp.get(new float[16]));

        mesh.render();
        shader.unbind();
    }

    public void cleanup() {
        mesh.cleanup();
    }
}

