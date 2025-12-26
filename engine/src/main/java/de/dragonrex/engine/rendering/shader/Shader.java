package de.dragonrex.engine.rendering.shader;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;

public class Shader {
    private int programId;
    private int vertexShaderId;
    private int fragmentShaderId;

    public Shader(String vertexSource, String fragmentSource) {
        programId = GL20.glCreateProgram();

        vertexShaderId = createShader(vertexSource, GL20.GL_VERTEX_SHADER);
        fragmentShaderId = createShader(fragmentSource, GL20.GL_FRAGMENT_SHADER);

        GL20.glAttachShader(programId, vertexShaderId);
        GL20.glAttachShader(programId, fragmentShaderId);
        GL20.glLinkProgram(programId);

        if (GL20.glGetProgrami(programId, GL20.GL_LINK_STATUS) == 0) {
            throw new RuntimeException("Error linking shader: " + GL20.glGetProgramInfoLog(programId));
        }

        GL20.glDetachShader(programId, vertexShaderId);
        GL20.glDetachShader(programId, fragmentShaderId);

        GL20.glValidateProgram(programId);
        if (GL20.glGetProgrami(programId, GL20.GL_VALIDATE_STATUS) == 0) {
            System.err.println("Warning validating shader: " + GL20.glGetProgramInfoLog(programId));
        }
    }

    private int createShader(String source, int type) {
        int shaderId = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderId, source);
        GL20.glCompileShader(shaderId);

        if (GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS) == 0) {
            throw new RuntimeException("Error compiling shader: " + GL20.glGetShaderInfoLog(shaderId));
        }

        return shaderId;
    }

    public void bind() {
        GL20.glUseProgram(programId);
    }

    public void unbind() {
        GL20.glUseProgram(0);
    }

    public void setUniform(String name, int value) {
        int location = GL20.glGetUniformLocation(programId, name);
        GL20.glUniform1i(location, value);
    }

    public void setUniform(String name, float value) {
        int location = GL20.glGetUniformLocation(programId, name);
        GL20.glUniform1f(location, value);
    }

    public void setUniform(String name, Vector3f value) {
        int location = GL20.glGetUniformLocation(programId, name);
        GL20.glUniform3f(location, value.x, value.y, value.z);
    }

    public void setUniform(String name, Vector4f value) {
        int location = GL20.glGetUniformLocation(programId, name);
        GL20.glUniform4f(location, value.x, value.y, value.z, value.w);
    }

    public void setUniform(String name, Matrix4f value) {
        int location = GL20.glGetUniformLocation(programId, name);
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(16);
            value.get(fb);
            GL20.glUniformMatrix4fv(location, false, fb);
        }
    }

    public void cleanup() {
        unbind();
        if (programId != 0) {
            GL20.glDeleteProgram(programId);
        }
    }

    public int getProgramId() {
        return programId;
    }
}