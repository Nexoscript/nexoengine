package de.dragonrex.engine.rendering;

import de.dragonrex.engine.rendering.shader.Shader;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Material {
    private Shader shader;
    private Vector4f color = new Vector4f(1, 1, 1, 1);
    private Vector3f ambient = new Vector3f(0.2f, 0.2f, 0.2f);
    private Vector3f diffuse = new Vector3f(0.8f, 0.8f, 0.8f);
    private Vector3f specular = new Vector3f(1.0f, 1.0f, 1.0f);
    private float shininess = 32.0f;

    public Material(Shader shader) {
        this.shader = shader;
    }

    public Shader getShader() {
        return shader;
    }

    public void setShader(Shader shader) {
        this.shader = shader;
    }

    public Vector4f getColor() {
        return color;
    }

    public void setColor(Vector4f color) {
        this.color = color;
    }

    public void setColor(float r, float g, float b, float a) {
        this.color.set(r, g, b, a);
    }

    public Vector3f getAmbient() {
        return ambient;
    }

    public void setAmbient(Vector3f ambient) {
        this.ambient = ambient;
    }

    public Vector3f getDiffuse() {
        return diffuse;
    }

    public void setDiffuse(Vector3f diffuse) {
        this.diffuse = diffuse;
    }

    public Vector3f getSpecular() {
        return specular;
    }

    public void setSpecular(Vector3f specular) {
        this.specular = specular;
    }

    public float getShininess() {
        return shininess;
    }

    public void setShininess(float shininess) {
        this.shininess = shininess;
    }

    public void applyUniforms() {
        shader.setUniform("material.color", color);
        shader.setUniform("material.ambient", ambient);
        shader.setUniform("material.diffuse", diffuse);
        shader.setUniform("material.specular", specular);
        shader.setUniform("material.shininess", shininess);
    }
}
