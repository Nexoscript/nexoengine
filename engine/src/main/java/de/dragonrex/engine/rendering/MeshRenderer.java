package de.dragonrex.engine.rendering;

import de.dragonrex.engine.components.Transform;
import de.dragonrex.engine.core.Component;

public class MeshRenderer extends Component {

    private Mesh mesh;
    private Material material;

    public MeshRenderer(Mesh mesh, Material material) {
        this.mesh = mesh;
        this.material = material;
    }

    @Override
    public void render() {
        RenderQueue.submit(this);
    }

    public void draw() {
        Transform t = gameObject.getComponent(Transform.class);
        material.applyUniforms();
        material.getShader().setUniform("model", t.getWorldMatrix());
        mesh.render();
    }

    public Mesh getMesh() { return mesh; }
    public Material getMaterial() { return material; }
}
