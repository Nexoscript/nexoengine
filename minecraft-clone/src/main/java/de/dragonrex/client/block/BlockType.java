package de.dragonrex.client.block;

public enum BlockType {
    GRASS(new float[]{0f, 1f, 0f}),
    DIRT(new float[]{0.59f, 0.29f, 0f}),
    STONE(new float[]{0.5f, 0.5f, 0.5f});

    private float[] color;

    BlockType(float[] color) {
        this.color = color;
    }

    public float[] getColor() {
        return color;
    }
}
