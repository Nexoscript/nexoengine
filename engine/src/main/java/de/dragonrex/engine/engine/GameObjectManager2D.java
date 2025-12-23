package de.dragonrex.engine.engine;

import de.dragonrex.engine.engine.objects.GameObject2D;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public class GameObjectManager2D {
    private static final List<GameObject2D> objects = new ArrayList<>();
    public static void add(GameObject2D obj) { objects.add(obj); }
    public static void updateAll() { objects.forEach(GameObject2D::update); }
    public static void renderAll(Matrix4f projection) { objects.forEach(obj -> obj.render(projection)); }
    public static void cleanupAll() {
        for (GameObject2D obj : objects) {
            if (obj.mesh != null) {
                obj.mesh.cleanup(); // ruft Mesh.cleanup() auf
            }
        }
        objects.clear(); // Liste leeren
    }
}
