package de.dragonrex.client.world;

import java.util.Objects;

public class ChunkPos {

    public final int x;
    public final int z;

    public ChunkPos(int x, int z) {
        this.x = x;
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ChunkPos other)) return false;
        return x == other.x && z == other.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, z);
    }
}

