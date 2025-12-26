package de.dragonrex.engine.ui.rendering;

import de.dragonrex.engine.rendering.mesh.Mesh;

public final class UIQuadMesh {

    private static Mesh quad;

    public static Mesh get() {
        if (quad == null) {
            quad = create();
        }
        return quad;
    }

    private static Mesh create() {
        float[] positions = {
                0, 0, 0,
                1, 0, 0,
                1, 1, 0,
                0, 1, 0
        };

        float[] uvs = {
                0, 0,
                1, 0,
                1, 1,
                0, 1
        };

        int[] indices = {
                0, 1, 2,
                2, 3, 0
        };

        return new Mesh(positions, null, uvs, indices);
    }
}

