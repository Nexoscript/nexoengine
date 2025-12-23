package de.dragonrex.new_engine.minecraft.player;

import de.dragonrex.new_engine.camera.Camera3D;
import de.dragonrex.new_engine.input.Input;
import de.dragonrex.new_engine.input.Mouse;
import de.dragonrex.new_engine.minecraft.block.Block;
import de.dragonrex.new_engine.minecraft.chunk.Chunk;
import de.dragonrex.new_engine.minecraft.chunk.ChunkObject;
import de.dragonrex.new_engine.minecraft.world.World;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Player {
    private final Camera3D camera;
    private final float speed = 5f;
    private final float jumpPower = 8f;
    private Vector3f velocity = new Vector3f(0, 0, 0);
    private boolean onGround = false;
    private float hw = 0.25f, hh = 1.8f, hd = 0.35f;

    private World world;

    public Player(Camera3D camera, World world) {
        this.camera = camera;
        this.world = world;
    }

    public void update(float deltaTime) {
        handleMovement(deltaTime);
        handleGravity(deltaTime);
        handleMouseLook();
        camera.updateViewMatrix();
    }

    private void handleMovement(float deltaTime) {
        float moveSpeed = speed * deltaTime;
        Vector3f movement = new Vector3f(0, 0, 0);

        if (Input.isKeyPressed(GLFW.GLFW_KEY_W)) {
            Vector3f forward = new Vector3f(camera.getForward()).mul(moveSpeed);
            forward.y = 0; // Nur horizontale Bewegung
            movement.add(forward);
        }
        if (Input.isKeyPressed(GLFW.GLFW_KEY_S)) {
            Vector3f backward = new Vector3f(camera.getForward()).mul(-moveSpeed);
            backward.y = 0;
            movement.add(backward);
        }
        if (Input.isKeyPressed(GLFW.GLFW_KEY_A)) {
            Vector3f left = new Vector3f(camera.getRight()).mul(-moveSpeed);
            left.y = 0;
            movement.add(left);
        }
        if (Input.isKeyPressed(GLFW.GLFW_KEY_D)) {
            Vector3f right = new Vector3f(camera.getRight()).mul(moveSpeed);
            right.y = 0;
            movement.add(right);
        }

        if (movement.lengthSquared() > 0) {
            tryMove(movement);
        }
    }

    private void handleMouseLook() {
        camera.rotate(
                -(float) Mouse.getDeltaY() * 0.15f, // pitch
                (float) Mouse.getDeltaX() * 0.15f, // yaw
                0f
        );
        /*
        if (Mouse.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_RIGHT)) {
            camera.rotate(
                    -(float) Mouse.getDeltaY() * 0.15f, // pitch
                    (float) Mouse.getDeltaX() * 0.15f, // yaw
                    0f
            );
        }
         */
    }

    private void handleGravity(float deltaTime) {
        if(!onGround) {
            velocity.y -= 9.18f * deltaTime;
            float remaining = velocity.y * deltaTime;
            float step = 0.05f;

            while (Math.abs(remaining) > 0) {
                float move = Math.signum(remaining) * Math.min(step, Math.abs(remaining));
                tryMove(new Vector3f(0, move, 0));
                remaining -= move;
                if(onGround) break;
            }
        }
    }

    public void jump() {
        if(onGround) {
            velocity.y = jumpPower;
        }
    }

    private void tryMove(Vector3f delta) {
        Vector3f currentPos = camera.getPosition();
        Vector3f newPos = new Vector3f(currentPos).add(delta);

        // Spieler Bounding Box NACH der Bewegung
        float playerMinX = newPos.x - hw;
        float playerMaxX = newPos.x + hw;
        float playerMinY = newPos.y - hh;  // Füße
        float playerMaxY = newPos.y;        // Kopf
        float playerMinZ = newPos.z - hd;
        float playerMaxZ = newPos.z + hd;

        onGround = false;

        for (ChunkObject chunkObj : world.getChunks()) {
            Chunk chunk = chunkObj.getChunk();
            float chunkX = chunkObj.getWorldX();
            float chunkZ = chunkObj.getWorldZ();

            for (int x = 0; x < Chunk.CHUNK_SIZE; x++) {
                for (int y = 0; y < Chunk.CHUNK_SIZE; y++) {
                    for (int z = 0; z < Chunk.CHUNK_SIZE; z++) {
                        Block block = chunk.getBlock(x, y, z);
                        if (block == null) continue;

                        // Block Bounding Box in Weltkoordinaten
                        float blockMinX = x + chunkX;
                        float blockMaxX = blockMinX + 1;
                        float blockMinY = y;
                        float blockMaxY = y + 1;
                        float blockMinZ = z + chunkZ;
                        float blockMaxZ = blockMinZ + 1;

                        // AABB Kollisionstest
                        boolean overlapsX = playerMinX < blockMaxX && playerMaxX > blockMinX;
                        boolean overlapsY = playerMinY < blockMaxY && playerMaxY > blockMinY;
                        boolean overlapsZ = playerMinZ < blockMaxZ && playerMaxZ > blockMinZ;

                        if (overlapsX && overlapsY && overlapsZ) {
                            // Y-Kollision (Boden/Decke)
                            if (delta.y < 0) {  // Nach unten fallend
                                delta.y = blockMaxY - (currentPos.y - hh);
                                velocity.y = 0;
                                onGround = true;
                            } else if (delta.y > 0) {  // Nach oben springend
                                delta.y = blockMinY - currentPos.y;
                                velocity.y = 0;
                            }

                            // X/Z-Kollision (Wände) - nur wenn horizontale Bewegung
                            if (delta.x != 0 || delta.z != 0) {
                                delta.x = 0;
                                delta.z = 0;
                            }
                        }
                    }
                }
            }
        }

        camera.getPosition().add(delta);
    }
}
