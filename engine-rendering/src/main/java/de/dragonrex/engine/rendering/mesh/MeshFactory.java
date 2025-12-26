package de.dragonrex.engine.rendering.mesh;

import java.util.*;

public final class MeshFactory {

    private static Mesh cube;
    private static final Map<Integer, Mesh> sphereCache = new HashMap<>();
    private static final Map<String, Mesh> capsuleCache = new HashMap<>();

    private MeshFactory() {}

    /* =========================
       CUBE
       ========================= */

    public static Mesh getCube() {
        if (cube == null) {
            cube = Mesh.createCube();
        }
        return cube;
    }

    /* =========================
       SPHERE
       ========================= */

    public static Mesh getSphere(int segments) {
        return sphereCache.computeIfAbsent(segments, MeshFactory::createSphere);
    }

    private static Mesh createSphere(int segments) {

        List<Float> pos = new ArrayList<>();
        List<Float> norm = new ArrayList<>();
        List<Integer> ind = new ArrayList<>();

        for (int y = 0; y <= segments; y++) {
            float v = (float) y / segments;
            float phi = (float) (v * Math.PI);

            for (int x = 0; x <= segments; x++) {
                float u = (float) x / segments;
                float theta = (float) (u * Math.PI * 2);

                float sx = (float) (Math.sin(phi) * Math.cos(theta));
                float sy = (float) Math.cos(phi);
                float sz = (float) (Math.sin(phi) * Math.sin(theta));

                pos.add(sx * 0.5f);
                pos.add(sy * 0.5f);
                pos.add(sz * 0.5f);

                norm.add(sx);
                norm.add(sy);
                norm.add(sz);
            }
        }

        for (int y = 0; y < segments; y++) {
            for (int x = 0; x < segments; x++) {
                int i = y * (segments + 1) + x;

                ind.add(i);
                ind.add(i + segments + 1);
                ind.add(i + 1);

                ind.add(i + 1);
                ind.add(i + segments + 1);
                ind.add(i + segments + 2);
            }
        }

        return new Mesh(
                toFloatArray(pos),
                toFloatArray(norm),
                null,
                toIntArray(ind)
        );
    }

    /* =========================
       CAPSULE
       ========================= */

    public static Mesh getCapsule(float radius, float height, int segments) {
        String key = radius + "_" + height + "_" + segments;
        return capsuleCache.computeIfAbsent(key,
                k -> createCapsule(radius, height, segments));
    }

    private static Mesh createCapsule(float radius, float height, int segments) {

        List<Float> pos = new ArrayList<>();
        List<Float> norm = new ArrayList<>();
        List<Integer> ind = new ArrayList<>();

        float half = height * 0.5f;
        int ringCount = segments;

        // === Top hemisphere ===
        for (int y = 0; y <= segments; y++) {
            float v = (float) y / segments;
            float phi = (float) (v * Math.PI * 0.5f);

            for (int x = 0; x <= segments; x++) {
                float u = (float) x / segments;
                float theta = (float) (u * Math.PI * 2);

                float nx = (float) (Math.sin(phi) * Math.cos(theta));
                float ny = (float) Math.cos(phi);
                float nz = (float) (Math.sin(phi) * Math.sin(theta));

                pos.add(nx * radius);
                pos.add(ny * radius + half);
                pos.add(nz * radius);

                norm.add(nx);
                norm.add(ny);
                norm.add(nz);
            }
        }

        // === Bottom hemisphere ===
        for (int y = 0; y <= segments; y++) {
            float v = (float) y / segments;
            float phi = (float) (Math.PI * 0.5f + v * Math.PI * 0.5f);

            for (int x = 0; x <= segments; x++) {
                float u = (float) x / segments;
                float theta = (float) (u * Math.PI * 2);

                float nx = (float) (Math.sin(phi) * Math.cos(theta));
                float ny = (float) Math.cos(phi);
                float nz = (float) (Math.sin(phi) * Math.sin(theta));

                pos.add(nx * radius);
                pos.add(ny * radius - half);
                pos.add(nz * radius);

                norm.add(nx);
                norm.add(ny);
                norm.add(nz);
            }
        }

        int vertsPerRing = segments + 1;
        int rings = segments * 2;

        for (int y = 0; y < rings; y++) {
            for (int x = 0; x < segments; x++) {
                int i = y * vertsPerRing + x;

                ind.add(i);
                ind.add(i + vertsPerRing);
                ind.add(i + 1);

                ind.add(i + 1);
                ind.add(i + vertsPerRing);
                ind.add(i + vertsPerRing + 1);
            }
        }

        return new Mesh(
                toFloatArray(pos),
                toFloatArray(norm),
                null,
                toIntArray(ind)
        );
    }

    /* =========================
       UTIL
       ========================= */

    private static float[] toFloatArray(List<Float> list) {
        float[] arr = new float[list.size()];
        for (int i = 0; i < list.size(); i++) arr[i] = list.get(i);
        return arr;
    }

    private static int[] toIntArray(List<Integer> list) {
        int[] arr = new int[list.size()];
        for (int i = 0; i < list.size(); i++) arr[i] = list.get(i);
        return arr;
    }
}

