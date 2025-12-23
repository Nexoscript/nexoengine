package de.dragonrex.engine.engine;

import de.dragonrex.engine.engine.objects.GameObject3D;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public class GameObjectManager3D {
    private static final List<GameObject3D> objects = new ArrayList<>();

    public static void add(GameObject3D obj) { objects.add(obj); }
    public static void updateAll() { objects.forEach(GameObject3D::update); }
    public static void renderAll(Matrix4f projection, Matrix4f view) {
        objects.forEach(obj -> obj.render(projection, view));
    }
    public static void cleanupAll() { objects.forEach(GameObject3D::cleanup); }
}
