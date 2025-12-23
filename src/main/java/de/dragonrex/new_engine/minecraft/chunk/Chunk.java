package de.dragonrex.new_engine.minecraft.chunk;

import de.dragonrex.new_engine.minecraft.block.Block;

public class Chunk {
    public static final int CHUNK_SIZE = 16;
    private Block[][][] blocks = new Block[CHUNK_SIZE][CHUNK_SIZE][CHUNK_SIZE];

    public Block getBlock(int x, int y, int z) {
        if(x < 0 || x >= CHUNK_SIZE || y < 0 || y >= CHUNK_SIZE || z < 0 || z >= CHUNK_SIZE) {
            return null;
        }
        return blocks[x][y][z];
    }

    public void setBlock(int x, int y, int z, Block block) {
        blocks[x][y][z] = block;
    }

    public Block[][][] getBlocks() {
        return blocks;
    }

    public int getSize() {
        return CHUNK_SIZE;
    }
}
