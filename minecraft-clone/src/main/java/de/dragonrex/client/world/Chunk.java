package de.dragonrex.client.world;

public class Chunk {

    public static final int SIZE = 16;
    public static final int HEIGHT = 64;

    private final ChunkPos pos;
    private final BlockType[] blocks;

    public Chunk(ChunkPos pos) {
        this.pos = pos;
        this.blocks = new BlockType[SIZE * HEIGHT * SIZE];
        generate();
    }

    private void generate() {
        for (int x = 0; x < SIZE; x++) {
            for (int z = 0; z < SIZE; z++) {
                setBlock(x, 0, z, BlockType.GRASS);
            }
        }
    }

    public BlockType getBlock(int x, int y, int z) {
        if (x < 0 || y < 0 || z < 0 || x >= SIZE || y >= HEIGHT || z >= SIZE)
            return BlockType.AIR;
        return blocks[index(x, y, z)];
    }

    public void setBlock(int x, int y, int z, BlockType type) {
        blocks[index(x, y, z)] = type;
    }

    private int index(int x, int y, int z) {
        return x + SIZE * (z + SIZE * y);
    }

    public ChunkPos getPos() {
        return pos;
    }
}

