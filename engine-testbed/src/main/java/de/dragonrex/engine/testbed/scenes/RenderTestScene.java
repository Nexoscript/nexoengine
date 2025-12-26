package de.dragonrex.engine.testbed.scenes;

import de.dragonrex.engine.core.GameObject;
import de.dragonrex.engine.core.Transform;
import de.dragonrex.engine.core.scene.Scene;
import de.dragonrex.engine.physics.BoxCollider;
import de.dragonrex.engine.rendering.camera.CameraComponent;
import de.dragonrex.engine.rendering.light.DirectionalLight;
import de.dragonrex.engine.rendering.light.LightManager;
import de.dragonrex.engine.rendering.material.Material;
import de.dragonrex.engine.rendering.primitives.CapsuleRenderer;
import de.dragonrex.engine.rendering.primitives.CubeRenderer;
import de.dragonrex.engine.rendering.primitives.MeshRenderer;
import de.dragonrex.engine.rendering.primitives.SphereRenderer;
import de.dragonrex.engine.rendering.shader.Shader;
import de.dragonrex.engine.rendering.shader.ShaderLibrary;
import de.dragonrex.engine.testbed.controller.CameraController;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class RenderTestScene extends Scene {

    private void createCamera() {
        GameObject player = new GameObject();
        Transform transform = new Transform();
        transform.getPosition().set(0, 0, -10);
        transform.getRotation().set(0, 0, 0);
        CameraComponent camera = new CameraComponent();
        camera.setPrimary(true);
        player.addComponent(transform);
        player.addComponent(new CameraController());
        player.addComponent(camera);
        add(player);
    }

    private void createLight() {
        DirectionalLight sun = new DirectionalLight();
        sun.getDirection().set(-0.3f, -1.0f, -0.2f);
        sun.getColor().set(1.0f, 0.95f, 0.85f);
        sun.intensity = 1.2f;
        LightManager.setSun(sun);
    }

    @Override
    protected void onInit() {
        createCamera();
        createLight();

        Shader shader = ShaderLibrary.createBasicShader();

        Material cubeMat = new Material(shader);
        cubeMat.setColor(50f, 168f, 82f, 255f);

        GameObject cube = new GameObject();
        Transform cubeTransform = new Transform();
        cubeTransform.setPosition(new Vector3f(0, 0, 0));
        cube.addComponent(cubeTransform);
        cube.addComponent(new CubeRenderer(cubeMat, cube));
        add(cube);

        Material sphereMat = new Material(shader);
        sphereMat.setColor(224f, 196f, 54f, 255f);

        GameObject sphere = new GameObject();
        Transform sphereTransform = new Transform();
        sphereTransform.setPosition(new Vector3f(2, 0, 0));
        sphere.addComponent(sphereTransform);
        sphere.addComponent(new SphereRenderer(sphereMat, 16, sphere));
        add(sphere);

        Material capMat = new Material(shader);
        capMat.setColor(224f, 54f, 99f, 255f);

        GameObject capsule = new GameObject();
        Transform capsualTransform = new Transform();
        capsualTransform.setPosition(new Vector3f(0, 0, 2));
        capsule.addComponent(capsualTransform);
        capsule.addComponent(new CapsuleRenderer(capMat, 0.5f, 1.8f, 16, capsule));
        add(capsule);
    }
}
