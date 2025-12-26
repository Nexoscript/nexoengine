package de.dragonrex.engine.rendering.shader;

public class ShaderLibrary {

    public static final String BASIC_VERTEX_SHADER = """
        #version 330 core
        
        layout (location = 0) in vec3 aPosition;
        layout (location = 1) in vec3 aNormal;
        layout (location = 2) in vec2 aTexCoord;
        
        out vec3 FragPos;
        out vec3 Normal;
        out vec2 TexCoord;
        
        uniform mat4 model;
        uniform mat4 view;
        uniform mat4 projection;
        
        void main() {
            FragPos = vec3(model * vec4(aPosition, 1.0));
            Normal = mat3(transpose(inverse(model))) * aNormal;
            TexCoord = aTexCoord;
            
            gl_Position = projection * view * model * vec4(aPosition, 1.0);
        }
        """;

    public static final String BASIC_FRAGMENT_SHADER = """
        #version 330 core
        
        struct Material {
            vec4 color;
            vec3 ambient;
            vec3 diffuse;
            vec3 specular;
            float shininess;
        };
        
        struct DirectionalLight {
            vec3 direction;
            vec3 color;
            float intensity;
        };
        
        in vec3 FragPos;
        in vec3 Normal;
        in vec2 TexCoord;
        
        out vec4 FragColor;
        
        uniform Material material;
        uniform vec3 viewPos;
        uniform DirectionalLight sun;
        
        void main() {
            // Ambient
            vec3 ambient = material.ambient * material.color.rgb;
            
            vec3 lightDir = normalize(-sun.direction);
            
            // Diffuse
            vec3 norm = normalize(Normal);
            float diff = max(dot(norm, lightDir), 0.0);
            diff = max(diff, 0.2);
            vec3 diffuse = diff * sun.color * sun.intensity * material.diffuse * material.color.rgb;
            
            // Specular
            vec3 viewDir = normalize(viewPos - FragPos);
            vec3 reflectDir = reflect(-lightDir, norm);
            float spec = pow(max(dot(viewDir, reflectDir), 0.0), material.shininess);
            vec3 specular = spec * material.specular * sun.color * sun.intensity;
            
            vec3 result = ambient + diffuse + specular;
            FragColor = vec4(result, material.color.a);
        }
        """;

    public static Shader createBasicShader() {
        return new Shader(BASIC_VERTEX_SHADER, BASIC_FRAGMENT_SHADER);
    }
}
