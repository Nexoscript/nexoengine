package de.dragonrex.client.chunk;

import de.dragonrex.client.block.Cube;
import de.dragonrex.engine.shader.Mesh;
import de.dragonrex.engine.shader.Shader;

public class ChunkObject extends Cube {
    private Chunk chunk;
    private float worldX, worldZ;

    public ChunkObject(float worldX, float y, float worldZ, Mesh mesh, Shader shader, Chunk chunk) {
        super(worldX, y, worldZ, mesh, shader);
        this.chunk = chunk;
        this.worldX = worldX;
        this.worldZ = worldZ;
    }

    public Chunk getChunk() {
        return chunk;
    }

    public void setMesh(Mesh mesh) {
        super.mesh = mesh;
    }

    public float getWorldX() {
        return worldX;
    }

    public float getWorldZ() {
        return worldZ;
    }
}
