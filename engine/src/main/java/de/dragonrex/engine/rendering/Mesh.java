package de.dragonrex.engine.rendering;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.system.MemoryUtil;

public class Mesh {
    private int vaoId;
    private int posVboId;
    private int normalVboId;
    private int uvVboId;
    private int eboId;
    private int vertexCount;

    public Mesh(float[] positions, float[] normals, float[] uvs, int[] indices) {
        vertexCount = indices.length;

        vaoId = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoId);

        // Position VBO
        posVboId = GL15.glGenBuffers();
        FloatBuffer posBuffer = MemoryUtil.memAllocFloat(positions.length);
        posBuffer.put(positions).flip();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, posVboId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, posBuffer, GL15.GL_STATIC_DRAW);
        GL20.glEnableVertexAttribArray(0);
        GL20.glVertexAttribPointer(0, 3, GL15.GL_FLOAT, false, 0, 0);
        MemoryUtil.memFree(posBuffer);

        // Normal VBO
        if (normals != null && normals.length > 0) {
            normalVboId = GL15.glGenBuffers();
            FloatBuffer normalBuffer = MemoryUtil.memAllocFloat(normals.length);
            normalBuffer.put(normals).flip();
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, normalVboId);
            GL15.glBufferData(GL15.GL_ARRAY_BUFFER, normalBuffer, GL15.GL_STATIC_DRAW);
            GL20.glEnableVertexAttribArray(1);
            GL20.glVertexAttribPointer(1, 3, GL15.GL_FLOAT, false, 0, 0);
            MemoryUtil.memFree(normalBuffer);
        }

        // UV VBO
        if (uvs != null && uvs.length > 0) {
            uvVboId = GL15.glGenBuffers();
            FloatBuffer uvBuffer = MemoryUtil.memAllocFloat(uvs.length);
            uvBuffer.put(uvs).flip();
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, uvVboId);
            GL15.glBufferData(GL15.GL_ARRAY_BUFFER, uvBuffer, GL15.GL_STATIC_DRAW);
            GL20.glEnableVertexAttribArray(2);
            GL20.glVertexAttribPointer(2, 2, GL15.GL_FLOAT, false, 0, 0);
            MemoryUtil.memFree(uvBuffer);
        }

        // Index buffer
        eboId = GL15.glGenBuffers();
        IntBuffer indexBuffer = MemoryUtil.memAllocInt(indices.length);
        indexBuffer.put(indices).flip();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, eboId);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL15.GL_STATIC_DRAW);
        MemoryUtil.memFree(indexBuffer);

        GL30.glBindVertexArray(0);
    }

    public void render() {
        GL30.glBindVertexArray(vaoId);
        GL15.glDrawElements(GL15.GL_TRIANGLES, vertexCount, GL15.GL_UNSIGNED_INT, 0);
        GL30.glBindVertexArray(0);
    }

    public void cleanup() {
        GL15.glDeleteBuffers(posVboId);
        if (normalVboId != 0) GL15.glDeleteBuffers(normalVboId);
        if (uvVboId != 0) GL15.glDeleteBuffers(uvVboId);
        GL15.glDeleteBuffers(eboId);
        GL30.glDeleteVertexArrays(vaoId);
    }

    // Helper method: Create a cube mesh
    public static Mesh createCube() {
        float[] positions = {
                // Front face
                -0.5f, -0.5f,  0.5f,  0.5f, -0.5f,  0.5f,  0.5f,  0.5f,  0.5f, -0.5f,  0.5f,  0.5f,
                // Back face
                -0.5f, -0.5f, -0.5f, -0.5f,  0.5f, -0.5f,  0.5f,  0.5f, -0.5f,  0.5f, -0.5f, -0.5f,
                // Top face
                -0.5f,  0.5f, -0.5f, -0.5f,  0.5f,  0.5f,  0.5f,  0.5f,  0.5f,  0.5f,  0.5f, -0.5f,
                // Bottom face
                -0.5f, -0.5f, -0.5f,  0.5f, -0.5f, -0.5f,  0.5f, -0.5f,  0.5f, -0.5f, -0.5f,  0.5f,
                // Right face
                0.5f, -0.5f, -0.5f,  0.5f,  0.5f, -0.5f,  0.5f,  0.5f,  0.5f,  0.5f, -0.5f,  0.5f,
                // Left face
                -0.5f, -0.5f, -0.5f, -0.5f, -0.5f,  0.5f, -0.5f,  0.5f,  0.5f, -0.5f,  0.5f, -0.5f
        };

        float[] normals = {
                // Front
                0, 0, 1,  0, 0, 1,  0, 0, 1,  0, 0, 1,
                // Back
                0, 0, -1,  0, 0, -1,  0, 0, -1,  0, 0, -1,
                // Top
                0, 1, 0,  0, 1, 0,  0, 1, 0,  0, 1, 0,
                // Bottom
                0, -1, 0,  0, -1, 0,  0, -1, 0,  0, -1, 0,
                // Right
                1, 0, 0,  1, 0, 0,  1, 0, 0,  1, 0, 0,
                // Left
                -1, 0, 0,  -1, 0, 0,  -1, 0, 0,  -1, 0, 0
        };

        float[] uvs = {
                0, 0,  1, 0,  1, 1,  0, 1,  // Front
                1, 0,  1, 1,  0, 1,  0, 0,  // Back
                0, 1,  0, 0,  1, 0,  1, 1,  // Top
                1, 1,  0, 1,  0, 0,  1, 0,  // Bottom
                1, 0,  1, 1,  0, 1,  0, 0,  // Right
                0, 0,  1, 0,  1, 1,  0, 1   // Left
        };

        int[] indices = {
                0,  1,  2,  0,  2,  3,   // Front
                4,  5,  6,  4,  6,  7,   // Back
                8,  9, 10,  8, 10, 11,   // Top
                12, 13, 14, 12, 14, 15,  // Bottom
                16, 17, 18, 16, 18, 19,  // Right
                20, 21, 22, 20, 22, 23   // Left
        };

        return new Mesh(positions, normals, uvs, indices);
    }
}
