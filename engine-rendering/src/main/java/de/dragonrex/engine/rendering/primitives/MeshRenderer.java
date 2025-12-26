package de.dragonrex.engine.rendering.primitives;


import de.dragonrex.engine.core.Component;
import de.dragonrex.engine.core.Transform;
import de.dragonrex.engine.rendering.RenderQueue;
import de.dragonrex.engine.rendering.material.Material;
import de.dragonrex.engine.rendering.mesh.Mesh;

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
