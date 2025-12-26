package de.dragonrex.engine.ui.rendering;

import de.dragonrex.engine.rendering.shader.Shader;

public class UIShader {

    private static String createVertexShader() {
        return """
                
                #version 330 core
                
                layout (location = 0) in vec3 aPos;
                layout (location = 2) in vec2 aUV;
                
                uniform mat4 projection;
                uniform mat4 model;
                
                out vec2 vUV;
                
                void main() {
                    vUV = aUV;
                    gl_Position = projection * model * vec4(aPos, 1.0);
                }
                
                """;
    }

    private static String createFragmentShader() {
        return """
                
                #version 330 core
                
                in vec2 vUV;
                out vec4 FragColor;
                
                uniform vec4 color;
                
                void main() {
                    FragColor = color;
                }
                
                """;
    }

    public static Shader createShader() {
        return new Shader(createVertexShader(), createFragmentShader());
    }
}
