package de.dragonrex.engine.components;

import de.dragonrex.engine.core.Component;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class CameraComponent extends Component {

    private final Matrix4f projection = new Matrix4f();
    private final Matrix4f view = new Matrix4f();

    private float fov = (float) Math.toRadians(70.0);
    private float near = 0.1f;
    private float far = 2000f;
    private float aspect = 16f / 9f;

    private boolean primary = false;

    @Override
    public void start() {
        updateProjection();
    }

    public void updateView() {
        Transform t = gameObject.getComponent(Transform.class);
        if (t == null) return;

        Vector3f pos = t.getPosition();
        Vector3f rot = t.getRotation();

        view.identity()
                .rotateX(-rot.x)
                .rotateY(-rot.y)
                .rotateZ(-rot.z)
                .translate(-pos.x, -pos.y, -pos.z);
    }

    private void updateProjection() {
        projection.identity().perspective(fov, aspect, near, far);
    }

    public Matrix4f getViewMatrix() {
        updateView();
        return view;
    }

    public Matrix4f getProjectionMatrix() {
        return projection;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public void setAspect(float aspect) {
        this.aspect = aspect;
        updateProjection();
    }
}

