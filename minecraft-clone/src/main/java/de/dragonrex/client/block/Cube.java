package de.dragonrex.client.block;

import de.dragonrex.engine.engine.objects.GameObject3D;
import de.dragonrex.engine.shader.Mesh;
import de.dragonrex.engine.shader.Shader;

public class Cube extends GameObject3D {

    public Cube(float x, float y, float z, Mesh mesh, Shader shader) {
        super(x, y, z, mesh, shader);
    }

    @Override
    public void update() {

    }
}
