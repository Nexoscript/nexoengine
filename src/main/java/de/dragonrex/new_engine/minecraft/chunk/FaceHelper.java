package de.dragonrex.new_engine.minecraft.chunk;

public class FaceHelper {
    public static float[][] cubeFaceFront(int x, int y, int z) {
        return new float[][] {
                {x,y,z+1},{x+1,y,z+1},{x+1,y+1,z+1},{x,y+1,z+1}
        };
    }

    public static float[][] cubeFaceBack(int x, int y, int z) {
        return new float[][] {
                {x+1,y,z},{x,y,z},{x,y+1,z},{x+1,y+1,z}
        };
    }

    public static float[][] cubeFaceLeft(int x, int y, int z) {
        return new float[][] {
                {x,y,z},{x,y,z+1},{x,y+1,z+1},{x,y+1,z}
        };
    }

    public static float[][] cubeFaceRight(int x, int y, int z) {
        return new float[][] {
                {x+1,y,z+1},{x+1,y,z},{x+1,y+1,z},{x+1,y+1,z+1}
        };
    }

    public static float[][] cubeFaceTop(int x, int y, int z) {
        return new float[][] {
                {x,y+1,z+1},{x+1,y+1,z+1},{x+1,y+1,z},{x,y+1,z}
        };
    }

    public static float[][] cubeFaceBottom(int x, int y, int z) {
        return new float[][] {
                {x,y,z},{x+1,y,z},{x+1,y,z+1},{x,y,z+1}
        };
    }
}
