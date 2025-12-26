package de.dragonrex.engine.rendering.primitives;

import de.dragonrex.engine.core.Component;
import de.dragonrex.engine.core.GameObject;
import de.dragonrex.engine.rendering.RenderQueue;
import de.dragonrex.engine.rendering.material.Material;
import de.dragonrex.engine.rendering.mesh.Mesh;
import de.dragonrex.engine.rendering.mesh.MeshFactory;

public class SphereRenderer extends Component {

    private static Mesh sphereMesh;
    private final Material material;
    private final int segments;
    private MeshRenderer meshRenderer;

    public SphereRenderer(Material material, int segments, GameObject parent) {
        this.material = material;
        this.segments = segments;
        sphereMesh = MeshFactory.getSphere(segments);
        meshRenderer = new MeshRenderer(sphereMesh, material);
        meshRenderer.setGameObject(parent);
    }

    @Override
    public void render() {
        RenderQueue.submit(meshRenderer);
    }
}

