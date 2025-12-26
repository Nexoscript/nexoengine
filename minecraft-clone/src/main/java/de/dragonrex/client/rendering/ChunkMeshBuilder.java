package de.dragonrex.client.rendering;

import de.dragonrex.client.world.BlockType;
import de.dragonrex.client.world.Chunk;
import de.dragonrex.engine.rendering.Mesh;

import java.util.ArrayList;
import java.util.List;

public class ChunkMeshBuilder {

    public static Mesh build(Chunk chunk) {

        List<Float> pos = new ArrayList<>();
        List<Float> norm = new ArrayList<>();
        List<Float> uv = new ArrayList<>();
        List<Integer> ind = new ArrayList<>();

        int offset = 0;

        for (int x = 0; x < Chunk.SIZE; x++) {
            for (int y = 0; y < Chunk.HEIGHT; y++) {
                for (int z = 0; z < Chunk.SIZE; z++) {

                    if (chunk.getBlock(x, y, z) == BlockType.AIR)
                        continue;

                    float wx = x + chunk.getPos().x * Chunk.SIZE;
                    float wy = y;
                    float wz = z + chunk.getPos().z * Chunk.SIZE;

                    // +Y (Top)
                    if (chunk.getBlock(x, y + 1, z) == BlockType.AIR)
                        offset = addTop(pos, norm, uv, ind, offset, wx, wy, wz);

                    // -Y (Bottom)
                    if (chunk.getBlock(x, y - 1, z) == BlockType.AIR)
                        offset = addBottom(pos, norm, uv, ind, offset, wx, wy, wz);

                    // -Z (North)
                    if (chunk.getBlock(x, y, z - 1) == BlockType.AIR)
                        offset = addNorth(pos, norm, uv, ind, offset, wx, wy, wz);

                    // +Z (South)
                    if (chunk.getBlock(x, y, z + 1) == BlockType.AIR)
                        offset = addSouth(pos, norm, uv, ind, offset, wx, wy, wz);

                    // +X (East)
                    if (chunk.getBlock(x + 1, y, z) == BlockType.AIR)
                        offset = addEast(pos, norm, uv, ind, offset, wx, wy, wz);

                    // -X (West)
                    if (chunk.getBlock(x - 1, y, z) == BlockType.AIR)
                        offset = addWest(pos, norm, uv, ind, offset, wx, wy, wz);
                }
            }
        }

        return new Mesh(
                toFloatArray(pos),
                toFloatArray(norm),
                toFloatArray(uv),
                toIntArray(ind)
        );
    }

    /* ================= TOP ================= */
    private static int addTop(List<Float> p, List<Float> n, List<Float> uv, List<Integer> i, int o, float x, float y, float z) {
        return quad(p,n,uv,i,o,
                x, y+1, z,
                x+1, y+1, z,
                x+1, y+1, z+1,
                x, y+1, z+1,
                0,1,0
        );
    }

    /* ================= BOTTOM ================= */
    private static int addBottom(List<Float> p, List<Float> n, List<Float> uv, List<Integer> i, int o, float x, float y, float z) {
        return quad(p,n,uv,i,o,
                x, y, z+1,
                x+1, y, z+1,
                x+1, y, z,
                x, y, z,
                0,-1,0
        );
    }

    /* ================= NORTH ================= */
    private static int addNorth(List<Float> p, List<Float> n, List<Float> uv, List<Integer> i, int o, float x, float y, float z) {
        return quad(p,n,uv,i,o,
                x+1, y, z,
                x+1, y+1, z,
                x, y+1, z,
                x, y, z,
                0,0,-1
        );
    }

    /* ================= SOUTH ================= */
    private static int addSouth(List<Float> p, List<Float> n, List<Float> uv, List<Integer> i, int o, float x, float y, float z) {
        return quad(p,n,uv,i,o,
                x, y, z+1,
                x, y+1, z+1,
                x+1, y+1, z+1,
                x+1, y, z+1,
                0,0,1
        );
    }

    /* ================= EAST ================= */
    private static int addEast(List<Float> p, List<Float> n, List<Float> uv, List<Integer> i, int o, float x, float y, float z) {
        return quad(p,n,uv,i,o,
                x+1, y, z+1,
                x+1, y+1, z+1,
                x+1, y+1, z,
                x+1, y, z,
                1,0,0
        );
    }

    /* ================= WEST ================= */
    private static int addWest(List<Float> p, List<Float> n, List<Float> uv, List<Integer> i, int o, float x, float y, float z) {
        return quad(p,n,uv,i,o,
                x, y, z,
                x, y+1, z,
                x, y+1, z+1,
                x, y, z+1,
                -1,0,0
        );
    }

    /* ================= QUAD HELPER ================= */
    private static int quad(
            List<Float> p, List<Float> n, List<Float> uv, List<Integer> i, int o,
            float x1,float y1,float z1,
            float x2,float y2,float z2,
            float x3,float y3,float z3,
            float x4,float y4,float z4,
            float nx,float ny,float nz
    ) {
        add(p,n,uv,x1,y1,z1,nx,ny,nz,0,0);
        add(p,n,uv,x2,y2,z2,nx,ny,nz,1,0);
        add(p,n,uv,x3,y3,z3,nx,ny,nz,1,1);
        add(p,n,uv,x4,y4,z4,nx,ny,nz,0,1);

        i.add(o); i.add(o+1); i.add(o+2);
        i.add(o); i.add(o+2); i.add(o+3);

        return o + 4;
    }

    private static void add(List<Float> p,List<Float> n,List<Float> uv,
                            float x,float y,float z,
                            float nx,float ny,float nz,
                            float u,float v) {
        p.add(x); p.add(y); p.add(z);
        n.add(nx); n.add(ny); n.add(nz);
        uv.add(u); uv.add(v);
    }

    private static float[] toFloatArray(List<Float> l) {
        float[] a = new float[l.size()];
        for (int i = 0; i < l.size(); i++) a[i] = l.get(i);
        return a;
    }

    private static int[] toIntArray(List<Integer> l) {
        int[] a = new int[l.size()];
        for (int i = 0; i < l.size(); i++) a[i] = l.get(i);
        return a;
    }
}

