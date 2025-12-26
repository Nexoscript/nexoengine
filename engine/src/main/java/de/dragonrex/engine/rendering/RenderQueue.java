package de.dragonrex.engine.rendering;

import java.util.ArrayList;
import java.util.List;

public final class RenderQueue {

    private static final List<MeshRenderer> queue = new ArrayList<>();

    private RenderQueue() {}

    public static void submit(MeshRenderer renderer) {
        queue.add(renderer);
    }

    static List<MeshRenderer> getQueue() {
        return queue;
    }

    static void clear() {
        queue.clear();
    }
}
