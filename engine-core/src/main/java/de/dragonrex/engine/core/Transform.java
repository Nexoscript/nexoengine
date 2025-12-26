package de.dragonrex.engine.core;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class Transform extends Component {

    private final Vector3f position = new Vector3f();
    private final Vector3f rotation = new Vector3f();
    private final Vector3f scale = new Vector3f(1, 1, 1);

    private Transform parent;
    private final List<Transform> children = new ArrayList<>();

    private final Matrix4f localMatrix = new Matrix4f();
    private final Matrix4f worldMatrix = new Matrix4f();

    public void setParent(Transform parent) {
        if (this.parent != null) {
            this.parent.children.remove(this);
        }
        this.parent = parent;
        if (parent != null) {
            parent.children.add(this);
        }
    }

    public Matrix4f getWorldMatrix() {
        updateMatrices();
        return worldMatrix;
    }

    private void updateMatrices() {
        localMatrix.identity()
                .translate(position)
                .rotateX(rotation.x)
                .rotateY(rotation.y)
                .rotateZ(rotation.z)
                .scale(scale);

        if (parent != null) {
            worldMatrix.set(parent.getWorldMatrix()).mul(localMatrix);
        } else {
            worldMatrix.set(localMatrix);
        }
    }

    // Getter & Setter
    public Vector3f getPosition() { return position; }
    public Vector3f getRotation() { return rotation; }
    public Vector3f getScale() { return scale; }

    public List<Transform> getChildren() { return children; }

    public void setPosition(Vector3f position) { this.position.set(position); }
    public void setRotation(Vector3f rotation) { this.rotation.set(rotation); }
    public void setScale(Vector3f scale) { this.scale.set(scale); }
}

