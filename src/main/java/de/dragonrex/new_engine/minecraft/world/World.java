package de.dragonrex.new_engine.minecraft.world;

import de.dragonrex.new_engine.engine.GameObjectManager2D;
import de.dragonrex.new_engine.engine.GameObjectManager3D;
import de.dragonrex.new_engine.minecraft.block.Block;
import de.dragonrex.new_engine.minecraft.block.BlockType;
import de.dragonrex.new_engine.minecraft.chunk.Chunk;
import de.dragonrex.new_engine.minecraft.chunk.ChunkMeshBuilder;
import de.dragonrex.new_engine.minecraft.chunk.ChunkObject;
import de.dragonrex.new_engine.shader.Mesh;
import de.dragonrex.new_engine.shader.Shader;

import java.util.ArrayList;
import java.util.List;

public class World {
    private int chunksX, chunksZ;
    private List<ChunkObject> chunks;
    private ChunkMeshBuilder chunkMeshBuilder;
    private Shader shader;

    public World(int chunksX, int chunksZ, Shader shader) {
        this.chunksX = chunksX;
        this.chunksZ = chunksZ;
        this.shader = shader;
        this.chunks = new ArrayList<>();
        this.chunkMeshBuilder = new ChunkMeshBuilder();
    }

    public void generateChunks() {
        for (int cx = 0; cx < chunksX; cx++) {
            for (int cz = 0; cz < chunksZ; cz++ ) {

                Chunk chunk = new Chunk();
                for (int x = 0; x < chunk.getSize(); x++) {
                    for (int y = 0; y < chunk.getSize(); y++) {
                        for (int z = 0; z < chunk.getSize(); z++) {
                            if(y < 5) {
                                chunk.setBlock(x, y, z, new Block(x, y, z, BlockType.STONE));
                            } else if (y < 9) {
                                chunk.setBlock(x, y, z, new Block(x, y, z, BlockType.DIRT));
                            } else if (y == 9) {
                                chunk.setBlock(x, y, z, new Block(x, y, z, BlockType.GRASS));
                            }
                            // y >= 10: Luft (null)
                        }
                    }
                }

                Mesh mesh = this.chunkMeshBuilder.buildMesh(chunk);

                float worldX = cx * chunk.getSize();
                float worldZ = cz * chunk.getSize();

                ChunkObject chunkObject = new ChunkObject(worldX, 0f, worldZ, mesh, shader, chunk);
                chunks.add(chunkObject);
                GameObjectManager3D.add(chunkObject);
            }
        }
    }

    public List<ChunkObject> getChunks() {
        return chunks;
    }

    public void setBlock(int worldX, int worldY, int worldZ, BlockType type) {
        int chunkX = worldX / Chunk.CHUNK_SIZE;
        int chunkZ = worldZ / Chunk.CHUNK_SIZE;
        int localX = worldX % Chunk.CHUNK_SIZE;
        int localZ = worldZ % Chunk.CHUNK_SIZE;

        ChunkObject chunkObject = chunks.get(chunkZ * chunksX + chunkX);
        chunkObject.getChunk().setBlock(localX, worldY, localZ, new Block(localX, worldY, localZ, type));

        Mesh newMesh = this.chunkMeshBuilder.buildMesh(chunkObject.getChunk());
        chunkObject.setMesh(newMesh);
    }
}
