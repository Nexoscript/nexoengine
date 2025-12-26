package de.dragonrex.engine.rendering.primitives;

import de.dragonrex.engine.core.Component;
import de.dragonrex.engine.core.GameObject;
import de.dragonrex.engine.rendering.RenderQueue;
import de.dragonrex.engine.rendering.material.Material;
import de.dragonrex.engine.rendering.mesh.Mesh;
import de.dragonrex.engine.rendering.mesh.MeshFactory;

public class CapsuleRenderer extends Component {

    private static Mesh capsuleMesh;
    private final Material material;
    private final float radius;
    private final float height;
    private final int segments;
    private MeshRenderer meshRenderer;

    public CapsuleRenderer(Material material, float radius, float height, int segments, GameObject parent) {
        this.material = material;
        this.radius = radius;
        this.height = height;
        this.segments = segments;
        capsuleMesh = MeshFactory.getCapsule(radius, height, segments);
        meshRenderer = new MeshRenderer(capsuleMesh, material);
        meshRenderer.setGameObject(parent);
    }

    @Override
    public void render() {
        RenderQueue.submit(meshRenderer);
    }
}

