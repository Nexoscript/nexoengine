package de.dragonrex.engine.shader;

import org.lwjgl.opengl.GL20;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Shader {
    public int programId;
    private int vertexShaderId;
    private int fragmentShaderId;

    public Shader(String vertexPath, String fragmentPath) {
        try {
            vertexShaderId = compileShader(vertexPath, GL20.GL_VERTEX_SHADER);
            fragmentShaderId = compileShader(fragmentPath, GL20.GL_FRAGMENT_SHADER);

            programId = GL20.glCreateProgram();
            GL20.glAttachShader(programId, vertexShaderId);
            GL20.glAttachShader(programId, fragmentShaderId);
            GL20.glLinkProgram(programId);

            if (GL20.glGetProgrami(programId, GL20.GL_LINK_STATUS) == 0)
                throw new Exception("Shader-Programm konnte nicht gelinkt werden: " + GL20.glGetProgramInfoLog(programId));

            GL20.glDeleteShader(vertexShaderId);
            GL20.glDeleteShader(fragmentShaderId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int compileShader(String path, int type) {
        try {
            String source = new String(Files.readAllBytes(Paths.get(path)));
            int shader = GL20.glCreateShader(type);
            GL20.glShaderSource(shader, source);
            GL20.glCompileShader(shader);

            if (GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS) == 0)
                throw new Exception("Shader konnte nicht kompiliert werden: " + GL20.glGetShaderInfoLog(shader));

            return shader;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void bind() {
        GL20.glUseProgram(programId);
    }

    public void unbind() {
        GL20.glUseProgram(0);
    }

    public void cleanup() {
        // Shader-Programm trennen
        if (programId != 0) {
            GL20.glUseProgram(0);

            // Vertex und Fragment Shader löschen
            if (vertexShaderId != 0) {
                GL20.glDetachShader(programId, vertexShaderId);
                GL20.glDeleteShader(vertexShaderId);
                vertexShaderId = 0;
            }
            if (fragmentShaderId != 0) {
                GL20.glDetachShader(programId, fragmentShaderId);
                GL20.glDeleteShader(fragmentShaderId);
                fragmentShaderId = 0;
            }

            // Programm löschen
            GL20.glDeleteProgram(programId);
            programId = 0;
        }
    }
}
