package de.dragonrex.new_engine.minecraft.chunk;

import de.dragonrex.new_engine.minecraft.block.Block;
import de.dragonrex.new_engine.shader.Mesh;

import java.util.ArrayList;
import java.util.List;

public class ChunkMeshBuilder {

    public Mesh buildMesh(Chunk chunk) {
        List<Float> vertices = new ArrayList<>();

        for (int x = 0; x < chunk.getSize(); x++) {
            for (int y = 0; y < chunk.getSize(); y++) {
                for (int z = 0; z < chunk.getSize(); z++) {
                    addCube(vertices, chunk, x, y, z);
                }
            }
        }

        float[] verticesArray = new float[vertices.size()];
        for (int i = 0; i < vertices.size(); i++) {
            verticesArray[i] = vertices.get(i);
        }

        return new Mesh(verticesArray);
    }

    private void addCube(List<Float> vertices, Chunk chunk, int x, int y, int z) {
        Block block = chunk.getBlock(x, y, z);
        if(block == null) return;
        float[] color = block.type.getColor();

        if(chunk.getBlock(x, y, z+1) == null) addFace(vertices, FaceHelper.cubeFaceFront(x, y, z), color);
        if(chunk.getBlock(x, y, z-1) == null) addFace(vertices, FaceHelper.cubeFaceBack(x, y, z), color);
        if(chunk.getBlock(x-1, y, z) == null) addFace(vertices, FaceHelper.cubeFaceLeft(x, y, z), color);
        if(chunk.getBlock(x+1, y, z) == null) addFace(vertices, FaceHelper.cubeFaceRight(x, y, z), color);
        if(chunk.getBlock(x, y+1, z) == null) addFace(vertices, FaceHelper.cubeFaceTop(x, y, z), color);
        if(chunk.getBlock(x, y-1, z) == null) addFace(vertices, FaceHelper.cubeFaceBottom(x, y, z), color);
    }

    private void addFace(List<Float> vertices, float[][] corners, float[] color) {
        int[][] indices = {
                {0, 1, 2},
                {0, 2, 3}
        };
        for (int[] tri : indices) {
            for(int i : tri) {
                vertices.add(corners[i][0]);
                vertices.add(corners[i][1]);
                vertices.add(corners[i][2]);
                vertices.add(color[0]);
                vertices.add(color[1]);
                vertices.add(color[2]);
            }
        }
    }
}
