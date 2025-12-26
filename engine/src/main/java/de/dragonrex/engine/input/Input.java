package de.dragonrex.engine.input;

public final class Input {

    private Input() {}

    public static void init(long windowHandle) {
        Keyboard.init(windowHandle);
        Mouse.init(windowHandle);
    }

    public static void update() {
        Keyboard.update();
        Mouse.update();
    }
}
