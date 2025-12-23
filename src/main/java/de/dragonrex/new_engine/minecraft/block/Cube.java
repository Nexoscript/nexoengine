package de.dragonrex.new_engine.minecraft.block;

import de.dragonrex.new_engine.engine.objects.GameObject3D;
import de.dragonrex.new_engine.shader.Mesh;
import de.dragonrex.new_engine.shader.Shader;

public class Cube extends GameObject3D {

    public Cube(float x, float y, float z, Mesh mesh, Shader shader) {
        super(x, y, z, mesh, shader);
    }

    @Override
    public void update() {

    }
}
