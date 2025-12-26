package de.dragonrex.client.world;

import java.util.HashMap;
import java.util.Map;

public class World {

    private final Map<ChunkPos, Chunk> chunks = new HashMap<>();

    public Chunk getChunk(int cx, int cz) {
        ChunkPos pos = new ChunkPos(cx, cz);
        return chunks.computeIfAbsent(pos, Chunk::new);
    }

    public BlockType getBlock(int wx, int wy, int wz) {

        if (wy < 0 || wy >= Chunk.HEIGHT)
            return BlockType.AIR;

        int cx = Math.floorDiv(wx, Chunk.SIZE);
        int cz = Math.floorDiv(wz, Chunk.SIZE);

        Chunk chunk = getChunk(cx, cz);
        if (chunk == null)
            return BlockType.AIR;

        int bx = Math.floorMod(wx, Chunk.SIZE);
        int bz = Math.floorMod(wz, Chunk.SIZE);

        return chunk.getBlock(bx, wy, bz);
    }
}
