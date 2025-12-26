package de.dragonrex.engine.rendering.primitives;

import de.dragonrex.engine.core.Component;
import de.dragonrex.engine.core.GameObject;
import de.dragonrex.engine.rendering.RenderQueue;
import de.dragonrex.engine.rendering.material.Material;
import de.dragonrex.engine.rendering.mesh.Mesh;
import de.dragonrex.engine.rendering.mesh.MeshFactory;

public class CubeRenderer extends Component {

    private static Mesh cubeMesh;
    private final Material material;
    private MeshRenderer meshRenderer;

    public CubeRenderer(Material material, GameObject parent) {
        this.material = material;
        if (cubeMesh == null) {
            cubeMesh = MeshFactory.getCube();
        }

        meshRenderer = new MeshRenderer(cubeMesh, material);
        meshRenderer.setGameObject(parent);
    }

    @Override
    public void render() {
        RenderQueue.submit(meshRenderer);
    }
}
